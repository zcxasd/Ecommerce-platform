package com.neuedu.service.impl;

import com.neuedu.businessconst.Const;
import com.neuedu.cache.TokenCache;
import com.neuedu.common.MD5Utils;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.IUserDao;
import com.neuedu.dao.impl.UserDaoImpl;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    IUserDao userDao = new UserDaoImpl();

    @Override
    public ServerResponse<UserInfo> login(String username, String password) {
        UserInfo userInfo = userDao.login(username,MD5Utils.GetMD5Code(password));
        if (userInfo!=null){
            return ServerResponse.createServerResponse(0,userInfo);
        }
            return ServerResponse.createServerResponse(1,"登录失败",userInfo);
    }

    @Override
    public int updateTokenById(int userid, String token) {

        return userDao.updateTokenById(userid,token);
    }

    @Override
    public UserInfo findUserInfoByToken(String token) {
        return userDao.findUserInfoByToken(token);
    }

    @Override
    public ServerResponse<UserInfo> register(UserInfo userInfo) {
        // 判断username是否存在
        int result_username = userDao.checkUserName(userInfo.getUsername());
        if (result_username>0){// 用户名已存在
            //throw BusinessException.createException(session,"用户名已经存在","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(7,"用户名已经存在");
        }
        // 判断邮箱是否存在
        int result_email = userDao.checkEmail(userInfo.getEmail());
        if (result_email>0){// 邮箱已存在
            //throw BusinessException.createException(session,"邮箱已经存在","3s后跳转到注册页面","register.jsp");
            return ServerResponse.createServerResponse(7,"邮箱已经存在");
        }
        // 密码md5加密
        userInfo.setPassword(MD5Utils.GetMD5Code(userInfo.getPassword()));
        // 设置角色
        userInfo.setRole(1);
        // 注册
       int result = userDao.register(userInfo);
       if (result>0){
           return ServerResponse.createServerResponse(0,"注册成功");
       }else {
           return ServerResponse.createServerResponse(8,"注册失败");
       }
    }

    @Override
    public ServerResponse<String> findQuestionByUsername(HttpSession session,String username) {
        // step1：校验username是否存在
        int result = userDao.checkUserName(username);
        if (result<=0){// 用户名不存在
            //throw BusinessException.createException(session,"用户名不存在","3s后跳转到找回密码页面","findPassword.jsp");
            return ServerResponse.createServerResponse(2,"用户名不存在");
        }
        // step2：根据username查询问题
        String question = userDao.findQuestionByUsername(username);
        return ServerResponse.createServerResponse(0,"成功获取问题",question);
    }

    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {

        // 判断username是否存在
        int result = userDao.checkUserName(username);
        if (result<=0){// 用户名不存在
            return ServerResponse.createServerResponse(4,"用户名不存在");
            //throw BusinessException.createException(session,"用户名不存在","3s后跳转到找回填写答案页面","answer.jsp");
        }
        // 校验
        int count = userDao.checkAnswer(username,question,answer);
        if (count>0){// 校验成功
            // 防止横向越权：token
            String forget_token = UUID.randomUUID().toString();
            // 缓存
            TokenCache.set(Const.TOKEN_PREFIX+username,forget_token);
            return ServerResponse.createServerResponse(0,"校验成功",forget_token);
        } else {
            return ServerResponse.createServerResponse(5,"答案不正确");
            //throw BusinessException.createException(session,"答案不正确","3s后跳转到找回填写答案页面","answer.jsp");
        }
    }

    @Override
    public ServerResponse<String> updatePassword(String username, String newPassword, String token) {

       // 判断username是否存在
        int result = userDao.checkUserName(username);
        if (result<=0){// 用户名不存在
            return ServerResponse.createServerResponse(4,"用户名不存在");
            //throw BusinessException.createException(session,"用户名不存在","3s后跳转到修改密码页面","newPassword.jsp");
        }
        // 判断token是否有效
        String cacheToken = TokenCache.get(Const.TOKEN_PREFIX+username);
        if (!cacheToken.equals(token)){
            return ServerResponse.createServerResponse(5,"无效的token");
            //throw BusinessException.createException(session,"无效的token","3s后跳转到修改密码页面","newPassword.jsp");
        }
        // 修改密码
       result = userDao.updatePassword(username,MD5Utils.GetMD5Code(newPassword));
        if (result>0){// 修改成功
            return ServerResponse.createServerResponse(0,"恭喜，修改密码成功");
            //request.getRequestDispatcher("login.jsp").forward(request,response);
        } else {
            return ServerResponse.createServerResponse(6,"密码修改失败");
            //throw BusinessException.createException(session,"密码修改失败","3s后跳转到输入新密码页面","newPassword.jsp");
        }
    }
    @Override
    public ServerResponse<UserInfo> updatePassword(String oldpassword, String newpassword,UserInfo userInfo) {

       // 判断用户是否登录
       /* UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 未登录或者已经过期
            throw BusinessException.createException(session,"未登录或者已经过期","3s后跳转到登录页面","login.jsp");
        }*/
        // 获取用户信息，验证旧密码是否正确
        UserInfo oldUser = userDao.login(userInfo.getUsername(),MD5Utils.GetMD5Code(oldpassword));
        if (oldUser==null){// 旧密码是错误的
            //throw BusinessException.createException(session,"旧密码是错误的","3s后跳转到登录页面","login.jsp");
            return ServerResponse.createServerResponse(6,"旧密码是错误的");
        }
        // 修改密码
        int result = userDao.updatePassword(userInfo.getUsername(),MD5Utils.GetMD5Code(newpassword));
        if (result>0){
            return ServerResponse.createServerResponse(0,"修改成功");
        }else {
            return ServerResponse.createServerResponse(7,"修改失败");

        }
        // http://localhost:8080/BusinessProject/user?operation=1&username=root1&password=123456
        // http://localhost:8080/BusinessProject/user?operation=6&oldpassword=123456&newpassword=123
    }

    @Override
    public boolean isAdminRole(UserInfo userInfo) {
        return userInfo.getRole()==0;
    }
}
