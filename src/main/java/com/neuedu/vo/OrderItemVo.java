package com.neuedu.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemVo implements Serializable {

    private Integer id;//'订单id',
    private Long order_no;// '订单号' ,
    private Integer product_id;//
    private String product_name;// '商品名称' ,
    private String product_image;// '商品图片地址' ,
    private BigDecimal current_unit_price;// '生成订单时的商品单价，单位元，保留两位小数,
    private Integer quantity;//'商品数量',
    private BigDecimal total_price;// '商品总价，单位元，保留两位小数,
    private String create_time;//'创建时间',

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getOrder_no() {
        return order_no;
    }

    public void setOrder_no(Long order_no) {
        this.order_no = order_no;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public BigDecimal getCurrent_unit_price() {
        return current_unit_price;
    }

    public void setCurrent_unit_price(BigDecimal current_unit_price) {
        this.current_unit_price = current_unit_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
