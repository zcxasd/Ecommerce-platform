package com.neuedu.vo;

import com.neuedu.pojo.Category;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductVo implements Serializable{

    private Integer id ;//'商品id',
    private Integer category_id    ;// 类别id，对应neuedu_category表的主键
    private String name    ;// '商品名称',
    private String subtitle    ;// 商品副标题
    private String imageHost; // 显示图片的主机地址
    private String main_image    ;// 产品主图，url相对地址
    private String sub_images    ;// 图片地址，json格式
    private String detail    ;// 商品详情
    private BigDecimal price;//价格，单位-元保留两位小数
    private Integer stock      ;// 库存数量
    private Integer status      ;// 商品状态，1-在售 2-下架 3-删除
    private String create_time ;// '创建时间',
    private String update_time    ;// '最后一次更新时间'

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
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

    public String getImageHost() {
        return imageHost;
    }

    public void setImageHost(String imageHost) {
        this.imageHost = imageHost;
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "ProductVo{" +
                "id=" + id +
                ", category_id=" + category_id +
                ", name='" + name + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", imageHost='" + imageHost + '\'' +
                ", main_image='" + main_image + '\'' +
                ", sub_images='" + sub_images + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", status=" + status +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
