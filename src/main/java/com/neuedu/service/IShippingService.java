package com.neuedu.service;

import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Shipping;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ShippingVo;


public interface IShippingService {
    /*添加地址*/
    public ServerResponse<Integer> addShipping(Shipping shipping);

    /*删除地址*/
    public ServerResponse<Integer> deleteShipping(Integer userid, Integer shippingId);

    /*修改地址*/
    public ServerResponse<Integer> updateAddress(Shipping shipping);

    /*查看具体地址*/
    public ServerResponse<Shipping> checkedShippingByShippingId(Integer userid, Integer shippingId);

    /*
     * 分页查询
     * */
    public ServerResponse<PageModel<ShippingVo>> findShippingByPageNo(Integer pageNo, Integer pageSize);

}
