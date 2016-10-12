package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.adapter.OrderDetailListAdapter;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.model.GoodsInfo;
import com.zhy_9.tianjinfoodgroup.model.OrderInfo;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListActivity extends Activity implements View.OnClickListener{

    private TextView back;
    private String userId;
    private String shopId;
    private RecyclerView orderList;
    private List<OrderInfo> data;
    private OrderDetailListAdapter adapter;
    private TextView orderAll, orderUnHandled, orderHandled, orderPacking, orderDelivery, orderArrived;
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initView();
        getIntentData();
        initOrderListData(chooseRequestMode(mode));
    }

    private void initView(){
        back = (TextView) findViewById(R.id.order_list_back);
        back.setOnClickListener(this);
        orderList = (RecyclerView) findViewById(R.id.my_order_list);
        orderAll = (TextView) findViewById(R.id.order_all);
        orderUnHandled = (TextView) findViewById(R.id.order_unhandle);
        orderHandled = (TextView) findViewById(R.id.order_handle);
        orderPacking = (TextView) findViewById(R.id.order_package);
        orderDelivery = (TextView) findViewById(R.id.order_delivery);
        orderArrived = (TextView) findViewById(R.id.order_arrived);
        orderAll.setOnClickListener(this);
        orderUnHandled.setOnClickListener(this);
        orderHandled.setOnClickListener(this);
        orderPacking.setOnClickListener(this);
        orderDelivery.setOnClickListener(this);
        orderArrived.setOnClickListener(this);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        shopId = intent.getStringExtra("shopId");
        mode = intent.getIntExtra("mode", 0);
    }

    private String chooseRequestMode(int mode){
        String request = "";
        switch (mode) {
            case 0:
                request = "all";
                break;
            case 1:
                request = "beAcceptedCount";
                break;
            case 2:
                request = "hasAcceptedCount";
                break;

            case 3:
                request = "packagedCount";
                break;
            case 4:
                request = "appraisesCount";
                break;
        }
        return request;
    }

    private void initOrderListData(String request){
        data = new ArrayList<>();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("shop_id", shopId);
        params.put("order_type", request);
        params.put("page_size", "50");
        params.put("page_number", "1");
        HttpUtil.postVolley(OrderListActivity.this, UrlUtil.ORDER_CATEGORY, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("resp", s);
                try {
                    JSONObject resp = new JSONObject(s);
                    JSONArray arr = resp.getJSONArray("response");
                    for (int i = 0; i < arr.length(); i ++) {
                        List<GoodsInfo> goodsInfo = new ArrayList<>();
                        OrderInfo info = new OrderInfo();
                        info.setOrderNumber(arr.getJSONObject(i).getString("orderNo"));
                        info.setOrderStatus(arr.getJSONObject(i).getString("orderStatus"));
                        info.setOrderTotalMoney(arr.getJSONObject(i).getString("totalMoney"));
                        JSONArray goodsInfoArr = arr.getJSONObject(i).getJSONArray("goods");
                        for (int j = 0; j < goodsInfoArr.length(); j ++) {
                            GoodsInfo goods = new GoodsInfo();
                            goods.setGoodsThums(goodsInfoArr.getJSONObject(j).getString("goodsThums"));
                            goods.setGoodsName(goodsInfoArr.getJSONObject(j).getString("goodsName"));
                            goods.setShopPrice(goodsInfoArr.getJSONObject(j).getString("goodsPrice"));
                            goods.setSaleCount(goodsInfoArr.getJSONObject(j).getString("goodsNums"));
                            goodsInfo.add(goods);
                        }
                        info.setOrderGoodsInfo(goodsInfo);
                        data.add(info);
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    adapter = new OrderDetailListAdapter(OrderListActivity.this, data);
                    orderList.setLayoutManager(new LinearLayoutManager(OrderListActivity.this));
                    orderList.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_list_back:
                finish();
                break;
            case R.id.order_all:
                initOrderListData("all");
                break;
            case R.id.order_unhandle:
                initOrderListData("beAcceptedCount");
                break;
            case R.id.order_handle:
                initOrderListData("hasAcceptedCount");
                break;
            case R.id.order_package:
                initOrderListData("packagedCount");
                break;
            case R.id.order_delivery:
                initOrderListData("appraisesCount");
                break;
            case R.id.order_arrived:
                initOrderListData("hasReceived");
                break;
        }
    }
}
