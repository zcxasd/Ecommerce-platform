package com.neuedu.dao;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Cart;
import com.neuedu.vo.CartVo;

import java.util.List;

public interface ICartDao {

    /*根据userid和productid查询购物车中的购物信息*/
    public Cart findCartByUseridAndProductid(Integer userid,Integer productid);

    /*添加购物信息*/
    public Integer addProductToCart(Cart cart);

    /*更新购物车中的商品数量*/
    public Integer updateCartByUseridAndProductid(Cart cart);

    /*根据userid查询购物集合*/
    public List<Cart> findCartByUserid(Integer userid);

   /*
   * 判断购物车全选
   * 0:全选
   * */
   public Integer isCheckAll(Integer userid);

    /*移除购物车中的商品信息*/
    public Integer deleteProducts(List<Integer> productIds,Integer userid);

    /*选中购物车某个商品*/
    public Integer checkedProductByProductId(Integer userid,Integer productid);

    /*取消选中购物车某个商品*/
    public Integer uncheckedProductByProductId(Integer userid,Integer productid);

    /*查询在购物车里的产品数量*/
    public Integer findAllCartByProductNum(Integer userid);

    /*购物车全选*/
    public Integer isAllCheck(Integer userid);

    /*购物车取消全选*/
    public Integer unIsAllCheck(Integer userid);

    /*查询用户已选中的购物信息*/
    public List<Cart> findCheckedCartsByUserId(Integer userid);

    /*下单成功后，移除购物车中已经下单的商品*/
    public Integer removeCheckedProduct(List<Cart> cartList,Integer userid);
}
