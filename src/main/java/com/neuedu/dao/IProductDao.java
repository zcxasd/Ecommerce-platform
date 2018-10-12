package com.neuedu.dao;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IProductDao {

    /*
    * 根据商品id查询商品信息
    * */
    public Product findProductById(int productId);

    /*
     * 添加商品
     * */
    public int addProduct(Product product);

    /*
     *更新商品
     * */
    public int updateProduct(Product product);

    /*
    * 分页查询商品数据
    * pageNo：查询第几页
    *pageSize：每页查询的数量
    * */
    public List<Product> findProductByPageNo(Integer pageNo,Integer pageSize);

    /*
    * 查询总的记录数
    * */
    public Long findTotalRecord();

    /*
    * 后台，商品搜索接口
    * */
    public List<Product> findProductsByProductIdOrProductName(Integer productId,String productName,Integer pageNO,Integer pageSize);

    /*
    * 根据商品id或商品名称获取商品总数量
    * */
    public Long findTotalRecord(Integer productId,String productName);

    /*根据productid查询在售商品详情*/
    public Product findProductByIdAndOnline(Integer productid);

    /*根据类别集合和商品名称查询商品信息
    * orderby 1:升序 2：降序 0：没有排序
    * */
    public List<Product> findProductsByCategoryIdsAndProductName(Set<Integer> categoryIds,String productName,Integer pageNo,Integer pageSize,int orderby);

    /*
     * 根据类别ids或商品名称获取商品总数量
     * */
    public Long findTotalRecord(Set<Integer> categoryIds,String productName);

}
