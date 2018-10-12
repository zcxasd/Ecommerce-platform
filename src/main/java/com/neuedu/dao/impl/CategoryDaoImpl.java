package com.neuedu.dao.impl;

import com.neuedu.common.MyBatisUtil;
import com.neuedu.dao.ICategoryDao;
import com.neuedu.pojo.Category;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Scope("singleton")
public class CategoryDaoImpl implements ICategoryDao {

    @Autowired
    public SqlSession sqlSession;

    //setter注入
    private int categoryId;

    /*@Autowired
    @Qualifier("category1")*/
    @Resource(name = "category1")
    private Category category;

   /* public void setCategory(Category category) {
        this.category = category;
    }*/
    public Category getCategory() {
        return category;
    }

   /* public CategoryDaoImpl(Category category){
        System.out.println("======CategoryDaoImpl(Category category)=======");
        this.category=category;
    }*/
    // 构造器注入
    public CategoryDaoImpl(int categoryId){
        this.categoryId=categoryId;
    }

   /* public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }*/
    public int getCategoryId() {
        return categoryId;
    }

    public  CategoryDaoImpl(){
        System.out.println("===CategoryDaoImpl 构造方法===");
    }
    /**
     * bean的初始化
     * */
    @PostConstruct
   public void init(){
        System.out.println("===CategoryDaoImpl init===");
    }

    /**
     * bean的销毁
     * */
    @PreDestroy
    public void destory(){
        System.out.println("===CategoryDaoImpl destory===");
    }

    @Override
    public Category findCategoryById(int category) {
        /*SqlSession sqlSession = MyBatisUtil.getSqlSession();*/
        return sqlSession.selectOne("com.neuedu.dao.ICategoryDao.findCategoryById",category);
    }

    @Override
    public List<Category> findSubCategoryByCategoryId(int categoryId) {
        /*SqlSession sqlSession = MyBatisUtil.getSqlSession();*/
        return sqlSession.selectList("com.neuedu.dao.ICategoryDao.findSubCategoryByCategoryId",categoryId);
    }

    @Override
    public int addCategory(int parent_id, String categoryName) {
        /*SqlSession sqlSession = MyBatisUtil.getSqlSession();*/
        Map<String,Object> map = new HashMap<>();
        map.put("parent_id",parent_id);
        map.put("categoryName",categoryName);
        int result = sqlSession.insert("com.neuedu.dao.ICategoryDao.addCategory",map);
        //sqlSession.commit();
        return result;
    }

    @Override
    public int updateCategoryByCategoryId(Category category) {
        return sqlSession.update("com.neuedu.dao.ICategoryDao.updateCategoryByCategoryId",category);
    }
}
