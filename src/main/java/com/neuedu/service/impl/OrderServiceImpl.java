package com.neuedu.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.MonitorHeartbeatSynResponse;
import com.alipay.demo.trade.DemoHbRunner;
import com.alipay.demo.trade.Main;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.*;
import com.alipay.demo.trade.model.hb.*;
import com.alipay.demo.trade.model.hb.Product;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.model.result.AlipayF2FRefundResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.Utils;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.neuedu.businessconst.Const;
import com.neuedu.common.*;
import com.neuedu.dao.ICartDao;
import com.neuedu.dao.IOrderDao;
import com.neuedu.dao.IProductDao;
import com.neuedu.dao.IShippingDao;
import com.neuedu.pojo.*;
import com.neuedu.service.IOrderService;
import com.neuedu.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Autowired
    private ICartDao cartDao;

    @Autowired
    private IProductDao productDao;

    @Autowired
    private IShippingDao shippingDao;

    @Override
    public ServerResponse pay(Long orderNo,Integer userid) {
        //测试当面付2.0生成支付二位码
        /*test_trade_precreate();*/

        /*参数的非空校验*/
        if (orderNo==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_ORDERNO.getCode(),ResponseCode.NEED_ORDERNO.getMsg());
        }
        //step1：根据订单号和用户id查询订单信息
        Order order = orderDao.findOrderByOrderNoAndUserid(orderNo,userid);
        if (order==null){//没有查询到订单
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        //step2：支付
        // 测试当面付2.0生成支付二维码
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
            String outTradeNo = String.valueOf(order.getOrder_no());

            // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
            String subject = "订单号"+order.getOrder_no()+"消费"+order.getPayment()+"元";

            // (必填) 订单总金额，单位为元，不能超过1亿元
            // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
            String totalAmount = String.valueOf(order.getPayment().intValue());

            // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
            // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
            String undiscountableAmount = "0";

            // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
            // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
            String sellerId = "";

            // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
            String body = "订单号"+order.getOrder_no()+"消费"+order.getPayment()+"元";

            // 商户操作员编号，添加此参数可以为商户操作员做销售统计
            String operatorId = "test_operator_id";

            // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
            String storeId = "test_store_id";

            // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId("2088100200300400500");

            // 支付超时，定义为120分钟
            String timeoutExpress = "120m";

            // 商品明细列表，需填写购买商品详细信息，
            List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

            //根据订单编号查询订单明细
            List<OrderItem> orderItemList = orderDao.findOrderItemsByOrderNo(orderNo);
            for (OrderItem orderItem:orderItemList){
                GoodsDetail goods = GoodsDetail.newInstance(String.valueOf(orderItem.getProduct_id()),
                        orderItem.getProduct_name(), BigDecimalUtils.multiply(orderItem.getTotal_price(),new BigDecimal(100)).longValue(), orderItem.getQuantity());
                goodsDetailList.add(goods);
        }

            // 创建扫码支付请求builder，设置请求参数
            AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                    .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                    .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                    .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                    .setTimeoutExpress(timeoutExpress)
                    //支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                    .setNotifyUrl("http://e4jwd4.natappfree.cc/BusinessProject/order/callback.do")
                    .setGoodsDetailList(goodsDetailList);

            AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    log.info("支付宝预下单成功: )");

                    AlipayTradePrecreateResponse response = result.getResponse();
                    dumpResponse(response);

                    // 需要修改为运行机器上的路径
                    String filePath = String.format("D:/ftpfile/pay/qr-%s.png",
                            response.getOutTradeNo());
                    log.info("filePath:" + filePath);
                    ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                    return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),String.format("http://localhost:8080/uploadpic/qr-%s.png",
                            response.getOutTradeNo()));

                case FAILED:
                    log.error("支付宝预下单失败!!!");
                    return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());

                case UNKNOWN:
                    log.error("系统异常，预下单状态未知!!!");
                    return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());

                default:
                    log.error("不支持的交易状态，交易返回异常!!!");
                    return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
            }
    }

    @Override
    public String alipaycallback(Map<String, String> map) {

        //step1：获取订单号，查询订单
        String orderno = map.get("out_trade_no");
        String trade_status = map.get("trade_status");
        String trade_no = map.get("trade_no");
        Order order = orderDao.findOrderByOrderNo(Long.parseLong(orderno));
        //step2：获取支付状态，修改订单表中的订单状态
        if (order!=null){
            //支付成功
            if (trade_status.equals(Const.ALIPAYSTATUS.TRADE_SUCCESS.getMsg())){
                orderDao.updateOrderStatusByOrderNo(20,Long.parseLong(orderno));
            }
        }
        //step3：将支付信息保存
        PayInfo payInfo = new PayInfo();
        //设置订单号
        payInfo.setOrder_no(Long.parseLong(orderno));
        payInfo.setUser_id(order.getUser_id());
        //支付平台
        payInfo.setPay_platform(Const.PAYPLATFORM.ALIPAY.getCode());
        //支付status
        payInfo.setPlatform_status(trade_status);
        //支付宝流水号
        payInfo.setPlatform_number(trade_no);
        orderDao.addPayInfo(payInfo);
        return "success";
    }

    @Override
    public ServerResponse query_order_status(Long orderNo) {
        Order order = orderDao.findOrderByOrderNo(orderNo);
        //step2：获取支付状态，修改订单表中的订单状态
        if (order==null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        if (order.getStatus()>=Const.ALIPAYSTATUS.TRADE_SUCCESS.getCode()){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),true);
        }
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),false);
    }

    @Override
    public ServerResponse createOrder(Integer shippingId, Integer userid) {
        //step1：去购物车查询已选中的商品 List<Cart>
        List<Cart> cartList = cartDao.findCheckedCartsByUserId(userid);
        //step2：List<Cart> ===>List<OrderItem>
        ServerResponse serverResponse = getOrderItemList(cartList,userid);
        if (serverResponse.getStatus()!=ResponseCode.SUCCESS.getCode()){
            //订单失败
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        //step3：创建Order---->insert
        Order order = getOrder(userid,shippingId);
        order.setPayment(getOrderPrice(orderItemList));

        orderDao.addOrder(order);
        //step4：批量插入List<OrderItem>
        for (OrderItem orderItem:orderItemList){
            orderItem.setOrder_no(order.getOrder_no());
        }
        orderDao.batchInsertOrderItem(orderItemList);

        //step5：扣库存
        reduceStock(cartList);
        //step6：清空购物车
        cartDao.removeCheckedProduct(cartList,userid);
        //step7：返回数据OrderVo
        OrderVo orderVo = assembleOrderVo(order);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderVo);
    }

    @Override
    public ServerResponse cancelOrder(Long orderNo,Integer userid) {
        if (orderNo==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_ORDERNO.getCode(),ResponseCode.NEED_ORDERNO.getMsg());
        }
        //step1：根据订单号查询Order
        Order order = orderDao.findOrderByOrderNoAndUserid(orderNo,userid);
        if (order!=null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        //step2：已支付的订单不能取消
        if (order.getStatus()!=Const.ORDERSTATUSENUM.ORDER_NO_PAY.getCode()){//只有未付款的订单才能取消
            return ServerResponse.createServerResponse(ResponseCode.ORDER_NOT_CANCEL.getCode(),ResponseCode.ORDER_NOT_CANCEL.getMsg());
        }
        //step3：取消订单
        int result = orderDao.updateOrderStatusByOrderNoAndUserid(Const.ORDERSTATUSENUM.ORDER_CANCELLED.getCode(),orderNo,userid);
        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        }
        return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
    }

    /*获取订单中的商品信息*/
    @Override
    public ServerResponse getCartProductInfo(Integer userid) {
        //step1：userid查询购物车已选中商品
        List<Cart> cartList = cartDao.findCheckedCartsByUserId(userid);
        if (cartList==null||cartList.size()==0){
            return ServerResponse.createServerResponse(ResponseCode.CARE_NOT_FOUND_PRODUCT.getCode(),ResponseCode.CARE_NOT_FOUND_PRODUCT.getMsg());
        }
        //step2：List<Cart> -->List<OrderItem>
        ServerResponse serverResponse = getOrderItemList(cartList,userid);
        if (serverResponse.getStatus()!=ResponseCode.SUCCESS.getCode()){
            return serverResponse;
        }
        //step3：List<OrderItem> -->List<OrderItemVo>
        List<OrderItem> orderItemList = (List<OrderItem>) serverResponse.getData();
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (OrderItem orderItem:orderItemList){
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        //step4：返回数据 OrderProductVo
        OrderProductVo orderProductVo = new OrderProductVo();
        orderProductVo.setImageHost(PropertiesUtils.getProperty("imageHost"));
        orderProductVo.setOrderItemVoList(orderItemVoList);
        //总价格
        orderProductVo.setProductTotalPrice(getOrderPrice(orderItemList));
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderProductVo);
    }

    @Override
    public ServerResponse getOrderDetail(Long orderNo, Integer userid) {

        //step1：orderNo-->Order
        if (orderNo==null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        Order order = orderDao.findOrderByOrderNo(orderNo);
        if (order==null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        //step2：Order-->OrderVo
        OrderVo orderVo = assembleOrderVo(order);
        //step1：返回
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderVo);
    }

    @Override
    public ServerResponse sendOrder(Long orderNo) {
        //校验
        if(orderNo==null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        //查询订单（order）
        Order order = orderDao.findOrderByOrderNo(orderNo);
        if(order==null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        //修改订单的status和send_time
        Order order1 = new Order();
        order1.setStatus(Const.ORDERSTATUSENUM.ORDER_SEND.getCode());
        order1.setOrder_no(orderNo);
        Integer result = orderDao.sendOrder(order1);
        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        }
        //返回数据
        return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
    }

    @Override
    public ServerResponse<PageModel<OrderVo>> findOrderByPageNo(Integer pageNo, Integer pageSize) {
        List<Order> orderList = orderDao.findOrderByPageNo(pageNo,pageSize);

        List<OrderVo> orderVoList = new ArrayList<>();

        for (Order order:orderList){
            orderVoList.add(assembleOrderVo(order));
        }

        long totalRecord = orderDao.findTotalRecord();
        long totalpage = (totalRecord%pageSize==0?totalRecord/pageSize:(totalRecord/pageSize)+1);
        PageModel<OrderVo> orderVoPageModel = new PageModel();
        orderVoPageModel.setData(orderVoList);
        orderVoPageModel.setTotalPage(totalpage);
        if (pageNo==1){
            orderVoPageModel.setFirst(true);
        }else{
            orderVoPageModel.setFirst(false);
        }
        if (pageNo==totalpage){
            orderVoPageModel.setLast(true);
        }else{
            orderVoPageModel.setLast(false);
        }
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderVoPageModel);
    }

    @Override
    public ServerResponse<OrderVo> findOrderById(Long orderNo) {
        if (orderNo==null){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        // pojo
        Order order = orderDao.findOrderById(orderNo);
        // vo
        OrderVo orderVo = assembleOrderVo(order);

        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderVo);
    }

    @Override
    public ServerResponse getOrderDetail(Long orderNo) {
        //step1：orderNo-->Order
        if (orderNo==null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        Order order = orderDao.findOrderByOrderNo(orderNo);
        if (order==null){
            return ServerResponse.createServerResponse(ResponseCode.NOT_FOUNT_ORDERNO.getCode(),ResponseCode.NOT_FOUNT_ORDERNO.getMsg());
        }
        //step2：Order-->OrderVo
        OrderVo orderVo = assembleOrderVo(order);
        //step1：返回
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderVo);

    }

    @Override
    public ServerResponse<PageModel<OrderVo>> findOrderByPageNoAndUserId(Integer pageNo, Integer pageSize, Integer userid) {
        List<Order> orderList = orderDao.findOrderByPageNoAndUserId(pageNo,pageSize,userid);

        List<OrderVo> orderVoList = new ArrayList<>();

        for (Order order:orderList){
            orderVoList.add(assembleOrderVo(order));
        }

        long totalRecord = orderDao.findTotalRecord();
        long totalpage = (totalRecord%pageSize==0?totalRecord/pageSize:(totalRecord/pageSize)+1);
        PageModel<OrderVo> orderVoPageModel = new PageModel();
        orderVoPageModel.setData(orderVoList);
        orderVoPageModel.setTotalPage(totalpage);
        if (pageNo==1){
            orderVoPageModel.setFirst(true);
        }else{
            orderVoPageModel.setFirst(false);
        }
        if (pageNo==totalpage){
            orderVoPageModel.setLast(true);
        }else{
            orderVoPageModel.setLast(false);
        }
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderVoPageModel);
    }

    /*获取OrderVo*/
    private OrderVo assembleOrderVo(Order order){
        OrderVo orderVo = new OrderVo();
        orderVo.setOrder_no(order.getOrder_no());
        orderVo.setPayment(order.getPayment());
        orderVo.setPayment_type(Const.PAYONLINEOROFFLINE.ONLINE_PAY.getCode());
        orderVo.setPaymentTypeDesc(Const.PAYONLINEOROFFLINE.OFFLINE_PAY.getMsg());
        orderVo.setPostage(0);
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.ORDERSTATUSENUM.codeOf(order.getStatus())!=null?Const.ORDERSTATUSENUM.codeOf(order.getStatus()).getMsg():"");
        orderVo.setPayment_time(DateUtils.dateToString(order.getPayment_time()));
        orderVo.setSend_time(DateUtils.dateToString(order.getSend_time()));
        orderVo.setClose_time(DateUtils.dateToString(order.getClose_time()));
        orderVo.setCreate_time(DateUtils.dateToString(order.getCreate_time()));
        orderVo.setImageHost(PropertiesUtils.getProperty("imageHost"));
        orderVo.setShipping_id(order.getShipping_id());
        //zijixie
        //根据shippingid查询shipping，并转成shippingVo
        ShippingVo shippingVo = assembleShippingVo(order.getShipping_id(),order.getUser_id());
        orderVo.setReceiverName(shippingVo.getReceiver_name());
        orderVo.setShippingVo(shippingVo);
        //根据order_no查询List<OrderItem>
        List<OrderItem> orderItemList = orderDao.findOrderItemsByOrderNo(order.getOrder_no());
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (OrderItem orderItem:orderItemList){
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    public OrderItemVo assembleOrderItemVo(OrderItem orderItem){
       OrderItemVo orderItemVo = new OrderItemVo();
       orderItemVo.setCreate_time(DateUtils.dateToString(orderItem.getCreate_time()));
       orderItemVo.setCurrent_unit_price(orderItem.getCurrent_unit_price());
       orderItemVo.setOrder_no(orderItem.getOrder_no());
       orderItemVo.setProduct_id(orderItem.getProduct_id());
       orderItemVo.setProduct_image(orderItem.getProduct_image());
       orderItemVo.setQuantity(orderItem.getQuantity());
       orderItemVo.setTotal_price(orderItem.getTotal_price());
       return orderItemVo;
    }

    private ShippingVo assembleShippingVo(Integer shippingid,Integer userid){
        ShippingVo shippingVo = new ShippingVo();
        Shipping shipping = shippingDao.checkedShippingByShippingId(userid,shippingid);
        shippingVo.setUser_id(shipping.getUser_id());
        shippingVo.setReceiver_name(shipping.getReceiver_name());
        shippingVo.setReceiver_phone(shipping.getReceiver_phone());
        shippingVo.setReceiver_mobile(shipping.getReceiver_mobile());
        shippingVo.setReceiver_province(shipping.getReceiver_province());
        shippingVo.setReceiver_city(shipping.getReceiver_city());
        shippingVo.setReceiver_district(shipping.getReceiver_district());
        shippingVo.setReceiver_address(shipping.getReceiver_address());
        shippingVo.setReceiver_zip(shipping.getReceiver_zip());
        return shippingVo;
    }


    /*扣库存*/
    public void reduceStock(List<Cart> cartList){
        for (Cart cart:cartList){
            com.neuedu.pojo.Product product = productDao.findProductById(cart.getProduct_id());
            com.neuedu.pojo.Product newProduct = new com.neuedu.pojo.Product();
            newProduct.setStock(product.getStock()-cart.getQuantity());
            newProduct.setId(cart.getProduct_id());
            productDao.updateProduct(newProduct);
        }
    }

    /*创建订单实体类*/
    private Order getOrder(Integer userid,Integer shippingid){
        Order order = new Order();
        order.setUser_id(userid);
        order.setShipping_id(shippingid);
        order.setOrder_no(generateOrderNo());
        order.setPayment_type(Const.PAYPLATFORM.ALIPAY.getCode());
        order.setPostage(0);
        order.setStatus(Const.ALIPAYSTATUS.WAIT_BUYER_PAY.getCode());
        return order;
    }

    /*生成订单号*/
    private Long generateOrderNo(){

        Long time = System.currentTimeMillis();

        return time+new Random().nextInt(100);
    }

    /*计算订单的总价格*/
    private BigDecimal getOrderPrice(List<OrderItem> orderItemList){
        BigDecimal totalPrice = new BigDecimal("0");
        for (OrderItem orderItem:orderItemList){
            totalPrice = BigDecimalUtils.add(totalPrice,orderItem.getTotal_price());
        }
        return totalPrice;
    }

    private ServerResponse getOrderItemList(List<Cart> cartList,Integer userid){
        List<OrderItem> orderItemList = new ArrayList<>();
        if (cartList==null||cartList.size()==0){
            //购物车空
            return ServerResponse.createServerResponse(ResponseCode.CART_EMPTY.getCode(),ResponseCode.CART_EMPTY.getMsg());
        }
        for (Cart cart:cartList){
            OrderItem orderItem = new OrderItem();
            orderItem.setUser_id(userid);

            com.neuedu.pojo.Product product = productDao.findProductById(cart.getProduct_id());

            if (product==null||product.getStatus()!=1){
                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_OFFLINE_NOT_EXISTS.getCode(),ResponseCode.PRODUCT_OFFLINE_NOT_EXISTS.getMsg());
            }
            //判断商品库存是否充足
            if (cart.getQuantity()>product.getStock()){//商品库存不足
                return ServerResponse.createServerResponse(ResponseCode.PRODUCT_STOCK_LESS.getCode(),ResponseCode.PRODUCT_STOCK_LESS.getMsg());
            }

            orderItem.setProduct_id(product.getId());
            orderItem.setProduct_name(product.getName());
            orderItem.setProduct_image(product.getMain_image());
            orderItem.setCurrent_unit_price(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            orderItem.setTotal_price(BigDecimalUtils.multiply(product.getPrice(),new BigDecimal(cart.getQuantity())));

            orderItemList.add(orderItem);
        }
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),orderItemList);
    }

    private static Log log = LogFactory.getLog(com.alipay.demo.trade.Main.class);

        // 支付宝当面付2.0服务
        private static AlipayTradeService tradeService;

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        private static AlipayTradeService   tradeWithHBService;

        // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
        private static AlipayMonitorService monitorService;

        static {
            /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
             *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
             */
            Configs.init("zfbinfo.properties");

            /** 使用Configs提供的默认参数
             *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
             */
            tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

            // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
            tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

            /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
            monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                    .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                    .setFormat("json").build();
        }

        // 简单打印应答
        private void dumpResponse(AlipayResponse response) {
            if (response != null) {
                log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
                if (StringUtils.isNotEmpty(response.getSubCode())) {
                    log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                            response.getSubMsg()));
                }
                log.info("body:" + response.getBody());
            }
        }

        public static void main(String[] args) {
            com.alipay.demo.trade.Main main = new com.alipay.demo.trade.Main();

            // 系统商商测试交易保障接口api
            //        main.test_monitor_sys();

            // POS厂商测试交易保障接口api
            //        main.test_monitor_pos();

            // 测试交易保障接口调度
            //        main.test_monitor_schedule_logic();

            // 测试当面付2.0支付（使用未集成交易保障接口的当面付2.0服务）
            //        main.test_trade_pay(tradeService);

            // 测试查询当面付2.0交易
            //        main.test_trade_query();

            // 测试当面付2.0退货
            //        main.test_trade_refund();

            // 测试当面付2.0生成支付二维码
            main.test_trade_precreate();
        }

        // 测试系统商交易保障调度
        public void test_monitor_schedule_logic() {
            // 启动交易保障线程
            DemoHbRunner demoRunner = new DemoHbRunner(monitorService);
            demoRunner.setDelay(5); // 设置启动后延迟5秒开始调度，不设置则默认3秒
            demoRunner.setDuration(10); // 设置间隔10秒进行调度，不设置则默认15 * 60秒
            demoRunner.schedule();

            // 启动当面付，此处每隔5秒调用一次支付接口，并且当随机数为0时交易保障线程退出
            while (Math.random() != 0) {
                test_trade_pay(tradeWithHBService);
                Utils.sleep(5 * 1000);
            }

            // 满足退出条件后可以调用shutdown优雅安全退出
            demoRunner.shutdown();
        }

        // 系统商的调用样例，填写了所有系统商商需要填写的字段
        public void test_monitor_sys() {
            // 系统商使用的交易信息格式，json字符串类型
            List<SysTradeInfo> sysTradeInfoList = new ArrayList<SysTradeInfo>();
            sysTradeInfoList.add(SysTradeInfo.newInstance("00000001", 5.2, HbStatus.S));
            sysTradeInfoList.add(SysTradeInfo.newInstance("00000002", 4.4, HbStatus.F));
            sysTradeInfoList.add(SysTradeInfo.newInstance("00000003", 11.3, HbStatus.P));
            sysTradeInfoList.add(SysTradeInfo.newInstance("00000004", 3.2, HbStatus.X));
            sysTradeInfoList.add(SysTradeInfo.newInstance("00000005", 4.1, HbStatus.X));

            // 填写异常信息，如果有的话
            List<ExceptionInfo> exceptionInfoList = new ArrayList<ExceptionInfo>();
            exceptionInfoList.add(ExceptionInfo.HE_SCANER);
            //        exceptionInfoList.add(ExceptionInfo.HE_PRINTER);
            //        exceptionInfoList.add(ExceptionInfo.HE_OTHER);

            // 填写扩展参数，如果有的话
            Map<String, Object> extendInfo = new HashMap<String, Object>();
            //        extendInfo.put("SHOP_ID", "BJ_ZZ_001");
            //        extendInfo.put("TERMINAL_ID", "1234");

            String appAuthToken = "应用授权令牌";//根据真实值填写

            AlipayHeartbeatSynRequestBuilder builder = new AlipayHeartbeatSynRequestBuilder()
                    .setAppAuthToken(appAuthToken).setProduct(Product.FP).setType(Type.CR)
                    .setEquipmentId("cr1000001").setEquipmentStatus(EquipStatus.NORMAL)
                    .setTime(Utils.toDate(new Date())).setStoreId("store10001").setMac("0a:00:27:00:00:00")
                    .setNetworkType("LAN").setProviderId("2088911212323549") // 设置系统商pid
                    .setSysTradeInfoList(sysTradeInfoList) // 系统商同步trade_info信息
                    //                .setExceptionInfoList(exceptionInfoList)  // 填写异常信息，如果有的话
                    .setExtendInfo(extendInfo) // 填写扩展信息，如果有的话
                    ;

            MonitorHeartbeatSynResponse response = monitorService.heartbeatSyn(builder);
            dumpResponse(response);
        }

        // POS厂商的调用样例，填写了所有pos厂商需要填写的字段
        public void test_monitor_pos() {
            // POS厂商使用的交易信息格式，字符串类型
            List<PosTradeInfo> posTradeInfoList = new ArrayList<PosTradeInfo>();
            posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.S, "1324", 7));
            posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.X, "1326", 15));
            posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.S, "1401", 8));
            posTradeInfoList.add(PosTradeInfo.newInstance(HbStatus.F, "1405", 3));

            // 填写异常信息，如果有的话
            List<ExceptionInfo> exceptionInfoList = new ArrayList<ExceptionInfo>();
            exceptionInfoList.add(ExceptionInfo.HE_PRINTER);

            // 填写扩展参数，如果有的话
            Map<String, Object> extendInfo = new HashMap<String, Object>();
            //        extendInfo.put("SHOP_ID", "BJ_ZZ_001");
            //        extendInfo.put("TERMINAL_ID", "1234");

            AlipayHeartbeatSynRequestBuilder builder = new AlipayHeartbeatSynRequestBuilder()
                    .setProduct(Product.FP)
                    .setType(Type.SOFT_POS)
                    .setEquipmentId("soft100001")
                    .setEquipmentStatus(EquipStatus.NORMAL)
                    .setTime("2015-09-28 11:14:49")
                    .setManufacturerPid("2088000000000009")
                    // 填写机具商的支付宝pid
                    .setStoreId("store200001").setEquipmentPosition("31.2433190000,121.5090750000")
                    .setBbsPosition("2869719733-065|2896507033-091").setNetworkStatus("gggbbbgggnnn")
                    .setNetworkType("3G").setBattery("98").setWifiMac("0a:00:27:00:00:00")
                    .setWifiName("test_wifi_name").setIp("192.168.1.188")
                    .setPosTradeInfoList(posTradeInfoList) // POS厂商同步trade_info信息
                    //                .setExceptionInfoList(exceptionInfoList) // 填写异常信息，如果有的话
                    .setExtendInfo(extendInfo) // 填写扩展信息，如果有的话
                    ;

            MonitorHeartbeatSynResponse response = monitorService.heartbeatSyn(builder);
            dumpResponse(response);
        }

        // 测试当面付2.0支付
        public void test_trade_pay(AlipayTradeService service) {
            // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
            // 需保证商户系统端不能重复，建议通过数据库sequence生成，
            String outTradeNo = "tradepay" + System.currentTimeMillis()
                    + (long) (Math.random() * 10000000L);

            // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
            String subject = "xxx品牌xxx门店当面付消费";

            // (必填) 订单总金额，单位为元，不能超过1亿元
            // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
            String totalAmount = "0.01";

            // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
            String authCode = "用户自己的支付宝付款码"; // 条码示例，286648048691290423
            // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
            // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
            //        String discountableAmount = "1.00"; //

            // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
            // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
            String undiscountableAmount = "0.0";

            // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
            // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
            String sellerId = "";

            // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
            String body = "购买商品3件共20.00元";

            // 商户操作员编号，添加此参数可以为商户操作员做销售统计
            String operatorId = "test_operator_id";

            // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
            String storeId = "test_store_id";

            // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
            String providerId = "2088100200300400500";
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(providerId);

            // 支付超时，线下扫码交易定义为5分钟
            String timeoutExpress = "5m";

            // 商品明细列表，需填写购买商品详细信息，
            List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
            GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包", 1000, 1);
            // 创建好一个商品后添加至商品明细列表
            goodsDetailList.add(goods1);

            // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
            GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
            goodsDetailList.add(goods2);

            String appAuthToken = "应用授权令牌";//根据真实值填写

            // 创建条码支付请求builder，设置请求参数
            AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                    //            .setAppAuthToken(appAuthToken)
                    .setOutTradeNo(outTradeNo).setSubject(subject).setAuthCode(authCode)
                    .setTotalAmount(totalAmount).setStoreId(storeId)
                    .setUndiscountableAmount(undiscountableAmount).setBody(body).setOperatorId(operatorId)
                    .setExtendParams(extendParams).setSellerId(sellerId)
                    .setGoodsDetailList(goodsDetailList).setTimeoutExpress(timeoutExpress);

            // 调用tradePay方法获取当面付应答
            AlipayF2FPayResult result = service.tradePay(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    log.info("支付宝支付成功: )");
                    break;

                case FAILED:
                    log.error("支付宝支付失败!!!");
                    break;

                case UNKNOWN:
                    log.error("系统异常，订单状态未知!!!");
                    break;

                default:
                    log.error("不支持的交易状态，交易返回异常!!!");
                    break;
            }
        }

        // 测试当面付2.0查询订单
        public void test_trade_query() {
            // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
            String outTradeNo = "tradepay14817938139942440181";

            // 创建查询请求builder，设置请求参数
            AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                    .setOutTradeNo(outTradeNo);

            AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    log.info("查询返回该订单支付成功: )");

                    AlipayTradeQueryResponse response = result.getResponse();
                    dumpResponse(response);

                    log.info(response.getTradeStatus());
                    if (Utils.isListNotEmpty(response.getFundBillList())) {
                        for (TradeFundBill bill : response.getFundBillList()) {
                            log.info(bill.getFundChannel() + ":" + bill.getAmount());
                        }
                    }
                    break;

                case FAILED:
                    log.error("查询返回该订单支付失败或被关闭!!!");
                    break;

                case UNKNOWN:
                    log.error("系统异常，订单支付状态未知!!!");
                    break;

                default:
                    log.error("不支持的交易状态，交易返回异常!!!");
                    break;
            }
        }

        // 测试当面付2.0退款
        public void test_trade_refund() {
            // (必填) 外部订单号，需要退款交易的商户外部订单号
            String outTradeNo = "tradepay14817938139942440181";

            // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
            String refundAmount = "0.01";

            // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
            // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
            String outRequestNo = "";

            // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
            String refundReason = "正常退款，用户买多了";

            // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
            String storeId = "test_store_id";

            // 创建退款请求builder，设置请求参数
            AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                    .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
                    .setOutRequestNo(outRequestNo).setStoreId(storeId);

            AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    log.info("支付宝退款成功: )");
                    break;

                case FAILED:
                    log.error("支付宝退款失败!!!");
                    break;

                case UNKNOWN:
                    log.error("系统异常，订单退款状态未知!!!");
                    break;

                default:
                    log.error("不支持的交易状态，交易返回异常!!!");
                    break;
            }
        }
    }
