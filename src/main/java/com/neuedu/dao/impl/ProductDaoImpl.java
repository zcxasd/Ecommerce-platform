package com.neuedu.dao.impl;

import com.neuedu.common.MyBatisUtil;
import com.neuedu.dao.IProductDao;
import com.neuedu.pojo.Product;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class ProductDaoImpl implements IProductDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public Product findProductById(int productId) {

        return sqlSession.selectOne("com.neuedu.dao.IProductDao.findProductById",productId);
    }

    @Override
    public int addProduct(Product product) {

        return sqlSession.insert("com.neuedu.dao.IProductDao.addProduct",product);

    }

    @Override
    public int updateProduct(Product product) {
        return sqlSession.update("com.neuedu.dao.IProductDao.updateProduct",product);
    }

    @Override
    public List<Product> findProductByPageNo(Integer pageNo, Integer pageSize) {

        Map<String,Integer> map = new HashMap<>();
        // 计算偏移量
        map.put("offSet",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        return  sqlSession.selectList("com.neuedu.dao.IProductDao.findProductByPageNo",map);
    }

    @Override
    public Long findTotalRecord() {

        return sqlSession.selectOne("com.neuedu.dao.IProductDao.findTotalRecord");

    }

    @Override
    public List<Product> findProductsByProductIdOrProductName(Integer productId, String productName,Integer pageNo,Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        // 计算偏移量
        map.put("productId",productId);
        map.put("productName",productName);
        map.put("offSet",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        return  sqlSession.selectList("com.neuedu.dao.IProductDao.findProductsByProductIdOrProductName",map);
    }

    @Override
    public Long findTotalRecord(Integer productId, String productName) {
        Map<String,Object> map = new HashMap<>();
        // 计算偏移量
        map.put("productId",productId);
        map.put("productName",productName);
        return sqlSession.selectOne("com.neuedu.dao.IProductDao.findTotalRecordbyidorname",map);
    }

    @Override
    public Product findProductByIdAndOnline(Integer productid) {
        return sqlSession.selectOne("com.neuedu.dao.IProductDao.findProductByIdAndOnline",productid);
    }

    @Override
    public List<Product> findProductsByCategoryIdsAndProductName(Set<Integer> categoryIds, String productName, Integer pageNo, Integer pageSize, int orderby) {

        Map<String,Object> map = new HashMap<>();
        map.put("categoryIds",categoryIds);
        map.put("productName",productName);
        map.put("pageNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("orderby",orderby);
        return sqlSession.selectList("com.neuedu.dao.IProductDao.findProductsByCategoryIdsAndProductName",map);
    }

    @Override
    public Long findTotalRecord(Set<Integer> categoryIds, String productName) {

        Map<String,Object> map = new HashMap<>();
        map.put("categoryIds",categoryIds);
        map.put("productName",productName);
        return sqlSession.selectOne("com.neuedu.dao.IProductDao.findTotalRecordByIdsAndProductName",map);
    }
}
