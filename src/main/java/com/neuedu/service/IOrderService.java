package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.vo.OrderVo;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;

import java.util.Map;

public interface IOrderService {

    /*支付*/
    public ServerResponse pay(Long orderNo, Integer userid);

    /*回调*/
    public String alipaycallback(Map<String,String> map);

    /*支付状态*/
    public ServerResponse query_order_status(Long orderNo);

    /*创建订单*/
    public ServerResponse createOrder(Integer shippingId,Integer userid);

    /*取消订单*/
    public ServerResponse cancelOrder(Long orderNo,Integer userid);

    /*获取订单中商品信息*/
    public ServerResponse getCartProductInfo(Integer userid);

    /*获取订单详细*/
    public ServerResponse getOrderDetail(Long orderNo,Integer userid);

    /*订单发货*/
    public ServerResponse sendOrder(Long orderNo);

    /*
     *  后台
     * 分页查询
     * */
    public ServerResponse<PageModel<OrderVo>> findOrderByPageNo(Integer pageNo, Integer pageSize);

    /*
     * 查询订单接口
     * */
    public ServerResponse<OrderVo> findOrderById(Long orderNo);

    /*订单详细*/
    public ServerResponse getOrderDetail(Long orderNo);

    /*
     *  前台
     * 分页查询
     * */
    public ServerResponse<PageModel<OrderVo>> findOrderByPageNoAndUserId(Integer pageNo, Integer pageSize,Integer userid);
}
