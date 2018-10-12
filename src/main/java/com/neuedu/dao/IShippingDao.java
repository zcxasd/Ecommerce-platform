package com.neuedu.dao;

import com.neuedu.pojo.Product;
import com.neuedu.pojo.Shipping;

import java.util.List;

public interface IShippingDao {

    /*添加地址*/
    public Integer addShipping(Shipping shipping);

    /*删除地址*/
    public Integer deleteShipping(Integer userid, Integer shippingId);

    /*登录状态下更新地址*/
    public Integer updateAddress(Shipping shipping);

    /*选中查看具体的地址*/
    public Shipping checkedShippingByShippingId(Integer userid, Integer shippingId);

    /*
     * 分页查询商品数据
     * pageNo：查询第几页
     *pageSize：每页查询的数量
     * */
    public List<Shipping> findShippingByPageNo(Integer pageNo, Integer pageSize);

    /*
     * 查询总的记录数
     * */
    public Long findTotalRecord1();
}
