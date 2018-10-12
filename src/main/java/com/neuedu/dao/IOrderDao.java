package com.neuedu.dao;

import com.neuedu.pojo.Order;
import com.neuedu.pojo.OrderItem;
import com.neuedu.pojo.PayInfo;
import com.neuedu.pojo.Product;

import java.util.List;
import java.util.Set;

public interface IOrderDao {

    /*查询订单信息*/
    public Order findOrderByOrderNoAndUserid(Long orderNo,Integer userid);

    /*查询订单信息*/
    public Order findOrderByOrderNo(Long orderNo);


    /*根据订单编号查询订单明细*/
    public List<OrderItem> findOrderItemsByOrderNo(Long orderNo);

    /*修改订单支付状态*/
    public Integer updateOrderStatusByOrderNo(Integer status,Long orderno);

    /*修改订单支付状态*/
    public Integer updateOrderStatusByOrderNoAndUserid(Integer status,Long orderno,Integer userid);


    /*添加支付信息*/
    public Integer addPayInfo(PayInfo payInfo);

    /*添加订单*/
    public Integer addOrder(Order order);

    /*批量插入订单明细*/
    public Integer batchInsertOrderItem(List<OrderItem> orderItemList);

    /*发货*/
    public Integer sendOrder(Order order);

    /*
     * 分页查询订单数据
     * pageNo：查询第几页
     *pageSize：每页查询的数量
     * */
    public List<Order> findOrderByPageNo(Integer pageNo, Integer pageSize);

    /*
     * 查询订单数量
     * */
    public Integer findTotalRecord();

    /*
     * 按订单号查询
     * */
    public Order findOrderById(Long orderNo);

    /*
     *前台
     * 分页查询订单数据
     * pageNo：查询第几页
     *pageSize：每页查询的数量
     * */
    public List<Order> findOrderByPageNoAndUserId(Integer pageNo, Integer pageSize,Integer userid);

    /*
     * 查询订单数量
     * */
    //public Integer findTotalRecord();

}
