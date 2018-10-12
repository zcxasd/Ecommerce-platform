package com.neuedu.service.impl;

import com.neuedu.businessconst.Const;
import com.neuedu.common.BigDecimalUtils;
import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.ICartDao;
import com.neuedu.dao.IProductDao;
import com.neuedu.pojo.Cart;
import com.neuedu.pojo.Product;
import com.neuedu.service.ICartService;
import com.neuedu.vo.CartProductVo;
import com.neuedu.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IProductDao productDao;

    @Override
    public ServerResponse<CartVo> addProductToCart(Integer userid, Integer productid, int count) {
        Product product = productDao.findProductByIdAndOnline(productid);
        if (product==null){// 商品不存在或者已将下架
            return ServerResponse.createServerResponse(ResponseCode.PRODUCT_OFFLINE.getCode(),ResponseCode.PRODUCT_OFFLINE.getMsg());
        }
        //step1：根据userid和productid查询购物车cart
        Cart cart = cartDao.findCartByUseridAndProductid(userid,productid);
        //step2：cart null 添加 否则 更新
        if(cart==null){//添加
            Cart cart1 = new Cart(userid, productid, count, 1);
            cartDao.addProductToCart(cart1);
        }else{//更新
            /*Cart updateCart = new Cart();
            updateCart.setUser_id(userid);
            updateCart.setProduct_id(productid);*/
            cart.setQuantity(cart.getQuantity()+count);
            cartDao.updateCartByUseridAndProductid(cart);
        }
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);
    }

    @Override
    public ServerResponse<CartVo> findCartsByUserId(Integer userid) {
        //step1：根据userid查询用户购物车信息
        //List<Cart> cartList = new ArrayList<>();
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);
    }

    @Override
    public ServerResponse<CartVo> updateCartByUseridAndProductid(Integer userid, Integer productid, Integer quantity) {

        //step1：参数的非空验证
        if (productid==null||quantity==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        //step2：构造cart
        Cart cart = new Cart();
        cart.setUser_id(userid);
        cart.setProduct_id(productid);
        cart.setQuantity(quantity);
        cartDao.updateCartByUseridAndProductid(cart);
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);
    }

    @Override
    public ServerResponse<CartVo> deleteProductsFromCart(Integer userid, String productIds) {

        //step1：参数的非空验证
        if (productIds==null||productIds.equals("")){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        //step2：productIds 1,2,3
        String[] productidstrarry = productIds.split(",");
        List<Integer> productIdsList = new ArrayList<>();
        for(String productidStr:productidstrarry){
            Integer productid = Integer.parseInt(productidStr);
            productIdsList.add(productid);
        }
        cartDao.deleteProducts(productIdsList,userid);
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);
    }

    @Override
    public ServerResponse<CartVo> checkedProductByProductId(Integer userid, Integer productid) {
        //step1：参数的非空验证
        if (productid==null||productid.equals("")){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        cartDao.checkedProductByProductId(userid,productid);
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);
    }

    @Override
    public ServerResponse<CartVo> uncheckedProductByProductId(Integer userid, Integer productid) {
        //step1：参数的非空验证
        if (productid==null||productid.equals("")){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        cartDao.uncheckedProductByProductId(userid,productid);
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);

    }

    @Override
    public ServerResponse<Integer> findAllCartByProductNum(Integer userid) {

        //step1：参数的非空验证
        if (userid==null||userid.equals("")){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        int result = cartDao.findAllCartByProductNum(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),result);

    }

    @Override
    public ServerResponse<CartVo> isAllCheck(Integer userid) {
        cartDao.isAllCheck(userid);
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);
    }

    @Override
    public ServerResponse<CartVo> unIsAllCheck(Integer userid) {
        cartDao.unIsAllCheck(userid);
        CartVo cartVo = getCartVoLimit(userid);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),cartVo);

    }

    public CartVo getCartVoLimit(Integer userid) {
        CartVo cartVo = new CartVo();
        //step1：根据userid查询购物车商品集合
        List<Cart> cartList = cartDao.findCartByUserid(userid);
        //step2：将cartList转成List<CartProductVo>
        List<CartProductVo> cartProductVoList = new ArrayList<>();
        BigDecimal carttotalprice = new BigDecimal(0);
        if (cartList != null && cartList.size() > 0) {
            for (Cart cart : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                Product product = productDao.findProductById(cart.getProduct_id());

                cartProductVo.setUser_id(userid);

                cartProductVo.setProductid(cart.getProduct_id());
                cartProductVo.setName(product.getName());
                cartProductVo.setSubtitle(product.getSubtitle());
                cartProductVo.setMain_image(product.getMain_image());
                cartProductVo.setProductprice(product.getPrice());
                // 设置商品总价格
                cartProductVo.setTotalprice(BigDecimalUtils.multiply(product.getPrice(), new BigDecimal(cart.getQuantity())));

                if (cart.getChecked()==Const.CHECKENUM.CHECKED.getCode()){
                    carttotalprice = BigDecimalUtils.add(carttotalprice, cartProductVo.getTotalprice());
                }

                //carttotalprice = BigDecimalUtils.add(carttotalprice, cartProductVo.getTotalprice());
                // 校验库存
                if (product.getStock() > cart.getQuantity()) {//库存充足
                    //枚举
                    cartProductVo.setLimitQuantity(Const.STOCK.LIMIT_NUM_SUCCESS.getStockdesc());
                } else {
                    cartProductVo.setLimitQuantity(Const.STOCK.LIMIT_NUM_FALL.getStockdesc());
                    //更新购物车中商品的购买数量
                    Cart cart1 = new Cart();
                    cart1.setUser_id(userid);
                    cart1.setQuantity(product.getStock());
                    cart1.setProduct_id(product.getId());
                    cart1.setQuantity(product.getStock());
                    cartDao.updateCartByUseridAndProductid(cart1);
                }
                cartProductVo.setQuantity(cart.getQuantity());
                cartProductVo.setStatus(product.getStatus());
                cartProductVo.setCartid(cart.getId());
                //是否被勾选
                cartProductVo.setChecked(cart.getChecked());
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setIsallchecked(cartDao.isCheckAll(userid) == 0);
        cartVo.setTotalPrice(carttotalprice);
        return cartVo;
    }
}
