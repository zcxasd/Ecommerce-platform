package com.neuedu.service.impl;

import com.neuedu.businessconst.Const;
import com.neuedu.common.DateUtils;
import com.neuedu.common.PropertiesUtils;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ICategoryDao;
import com.neuedu.dao.IProductDao;
import com.neuedu.pojo.Category;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICategoryService;
import com.neuedu.service.IProductService;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductDao productDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private ICategoryService categoryService;

    @Override
    public ServerResponse<String> addorupdateProduct(Product product) {

        if (product==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        /*
        sub_images（数组的方式传过来） 1.jpg，2.jpg，3.jpg（取第一个逗号前的子图作为主图）
        * 获取子图
        * */
        String subimages = product.getSub_images();
        if (subimages!=null&&!subimages.equals("")){
            String[] images = subimages.split(",");
            if (images!=null&&images.length>0){
                product.setMain_image(images[0]);
            }
        }
        Integer productId = product.getId();
        int result=0;
        if (productId==null){
            // 添加
            result = productDao.addProduct(product);
        }else{
            result = productDao.updateProduct(product);
        }
        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        }else{
            return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
        }
    }

    @Override
    public ServerResponse<String> onlineoroffline(Integer productId, Integer status) {

        if (productId==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        if (status==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT_STATUS.getCode(),ResponseCode.NEED_PRODUCT_STATUS.getMsg());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int result = productDao.updateProduct(product);
        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        }else{
            return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
        }
    }

    @Override
    public ServerResponse<ProductVo> findProductById(Integer productId) {
        if (productId==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        // pojo
        Product product = productDao.findProductById(productId);
        // vo
        ProductVo productVo = assembleProduct(product);

        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),productVo);
    }

    @Override
    public ServerResponse<PageModel<ProductVo>> findProductByPageNo(Integer pageNo, Integer pageSize) {
        List<Product> productList = productDao.findProductByPageNo(pageNo,pageSize);

        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product:productList){
            productVoList.add(assembleProduct(product));
        }

        long totalRecord = productDao.findTotalRecord();
        long totalpage = (totalRecord%pageSize==0?totalRecord/pageSize:(totalRecord/pageSize)+1);
        PageModel<ProductVo> pageModel = new PageModel();
        pageModel.setData(productVoList);
        pageModel.setTotalPage(totalpage);
        if (pageNo==1){
            pageModel.setFirst(true);
        }else{
            pageModel.setFirst(false);
        }
        if (pageNo==totalpage){
            pageModel.setLast(true);
        }else{
            pageModel.setLast(false);
        }
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),pageModel);
}

    @Override
    public ServerResponse<PageModel<ProductVo>> findProductsByProductIdOrProductName(Integer productId, String productName, Integer pageNo, Integer pageSize) {

        if (productName!=null&&!productName.equals("")){
            productName="%"+productName+"%";
        }

        List<Product> productList = productDao.findProductsByProductIdOrProductName(productId,productName,pageNo,pageSize);
        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product:productList){
            productVoList.add(assembleProduct(product));
        }

        //PageModel<ProductVo> productVoPageModel = new PageModel<>();

        long totalRecord = productDao.findTotalRecord(productId,productName);
        long totalpage = (totalRecord%pageSize==0?totalRecord/pageSize:(totalRecord/pageSize)+1);
        PageModel<ProductVo> pageModel = new PageModel();
        pageModel.setData(productVoList);
        pageModel.setTotalPage(totalpage);
        if (pageNo==1){
            pageModel.setFirst(true);
        }else{
            pageModel.setFirst(false);
        }
        if (pageNo==totalpage){
            pageModel.setLast(true);
        }else{
            pageModel.setLast(false);
        }
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),pageModel);
    }

    @Override
    public ServerResponse<String> upload(MultipartFile upload) {
        // 重新生成文件名 UUID+扩展名--》保存到本地
        if (upload!=null) {
            // step1：获取原文扩展名件
            String originFilename = upload.getOriginalFilename();
            if (originFilename != null && !originFilename.equals("")) {
                int index = originFilename.lastIndexOf('.');
                String extendname = originFilename.substring(index); // [)
                // step2：生成新的文件名
                String uuid = java.util.UUID.randomUUID().toString();
                String newFilename = uuid + extendname; // aasdas.jsp
                // step3：把生成好的图片保存到你想要的路径下
                String filePath = "D:\\ftpfile\\pay";
                File file = new File(filePath, newFilename);
                try {
                    upload.transferTo(file);
                    return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),newFilename);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
    }

    @Override
    public ServerResponse<ProductVo> findProductDetail(Integer productId) {

        // 参数校验
        if (productId==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        // 根据商品id查询商品
        Product product = productDao.findProductByIdAndOnline(productId);
        if (product==null){// 商品不存在或者已将下架
            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_OFFLINE.getCode(),ResponseCode.PRODUCT_OFFLINE.getMsg());
        }

        ProductVo productVo = assembleProduct(product);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),productVo);
    }

    @Override
    public ServerResponse<PageModel<ProductVo>> searchProducts(Integer categoryId, String productName, Integer pageNo, Integer pageSize,String orderBy) {
        //step1: 参数校验
        if (categoryId==null&&(productName==null||productName.equals(""))){
            return ServerResponse.createServerResponse(Const.ProductCode.ILLEGAL_PARAM.getCode(),Const.ProductCode.ILLEGAL_PARAM.getMsg());
        }
        Set<Integer> set = new HashSet<>();
        //step2: 如果categoryId不为空，根据categoryId查询类别信息
        if (categoryId!=null){
            Category category = categoryDao.findCategoryById(categoryId);
            //step2.1：如果category为空，而且productName也为空（没有商品）
            if (category==null&&(productName==null||productName.equals(""))){
                // 没有查询到商品
                PageModel<ProductVo> pageModel = new PageModel<>();
                pageModel.setData(null);
                pageModel.setCurrentPage(pageNo);
                pageModel.setTotalPage(0L);
                pageModel.setFirst(false);
                pageModel.setLast(false);
                return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),pageModel);
            }
            //step2.2：category不为空，需要递归查询子类别，list<categoryid> from product categoryid in (categoryid),
            set = categoryService.findAllChildByCategory(set,category.getId());
        }
        //step3：判断productName，如果productName不为空 %productName%
        if (productName!=null&&!productName.equals("")){
            // 模糊查询
            productName="%"+productName+"%";
        }
        //step4判断排序字段是否为空 price_asc/desc
        int sortnum = 0;
        if (orderBy!=null&&!orderBy.equals("")){
            String[] orderbyarr = orderBy.split("_");
            if (orderbyarr!=null&&orderbyarr.length>1){
                String orderby = orderbyarr[1];
                if (orderby.equals("desc")){
                    sortnum=2;
                }else if (orderby.equals("asc")){
                    sortnum=1;
                }
            }
        }
        //step5：调用dao层
        List<Product> productList = productDao.findProductsByCategoryIdsAndProductName(set,productName,pageNo,pageSize,sortnum);

        List<ProductVo> productVoList = new ArrayList<>();
        for (Product product:productList){
            productVoList.add(assembleProduct(product));
        }

        //PageModel<ProductVo> productVoPageModel = new PageModel<>();

        long totalRecord = productDao.findTotalRecord(set,productName);
        long totalpage = (totalRecord%pageSize==0?totalRecord/pageSize:(totalRecord/pageSize)+1);
        PageModel<ProductVo> pageModel = new PageModel();
        pageModel.setData(productVoList);
        pageModel.setTotalPage(totalpage);
        if (pageNo==1){
            pageModel.setFirst(true);
        }else{
            pageModel.setFirst(false);
        }
        if (pageNo==totalpage){
            pageModel.setLast(true);
        }else{
            pageModel.setLast(false);
        }
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),pageModel);
    }

    public ProductVo assembleProduct(Product product){
        ProductVo productVo = new ProductVo();
        productVo.setId(product.getId());
        productVo.setCategory_id(product.getCategory_id());
        productVo.setName(product.getName());
        productVo.setSubtitle(product.getSubtitle());
        productVo.setDetail(product.getDetail());
        productVo.setMain_image(product.getMain_image());
        productVo.setSub_images(product.getSub_images());
        productVo.setPrice(product.getPrice());
        productVo.setStock(product.getStock());
        productVo.setStatus(product.getStatus());
        productVo.setImageHost(PropertiesUtils.getProperty("imageHost"));

        // 时间（商品）设置
        productVo.setCreate_time(DateUtils.dateToString(product.getCreate_time(),"yyyy-MM-dd HH:mm:ss"));
        productVo.setUpdate_time(DateUtils.dateToString(product.getUpdate_time()));
        return productVo;
    }


}
