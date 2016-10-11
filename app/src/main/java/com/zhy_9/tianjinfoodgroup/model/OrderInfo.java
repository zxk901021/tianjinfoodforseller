package com.zhy_9.tianjinfoodgroup.model;

import java.util.List;

/**
 * Created by ZHY_9 on 2016/10/9.
 */

public class OrderInfo {

    private String orderNumber;
    private String orderStatus;
    private String orderTotalMoney;
    private List<GoodsInfo> orderGoodsInfo;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(String orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public List<GoodsInfo> getOrderGoodsInfo() {
        return orderGoodsInfo;
    }

    public void setOrderGoodsInfo(List<GoodsInfo> orderGoodsInfo) {
        this.orderGoodsInfo = orderGoodsInfo;
    }
}
