package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.vo.CartVo;

public interface ICartService {

    /*添加商品到购物车*/
    public ServerResponse<CartVo> addProductToCart(Integer userid,Integer productid,int count);

    /*查询用户购物信息*/
    public ServerResponse<CartVo> findCartsByUserId(Integer userid);

    /*更新某个商品的数量*/
    public ServerResponse<CartVo> updateCartByUseridAndProductid(Integer userid, Integer productid, Integer quantity);

    /*删除购物车中的商品*/
    public ServerResponse<CartVo> deleteProductsFromCart(Integer userid, String productIds);

    /*选中某个商品*/
    public ServerResponse<CartVo> checkedProductByProductId(Integer userid,Integer productid);

    /*取消选中某个商品*/
    public ServerResponse<CartVo> uncheckedProductByProductId(Integer userid,Integer productid);

    /*查询在购物车里的产品数量*/
    public ServerResponse<Integer> findAllCartByProductNum(Integer userid);

    /*购物车全选*/
    public ServerResponse<CartVo> isAllCheck(Integer userid);

    /*购物车取消全选*/
    public ServerResponse<CartVo> unIsAllCheck(Integer userid);
}
