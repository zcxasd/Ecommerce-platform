package com.neuedu.pojo;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 类别实体类
 * */
public class Category implements Serializable{


     private int id ;//'类别id',
     private int parent_id    ;// '父类Id,当pareng_id=0,说明是根节点，一级类别',
     private List<Product> productList;// 一对多
     private String name    ;// '类别名称',
     private int status    ;// '类别状态1-正常，2-已废弃',
     private int sort_order      ;// '排序编号，同类展示顺序，数值相等则自然排序',
     private Date create_time ;// '创建时间',
     private Date update_time    ;// '最后一次更新时间',

    public Category() {

    }

    @PostConstruct
    public void init(){
        System.out.println("=====category init====");
    }

    public Category(int id, int parent_id, String name, int status, int sort_order, Date create_time, Date update_time) {
        this.id = id;
        this.parent_id = parent_id;
        this.name = name;
        this.status = status;
        this.sort_order = sort_order;
        this.create_time = create_time;
        this.update_time = update_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", parent_id=" + parent_id +
                ", productList=" + productList +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", sort_order=" + sort_order +
                ", create_time=" + create_time +
                ", update_time=" + update_time +
                '}';
    }
}
