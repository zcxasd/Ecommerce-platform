package com.neuedu.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/*
* 前端购物车实体类
* */
public class CartVo implements Serializable{

    private List<CartProductVo> cartProductVoList;
    private boolean isallchecked;
    private BigDecimal totalPrice;

    public CartVo() {

    }

    public CartVo(List<CartProductVo> cartProductVoList, boolean isallchecked, BigDecimal totalPrice) {
        this.cartProductVoList = cartProductVoList;
        this.isallchecked = isallchecked;
        this.totalPrice = totalPrice;
    }

    public List<CartProductVo> getCartProductVoList() {
        return cartProductVoList;
    }

    public void setCartProductVoList(List<CartProductVo> cartProductVoList) {
        this.cartProductVoList = cartProductVoList;
    }

    public boolean isIsallchecked() {
        return isallchecked;
    }

    public void setIsallchecked(boolean isallchecked) {
        this.isallchecked = isallchecked;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
