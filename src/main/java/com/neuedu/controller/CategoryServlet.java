/*
package com.neuedu.controller;

import com.google.gson.Gson;
import com.neuedu.common.ServerResponse;
import com.neuedu.exception.BusinessException;
import com.neuedu.pojo.Category;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/manage/cate.do")
public class CategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        String operation = request.getParameter("operation");
        if (operation==null||operation.equals("")){

            ServerResponse serverResponse = ServerResponse.createServerResponse(1,"operation参数必传!!!");
            ServerResponse.convertToJson(serverResponse,response);

            //throw BusinessException.createException(request.getSession(),"operation参数必须","3s后跳转到登录页面","login.jsp");
        }

        if (operation.equals("1")){// 查询子类别
            findSubCategory(request,response);
        }else if (operation.equals("2")){// 添加类别
            addCategory(request,response);
        }
    }

    public void findSubCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
            String categoryId = request.getParameter("categoryId");
            if (categoryId==null||categoryId.equals("")){
                //throw BusinessException.createException(request.getSession(),"categoryId参数必须","3s后跳转到登录页面","login.jsp");
                ServerResponse serverResponse = ServerResponse.createServerResponse(1,"categoryId参数必传!!!");
                ServerResponse.convertToJson(serverResponse,response);
            }
            ICategoryService categoryService = new CategoryServiceImpl();
            try {
                int _categoryId = Integer.parseInt(categoryId);
                List<Category> categoryList = categoryService.findSubCategoryById(_categoryId);
                ServerResponse serverResponse = ServerResponse.createServerResponse(0, categoryList);
                ServerResponse.convertToJson(serverResponse,response);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
    }
    public void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String parentId = request.getParameter("parentId");
        String categoryName = request.getParameter("categoryName");
        if (parentId==null||parentId.equals("")){
            //throw BusinessException.createException(request.getSession(),"categoryId参数必须","3s后跳转到登录页面","login.jsp");
            ServerResponse serverResponse = ServerResponse.createServerResponse(1,"parentId参数必传!!!");
            ServerResponse.convertToJson(serverResponse,response);
        }
        if (categoryName==null||categoryName.equals("")){
            ServerResponse serverResponse = ServerResponse.createServerResponse(2,"categoryName参数必传!!!");
            ServerResponse.convertToJson(serverResponse,response);
        }
        ICategoryService categoryService = new CategoryServiceImpl();
        try {
            int _parentId = Integer.parseInt(parentId);
            int result = categoryService.addCategory(_parentId,categoryName);
            if (result>0){// 添加成功
                ServerResponse serverResponse = ServerResponse.createServerResponse(0, "添加成功");
                ServerResponse.convertToJson(serverResponse,response);
            }else{
                ServerResponse serverResponse = ServerResponse.createServerResponse(0,"添加失败");
                ServerResponse.convertToJson(serverResponse,response);
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
    }
}
*/
