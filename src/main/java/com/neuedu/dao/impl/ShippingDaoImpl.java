package com.neuedu.dao.impl;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.IShippingDao;
import com.neuedu.pojo.Shipping;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import com.neuedu.vo.ShippingVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ShippingDaoImpl implements IShippingDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public Integer addShipping(Shipping shipping) {
        return sqlSession.insert("com.neuedu.dao.IShippingDao.addShipping",shipping);
    }

    @Override
    public Integer deleteShipping(Integer userid, Integer shippingId) {
        Map<String ,Integer> map = new HashMap<>();
        map.put("userid",userid);
        map.put("shippingId",shippingId);
        return sqlSession.delete("com.neuedu.dao.IShippingDao.deleteShipping",map);
    }

    @Override
    public Integer updateAddress(Shipping shipping) {
        return sqlSession.update("com.neuedu.dao.IShippingDao.updateAddress",shipping);
    }

    @Override
    public Shipping checkedShippingByShippingId(Integer userid, Integer shippingId) {
        Map<String ,Integer> map = new HashMap<>();
        map.put("userid",userid);
        map.put("shippingId",shippingId);
        return sqlSession.selectOne("com.neuedu.dao.IShippingDao.checkedShippingByShippingId",map);
    }

    @Override
    public List<Shipping> findShippingByPageNo(Integer pageNo, Integer pageSize) {
        Map<String,Integer> map = new HashMap<>();
        // 计算偏移量
        map.put("offSet",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        return  sqlSession.selectList("com.neuedu.dao.IShippingDao.findShippingByPageNo",map);

    }

    @Override
    public Long findTotalRecord1() {
        return sqlSession.selectOne("com.neuedu.dao.IShippingDao.findTotalRecord1");
    }
}
