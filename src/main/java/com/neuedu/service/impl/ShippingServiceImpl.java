package com.neuedu.service.impl;

import com.neuedu.common.ResponseCode;
import com.neuedu.common.ServerResponse;
import com.neuedu.dao.IShippingDao;
import com.neuedu.pojo.Product;
import com.neuedu.pojo.Shipping;
import com.neuedu.service.IShippingService;
import com.neuedu.vo.PageModel;
import com.neuedu.vo.ProductVo;
import com.neuedu.vo.ShippingVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private IShippingDao shippingDao;

    @Override
    public ServerResponse<Integer> addShipping(Shipping shipping) {
        int result = shippingDao.addShipping(shipping);
        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),shipping.getId());
        }else{
            return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
        }
    }

    @Override
    public ServerResponse<Integer> deleteShipping(Integer userid, Integer shippingId) {
        //step1：参数的非空验证
        if (shippingId==null||shippingId.equals("")){
         return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
       shippingDao.deleteShipping(userid,shippingId);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
    }

    @Override
    public ServerResponse<Integer> updateAddress(Shipping shipping) {
        int result = shippingDao.updateAddress(shipping);
        if (result>0){
            return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
        }
        return ServerResponse.createServerResponse(ResponseCode.FAIL.getCode(),ResponseCode.FAIL.getMsg());
    }

    @Override
    public ServerResponse<Shipping> checkedShippingByShippingId(Integer userid, Integer shippingId) {
        //step1：参数的非空验证
        if (shippingId==null||shippingId.equals("")){
            return ServerResponse.createServerResponse(ResponseCode.NEED_PRODUCT.getCode(),ResponseCode.NEED_PRODUCT.getMsg());
        }
        Shipping shipping = shippingDao.checkedShippingByShippingId(userid,shippingId);
        return ServerResponse.createServerResponse(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),shipping);
    }

    @Override
    public ServerResponse<PageModel<ShippingVo>> findShippingByPageNo(Integer pageNo, Integer pageSize) {
        List<Shipping> shippingList = shippingDao.findShippingByPageNo(pageNo,pageSize);
        List<ShippingVo> shippingVoList = new ArrayList<>();

        for (Shipping shipping:shippingList){
            shippingVoList.add(assembleShippingVo(shipping));
        }

        long totalRecord = shippingDao.findTotalRecord1();
        long totalpage = (totalRecord%pageSize==0?totalRecord/pageSize:(totalRecord/pageSize)+1);
        PageModel<ShippingVo> pageModel = new PageModel();
        pageModel.setData(shippingVoList);
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

    public ShippingVo assembleShippingVo(Shipping shipping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setId(shipping.getId());
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
}
