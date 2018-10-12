package com.neuedu.vo;

import com.neuedu.pojo.Category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/*前端购物车商品实体类*/
public class CartProductVo implements Serializable{

    private Integer productid ;//'商品id',
    private Integer user_id;
    private String name    ;// '商品名称',
    private String subtitle    ;// 商品副标题
    private Integer stock; // 商品库存
    private String main_image    ;// 产品主图，url相对地址
    private String sub_images    ;// 图片地址，json格式
    private BigDecimal productprice;//价格，单位-元保留两位小数
    private String limitQuantity      ;// 库存描述
    private Integer status      ;// 商品状态，1-在售 2-下架 3-删除
    private Integer cartid;
    private Integer quantity;   // 商品数量
    private Integer checked;   // 是否选择：1=以勾选，0=未勾选
    private BigDecimal totalprice; // 商品总价格

    public CartProductVo() {

    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getMain_image() {
        return main_image;
    }

    public void setMain_image(String main_image) {
        this.main_image = main_image;
    }

    public String getSub_images() {
        return sub_images;
    }

    public void setSub_images(String sub_images) {
        this.sub_images = sub_images;
    }

    public BigDecimal getProductprice() {
        return productprice;
    }

    public void setProductprice(BigDecimal productprice) {
        this.productprice = productprice;
    }

    public String getLimitQuantity() {
        return limitQuantity;
    }

    public void setLimitQuantity(String limitQuantity) {
        this.limitQuantity = limitQuantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCartid() {
        return cartid;
    }

    public void setCartid(Integer cartid) {
        this.cartid = cartid;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }
}
