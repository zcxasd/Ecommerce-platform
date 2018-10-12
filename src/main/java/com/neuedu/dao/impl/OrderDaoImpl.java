package com.neuedu.dao.impl;

import com.neuedu.dao.IOrderDao;
import com.neuedu.pojo.Order;
import com.neuedu.pojo.OrderItem;
import com.neuedu.pojo.PayInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderDaoImpl implements IOrderDao{
    @Autowired
    private SqlSession sqlSession;
    @Override
    public Order findOrderByOrderNoAndUserid(Long orderNo, Integer userid) {
        Map<String, Object> map = new HashMap<>();
        map.put("userid",userid);
        map.put("orderno",orderNo);
        return sqlSession.selectOne("com.neuedu.dao.IOrderDao.findOrderByOrderNoAndUserid",map);
    }

    @Override
    public Order findOrderByOrderNo(Long orderNo) {
        return sqlSession.selectOne("com.neuedu.dao.IOrderDao.findOrderByOrderNo",orderNo);
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderNo(Long orderNo) {
        return sqlSession.selectList("com.neuedu.dao.IOrderDao.findOrderItemsByOrderNo",orderNo);
    }

    @Override
    public Integer updateOrderStatusByOrderNo(Integer status, Long orderno) {
        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        map.put("orderno",orderno);
        return sqlSession.update("com.neuedu.dao.IOrderDao.updateOrderStatusByOrderNo",map);

    }

    @Override
    public Integer updateOrderStatusByOrderNoAndUserid(Integer status, Long orderno, Integer userid) {

        Map<String, Object> map = new HashMap<>();
        map.put("status",status);
        map.put("orderno",orderno);
        map.put("userid",userid);
        return sqlSession.update("com.neuedu.dao.IOrderDao.updateOrderStatusByOrderNoAndUserid",map);

    }

    @Override
    public Integer addPayInfo(PayInfo payInfo) {
        return sqlSession.insert("com.neuedu.dao.IOrderDao.addPayInfo",payInfo);
    }

    @Override
    public Integer addOrder(Order order) {
        return sqlSession.insert("com.neuedu.dao.IOrderDao.addOrder",order);
    }

    @Override
    public Integer batchInsertOrderItem(List<OrderItem> orderItemList) {
        return sqlSession.insert("com.neuedu.dao.IOrderDao.batchInsertOrderItem",orderItemList);
    }

    @Override
    public Integer sendOrder(Order order) {
        return sqlSession.update("com.neuedu.dao.IOrderDao.sendOrder",order);
    }

    @Override
    public List<Order> findOrderByPageNo(Integer pageNo, Integer pageSize) {
        Map<String,Integer> map = new HashMap<>();
        // 计算偏移量
        map.put("offSet",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        return  sqlSession.selectList("com.neuedu.dao.IOrderDao.findOrderByPageNo",map);
    }

    @Override
    public Integer findTotalRecord() {
        return sqlSession.selectOne("com.neuedu.dao.IOrderDao.findTotalRecord");
    }

    @Override
    public Order findOrderById(Long orderNo) {
        return sqlSession.selectOne("com.neuedu.dao.IOrderDao.findOrderById",orderNo);
    }

    @Override
    public List<Order> findOrderByPageNoAndUserId(Integer pageNo, Integer pageSize, Integer userid) {
        Map<String,Integer> map = new HashMap<>();
        // 计算偏移量
        map.put("offSet",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("userid",userid);
        return  sqlSession.selectList("com.neuedu.dao.IOrderDao.findOrderByPageNoAndUserId",map);
    }
}
