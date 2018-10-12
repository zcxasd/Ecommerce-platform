package com.neuedu;

import com.neuedu.dao.IProductDao;
import com.neuedu.dao.impl.ProductDaoImpl;
import com.neuedu.pojo.Product;
import org.junit.Test;

public class ProductDaoTest {


    @Test
    public void testFindProductById(){
        IProductDao iProductDao = new ProductDaoImpl();
        Product product = iProductDao.findProductById(10000);
        System.out.println(product);
    }
}
