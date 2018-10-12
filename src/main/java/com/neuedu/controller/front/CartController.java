package com.neuedu.controller.front;

import com.neuedu.businessconst.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.ICartService;
import com.neuedu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @RequestMapping(value = "/add")
    public ServerResponse<CartVo> add(Integer productId, Integer count, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }

        return cartService.addProductToCart(userInfo.getId(),productId,count);
    }

    /*查询购物车列表*/
    @RequestMapping("/list.do")
    public ServerResponse<CartVo> findCartsByuserid(HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return cartService.findCartsByUserId(userInfo.getId());
    }

    /*更新购物车中的某个商品的数量*/
    @RequestMapping("/update.do")
    public ServerResponse<CartVo> updateCartByUseridAndProductId(HttpSession session,Integer productId,Integer quantity){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        if (quantity<1){
            return ServerResponse.createServerResponse(ResponseCode.NUMBER.getCode(),ResponseCode.NUMBER.getMsg());
        }
        return cartService.updateCartByUseridAndProductid(userInfo.getId(),productId,quantity);
    }

    /*删除购物车中的某些商品*/
    @RequestMapping("/delete.do")
    public ServerResponse<CartVo> deleteProductsFromCart(HttpSession session,String productIds){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return cartService.deleteProductsFromCart(userInfo.getId(),productIds);
    }

    /*选中购物车中的某个商品*/
    @RequestMapping("/checked.do")
    public ServerResponse<CartVo> checkedProduct(HttpSession session,Integer productid){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return cartService.checkedProductByProductId(userInfo.getId(),productid);
    }

    /*取消选中购物车中的某个商品*/
    @RequestMapping("/unchecked.do")
    public ServerResponse<CartVo> uncheckedProduct(HttpSession session,Integer productid){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return cartService.uncheckedProductByProductId(userInfo.getId(),productid);
    }

    /*查询在购物车里的产品数量
     */
    @RequestMapping("/findall.do")
    public ServerResponse<Integer> findAllCartByProductNum(HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return cartService.findAllCartByProductNum(userInfo.getId());
    }

    /*购物车全选*/
    @RequestMapping("/isAllCheck.do")
    public ServerResponse<CartVo> isAllCheck(HttpSession session,Integer productid){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return cartService.isAllCheck(userInfo.getId());
    }

    /*购物车取消全选*/
    @RequestMapping("/unIsAllCheck.do")
    public ServerResponse<CartVo> unIsAllCheck(HttpSession session,Integer productid){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        return cartService.unIsAllCheck(userInfo.getId());
    }
}
