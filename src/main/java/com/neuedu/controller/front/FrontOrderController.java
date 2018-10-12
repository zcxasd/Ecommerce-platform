package com.neuedu.controller.front;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.neuedu.businessconst.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IOrderService;
import com.neuedu.service.IUserService;
import com.neuedu.vo.OrderVo;
import com.neuedu.vo.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@RestController
@RequestMapping(value = "/order")
public class FrontOrderController {

    @Autowired
    IOrderService orderService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/pay.do")
    public ServerResponse pay(Long orderNo, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return orderService.pay(orderNo,userInfo.getId());
    }

    @RequestMapping(value = "/callback.do")
    public String alipay_callback(HttpServletRequest request){

        Map<String,String[]> map = request.getParameterMap();
        Map<String,String> params = new HashMap<>();
        for(Iterator<String> iterator=map.keySet().iterator();iterator.hasNext();){
            //map-->key
            String key = iterator.next();
            String[] values = (String[]) map.get(key);
            String value="";
            for (int i=0;i<values.length;i++){//{"a","b","c"} value=a,b,c
                value=(i==values.length-1?value+values[i]:value+values[i]+",");
            }
            params.put(key,value);
        }
        //支付宝验签
        try {
            params.remove("sign_type");
            boolean result = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if (result){//支付宝回调该接口
                return orderService.alipaycallback(params);
            }else{
                return "fail";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "fail";
    }

    @RequestMapping(value = "/query_order_status.do")
    public ServerResponse query_order_status(Long orderNo){
    return orderService.query_order_status(orderNo);
    }

    /*创建订单*/
    @RequestMapping(value = "/create.do")
    public ServerResponse createOrder(Integer shippingId,HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return orderService.createOrder(shippingId,userInfo.getId());
    }

    /*取消订单*/
    @RequestMapping("/cancelOrder")
    public ServerResponse cancelOrder(Long orderNo,HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return orderService.cancelOrder(orderNo,userInfo.getId());
    }

    /*获取订单中的商品信息*/
    @RequestMapping("/getcartproductinfo")
    public  ServerResponse getCart_productinfo(HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return orderService.getCartProductInfo(userInfo.getId());
    }

    /*获取订单中的商品详细信息*/
    @RequestMapping("/getorderdetail")
    public  ServerResponse getOrderDetail(Long orderNo,HttpSession session){
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return orderService.getOrderDetail(orderNo,userInfo.getId());
    }

    /*订单list*/
    @RequestMapping(value = "/findorderbypagenoanduserid")
    public ServerResponse<PageModel<OrderVo>> findOrderByPageNoAndUserId(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return orderService.findOrderByPageNoAndUserId(pageNo,pageSize,userInfo.getId());
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }
}
