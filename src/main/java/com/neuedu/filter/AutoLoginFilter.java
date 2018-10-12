package com.neuedu.filter;

import com.neuedu.businessconst.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IUserService;
import com.neuedu.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/manage/*")
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        HttpSession httpSession = request.getSession();
        UserInfo userInfo = (UserInfo) httpSession.getAttribute(Const.CURRENTUSER);
        if (userInfo!=null){
            chain.doFilter(req, resp);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String token = null;
        //String password = null;
        if (cookies!=null){
            for (Cookie cookie :cookies){
                String cookieName = cookie.getName();
                if (cookieName.equals(Const.AUTOLOGINTOKEN)){
                    token = cookie.getValue();
                }
               /* if (cookieName.equals(Const.PASSWORDCOOKIE)){
                    password = cookie.getValue();
                }*/
            }
        }

        if (token!=null){
            IUserService userService = new UserServiceImpl();
            userInfo = userService.findUserInfoByToken(token);
            if (userInfo!=null){// 登录成功
                HttpSession _httpSession = request.getSession();
                httpSession.setAttribute(Const.CURRENTUSER,userInfo);
                chain.doFilter(req, resp);
                return;
            }
        }

        //代码重构 code review
        //response.sendRedirect("http://localhost:8080/BusinessProject/login.jsp");
        ServerResponse serverResponse = ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        serverResponse.convertToJson(serverResponse,response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
