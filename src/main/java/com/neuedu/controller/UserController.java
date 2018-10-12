package com.neuedu.controller;

import com.google.gson.Gson;
import com.neuedu.businessconst.Const;
import com.neuedu.common.IpUtils;
import com.neuedu.common.MD5Utils;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping(value = "/user")
public class UserController extends HttpServlet {

    @Autowired
    IUserService userService;
    /*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        String operation = request.getParameter("operation");
        if (operation==null||operation.equals("")){
            throw BusinessException.createException(request.getSession(),"operation参数必须","3s后跳转到登录页面","login.jsp");
        }
        if (operation.equals("1")){// 登录操作
            //login(request,response);
        } else if (operation.equals("2")){// 注册
            //register(request,response);
        }else if (operation.equals("3")){// 根据username查询问题
            findQuestionUsername(request,response);
        } else if (operation.equals("4")){// 校验答案
            checkAnswer(request,response);
        }else if (operation.equals("5")){// 修改密码
            updatePassword(request,response);
        }else if (operation.equals("6")){// 登录状态下重置密码
            //updatePasswordLogin(request,response);
        }else if (operation.equals("7")){// 获取用户信息
           //getuserinfo(request,response);
        }else if (operation.equals("8")){// 退出登录
            //logout(request,response);
        }
    }*/

    @RequestMapping(value = "/logout")
    public ServerResponse<UserInfo> logout(HttpSession session){
        // 校验是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        if (userInfo!=null){
            session.removeAttribute(Const.CURRENTUSER);
        }
        // token清空
        //IUserService userService = new UserServiceImpl();
        if (userInfo!=null){
            userService.updateTokenById(userInfo.getId(),"");
        }
        return ServerResponse.createServerResponse(0,"成功");
   }

   // 获取用户信息
    @RequestMapping(value = "/userinfo")
    public ServerResponse<UserInfo> getuserinfo(HttpSession session){
        // 校验是否登录
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){
            // 未登录或登录过期
            //throw BusinessException.createException(session,"未登录或登录过期","3s后跳转到登录页面","login.jsp");
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
            System.out.println("===userinfo==="+userInfo);
        return ServerResponse.createServerResponse(0,"成功",userInfo);
    }

    // 登录状态下重置密码
    @RequestMapping(value = "/setpassword")
    public ServerResponse<UserInfo> updatePasswordLogin(@RequestParam(value = "oldpassword") String oldpassword
            ,@RequestParam(value = "newpassword") String newpassword,HttpSession session){
        // 非空验证
        if (oldpassword==null||oldpassword.equals("")){
            //throw BusinessException.createException(session,"旧密码不能为空","3s后跳转到登录页面","login.jsp");
            return ServerResponse.createServerResponse(1,"旧密码不能为空");
        }
        if (newpassword==null||newpassword.equals("")){
            //throw BusinessException.createException(session,"新密码不能为空","3s后跳转到登录页面","login.jsp");
            return ServerResponse.createServerResponse(2,"新密码不能为空");

        }
        //http://localhost:8080/BusinessProject/user?operation=4&username=root&question=xxx&answer=xxxx
        // 调用service
        //IUserService userService = new UserServiceImpl();
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 未登录或者已经过期
            //throw BusinessException.createException(session,"未登录或者已经过期","3s后跳转到登录页面","login.jsp");
            return ServerResponse.createServerResponse(5,"未登录或者已经过期");
        }
        return userService.updatePassword(oldpassword,newpassword,userInfo);
    }

    @RequestMapping(value = "/updatepassword")
    public ServerResponse<String> updatePassword(String username,String newpassword,String token){
        // 非空验证
        if (username==null||username.equals("")){
            return ServerResponse.createServerResponse(1,"用户名不能为空");
            //throw BusinessException.createException(session,"用户名不能为空","3s后跳转到输入新密码页面","newPassword.jsp");
        }
        if (newpassword==null||newpassword.equals("")){
            return ServerResponse.createServerResponse(2,"密码不能为空");
            //throw BusinessException.createException(session,"密码不能为空","3s后跳转到输入新密码页面","newPassword.jsp");
        }
        if (token==null||token.equals("")){
            return ServerResponse.createServerResponse(3,"token不能为空");
            //throw BusinessException.createException(session,"token不能为空","3s后跳转到输入新密码页面","newPassword.jsp");
        }
        //http://localhost:8080/BusinessProject/user?operation=4&username=root&question=xxx&answer=xxxx
        // 调用service
        //IUserService userService = new UserServiceImpl();
        return userService.updatePassword(username,newpassword,token);
    }

    @RequestMapping(value = "/checkanswer")
    public ServerResponse<String> checkAnswer(String username,String question,String answer) {
        // 非空验证
        if (username==null||username.equals("")){
            return ServerResponse.createServerResponse(1,"用户名不能为空");
            //throw BusinessException.createException(session,"用户名不能为空","3s后跳转到输入答案页面","answer.jsp");
        }
        if (question==null||question.equals("")){
            //throw BusinessException.createException(session,"问题不能为空","3s后跳转到输入答案页面","answer.jsp");
            return ServerResponse.createServerResponse(2,"问题不能为空");
        }
        if (answer==null||answer.equals("")){
            return ServerResponse.createServerResponse(3,"答案不能为空");
            //throw BusinessException.createException(session,"答案不能为空","3s后跳转到输入答案页面","answer.jsp");
        }
        // 调用service
        //IUserService userService = new UserServiceImpl();
        return userService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "/findquestion")
    public ServerResponse<String> findQuestionUsername(String username,HttpSession session){
        // 非空验证
        if (username==null||username.equals("")){
            //throw BusinessException.createException(session,"用户名不能为空","3s后跳转到找回密码页面","findPassword.jsp");
            return ServerResponse.createServerResponse(1,"用户名不能为空");
        }
        // 调用service层
        //IUserService userService = new UserServiceImpl();
        return userService.findQuestionByUsername(session,username);
    }

    // 注册
    @RequestMapping(value = "/register")
    public ServerResponse<UserInfo> register(String username,String password,String email,String phone,String question,String answer){
        // 非空验证
        if (username==null||username.equals("")){
            //throw BusinessException.createException(session,"用户名不能为空","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(1,"用户名不能为空");
        }
        if (password==null||password.equals("")){
            //throw BusinessException.createException(session,"密码不能为空","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(2,"密码不能为空");
        }
        if (email==null||email.equals("")){
            //throw BusinessException.createException(session,"邮箱不能为空","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(3,"邮箱不能为空");
        }
        if (phone==null||phone.equals("")){
            //throw BusinessException.createException(session,"手机号不能为空","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(4,"手机号不能为空");
        }
        if (question==null||question.equals("")){
            //throw BusinessException.createException(session,"找回密码的问题不能为空","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(5,"找回密码的问题不能为空");
        }
        if (answer==null||answer.equals("")){
            //throw BusinessException.createException(session,"找回密码的答案不能为空","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(6,"找回密码的答案不能为空");
        }
        //IUserService userService = new UserServiceImpl();
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.setEmail(email);
        userInfo.setPhone(phone);
        userInfo.setQuestion(question);
        userInfo.setAnswer(answer);
        return userService.register(userInfo);
    }

    // 登录
    @RequestMapping(value = "/login")
    public ServerResponse<UserInfo> login(String username,String password,HttpSession session,HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // 非空判断
        if (username == null || username.equals("") || password == null || password.equals("")) {
            return ServerResponse.createServerResponse(2, "用户名或密码不能为空");
            //throw BusinessException.createException(httpSession,"用户名或密码不能为空","3s后跳转到登录页面","login.jsp");
        }
        //IUserService userService = new UserServiceImpl();
        ServerResponse<UserInfo> serverResponse = userService.login(username, password);
        UserInfo userInfo = serverResponse.getData();
        if (userInfo != null) {//查询成功

           /* // 创建用户名的cookie
            Cookie username_cookie = new Cookie(Const.USERNAMECOOKIE,username);
            // 设置cookie有效期7天
            username_cookie.setMaxAge(60*60*24*7);
            // 设置密码的cookie
            Cookie password_cookie = new Cookie(Const.PASSWORDCOOKIE,password);
            // 设置cookie有效期7天
            password_cookie.setMaxAge(60*60*24*7);
            response.addCookie(username_cookie);
            response.addCookie(password_cookie);*/
            // 获取到ip
            String ip = IpUtils.getRemoteAddress(request);
            // 获取mac
            String macAddress = IpUtils.getMACAddress(ip);
            // 测试mac地址（ip和localhost）
            //System.out.println(macAddress);
            // mac加密
            String token = MD5Utils.GetMD5Code(macAddress);
            //String token = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(Const.AUTOLOGINTOKEN, token);
            // 设置自动登录的时间
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
            // token保存到数据库
            userService.updateTokenById(userInfo.getId(), token);
            session.setAttribute(Const.CURRENTUSER, userInfo);
            return serverResponse;
        } else {// 用户名或密码错误
            return ServerResponse.createServerResponse(3, "用户名或者密码错误");
        }
    }
}
