package com.neuedu.service.impl;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ICategoryDao;
import com.neuedu.dao.impl.CategoryDaoImpl;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements ICategoryService {
    // spring ioc：容器(控制反转)：将创建对象的权利交给spring处理。 spring ioc对bean：结耦、管理bean的生命周期管理
    @Autowired
    ICategoryDao categoryDao;
    // 调用容器
    /*public CategoryServiceImpl(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        categoryDao = (ICategoryDao) applicationContext.getBean("categoryDaoImpl");
        CategoryDaoImpl categoryDao2 = (CategoryDaoImpl) applicationContext.getBean("categoryDaoImpl");

        System.out.println("========"+categoryDao2.getCategory());
        System.out.println("========"+(categoryDao==categoryDao2));
    }
*/
    @Override
    public ServerResponse<List<Category>> findSubCategoryById(int categoryId) {
        List<Category> categoryList = categoryDao.findSubCategoryByCategoryId(categoryId);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),categoryList);
    }

    @Override
    public ServerResponse<String> addCategory(int parent_id, String categoryName) {

        int result = categoryDao.addCategory(parent_id,categoryName);
        if (result>0){// 添加成功
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        }else{
            return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
        }
    }

    @Override
    public ServerResponse<String> updateCategoryByCategoryId(Integer categoryId, String categoryName) {

        if (categoryId==null){
            return ServerResponse.createServerResponse(ResponseCode.GETSUBCATEGORY_NEED_CATEGORYID.getCode(),ResponseCode.GETSUBCATEGORY_NEED_CATEGORYID.getMsg());
        }
        if (categoryName==null){
            return ServerResponse.createServerResponse(ResponseCode.GETSUBCATEGORY_NEED_CATEGORYNAME.getCode(),ResponseCode.GETSUBCATEGORY_NEED_CATEGORYNAME.getMsg());
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int result = categoryDao.updateCategoryByCategoryId(category);
        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        }else{
            return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
        }
    }

    @Override
    public Set<Integer> findAllChildByCategory(Set<Integer> categoryidList, Integer categoryId) {

        // 根据当前categoryId查询类别
        Category category = categoryDao.findCategoryById(categoryId);
        if (category!=null){
            categoryidList.add(categoryId);
        }
        // 查询当前categoryid的子节点
        List<Category> categoryList = categoryDao.findSubCategoryByCategoryId(categoryId);
        if (categoryidList!=null&&categoryidList.size()>0){
            // 获取子节点
            for (Category category1:categoryList){
                findAllChildByCategory(categoryidList,category1.getId());
            }
        }
        return categoryidList;
    }
}
