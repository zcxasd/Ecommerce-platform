package com.neuedu.pojo;

import java.io.Serializable;
import java.util.Date;

/*购物车POJO*/
public class Cart implements Serializable{
    private Integer id;
	private Integer user_id;
	private Integer product_id;
    private Integer quantity;   // 商品数量
    private Integer checked;   // 是否选择：1=以勾选，0=未勾选
    private Date create_time;//创建时间
    private Date update_time;//更新时间

    public Cart() {

    }

    public Cart(Integer user_id, Integer product_id, Integer quantity, Integer checked) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Cart(Integer id, Integer user_id, Integer product_id, Integer quantity, Integer checked, Date create_time, Date update_time) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.checked = checked;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
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

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
