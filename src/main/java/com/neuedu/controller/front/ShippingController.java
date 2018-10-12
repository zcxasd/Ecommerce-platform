package com.neuedu.controller.front;

import com.neuedu.businessconst.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IShippingService;
import com.neuedu.service.IUserService;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import com.neuedu.vo.ShippingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/shipping")
public class ShippingController {

    @Autowired
    private IShippingService shippingService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/add.do")
    public ServerResponse<Integer> addProduct(HttpSession session, Shipping shipping){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        shipping.setUser_id(userInfo.getId());
        return shippingService.addShipping(shipping);
    }

    @RequestMapping(value = "/delete.do")
    public ServerResponse<Integer> deleteShipping(HttpSession session,Integer shippingId){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return shippingService.deleteShipping(userInfo.getId(),shippingId);
    }

    @RequestMapping(value = "/update.do")
    public ServerResponse<Integer> updateAddress(HttpSession session,Shipping shipping){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return shippingService.updateAddress(shipping);
    }

    @RequestMapping(value = "/check.do")
    public ServerResponse<Shipping> checkedShippingByShippingId(HttpSession session,Integer shippingId){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return shippingService.checkedShippingByShippingId(userInfo.getId(),shippingId);
    }

    /*
     * 分页查询地址
     * */
    @RequestMapping(value = "/findShippingByPageNo")
    public ServerResponse<PageModel<ShippingVo>> findShippingByPageNo(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return shippingService.findShippingByPageNo(pageNo,pageSize);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

}
