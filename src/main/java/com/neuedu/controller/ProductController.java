package com.neuedu.controller;

import com.neuedu.businessconst.Const;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.IProductDao;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.UserInfo;
import com.neuedu.service.IProductService;
import com.neuedu.service.IUserService;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping(value = "/manage/product")
public class ProductController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProductService productService;
    /*
    * 添加或更新商品
    * */
    @RequestMapping("/add")
    public ServerResponse<String> addProduct(Product product,HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return productService.addorupdateProduct(product);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
        //return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),product);
    }

    /*
    * 商品上下架
    * */
    @RequestMapping("/onlineoroffline")
    public ServerResponse<String> onlineOrOffline(Integer productId,Integer status,HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return productService.onlineoroffline(productId,status);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

    /*
    * 商品详情
    * */
    @RequestMapping(value = "/detail")
    public ServerResponse<ProductVo> productDetail(Integer productId, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return productService.findProductById(productId);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

    /*
     * 分页查询商品
     * */
    @RequestMapping(value = "/findproductbypageno")
    public ServerResponse<PageModel<ProductVo>> findProductByPageNo(@RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return productService.findProductByPageNo(pageNo,pageSize);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

    /*
     * 分页搜索商品
     * */
    @RequestMapping(value = "/search")
    public ServerResponse<PageModel<ProductVo>> search(@RequestParam(required = false) Integer productid,
                                                       @RequestParam(required = false) String productname,
                                                       @RequestParam(name = "pageNo",defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize",defaultValue = "10") Integer pageSize, HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return productService.findProductsByProductIdOrProductName(productid,productname,pageNo,pageSize);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }

    // 图片上传
    @RequestMapping(value = "/upload")
    public ServerResponse<String> search(MultipartFile upload,HttpSession session){
        // 判断用户是否登录
        UserInfo userInfo = (UserInfo)session.getAttribute(Const.CURRENTUSER);
        if (userInfo==null){// 没有登录
            return ServerResponse.createServerResponse(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getMsg());
        }
        // 判断用户是否有权限
        if (userService.isAdminRole(userInfo)){
            // 有管理员权限，可以添加类别
            return productService.upload(upload);
        }else{
            return ServerResponse.createServerResponse(ResponseCode.NO_PERMISSION.getCode(),ResponseCode.NO_PERMISSION.getMsg());
        }
    }
}
