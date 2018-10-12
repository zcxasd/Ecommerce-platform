package com.neuedu;

import com.neuedu.dao.ICategoryDao;
import com.neuedu.dao.impl.CategoryDaoImpl;
import com.neuedu.pojo.Category;
import org.junit.Test;

import java.util.List;

public class CategoryDaoTest {
    ICategoryDao categoryDao = new CategoryDaoImpl();
    @Test
    public void testFindCategoryById(){
        System.out.println(categoryDao.findCategoryById(10032));
    }

    @Test
    public void testFindSubCategoryByCategoryId(){
        List< Category> categoryList = categoryDao.findSubCategoryByCategoryId(10032);
        System.out.println(categoryList.size());
        System.out.println(categoryList);
    }

    @Test
    public void testAddCategory(){
       int result = categoryDao.addCategory(10033,"p20");
        System.out.println(result);
    }
}
