package com.neuedu.controller;

import com.neuedu.businessconst.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import com.neuedu.service.IUserService;
import com.neuedu.vo.OrderVo;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/manage/order")
public class OrderController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    /*订单发货*/
    @RequestMapping(value = "/send")
    public ServerResponse sendOrder(Long orderNo, HttpSession session){
        //step1：是否登录
        //step2：是否为管理员

        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return orderService.sendOrder(orderNo);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

    /*订单list*/
    @RequestMapping(value = "/findorderbypageno")
    public ServerResponse<PageModel<OrderVo>> findOrderByPageNo(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return orderService.findOrderByPageNo(pageNo,pageSize);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

    /*按订单号查询*/
    @RequestMapping(value = "/findorderbyId")
    public ServerResponse<OrderVo> findOrderById(Long orderNo,HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return orderService.findOrderById(orderNo);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

    /*订单详情*/
    @RequestMapping("/getorderdetail")
    public  ServerResponse getOrderDetail(Long orderNo,HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return orderService.getOrderDetail(orderNo,userInfo.getId());
    }

}
