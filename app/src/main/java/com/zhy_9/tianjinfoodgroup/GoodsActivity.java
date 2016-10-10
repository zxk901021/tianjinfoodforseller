package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.adapter.OrderListAdapter;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.model.GoodsInfo;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsActivity extends Activity implements View.OnClickListener{

    private TextView goodsListTitle;
    private String pageTitle;
    private TextView goodsListBack;
    private Map<String, String> params = new HashMap<>();
    private String userId, shopId;
    private List<GoodsInfo> goodsData;
    private RecyclerView goodsList;
    private OrderListAdapter adapter;
    private int mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        initView();
        setPageTitle();
        getIntentData();
        getGoodsInfo();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        shopId = intent.getStringExtra("shopId");
        mode = intent.getIntExtra("mode", -1);
    }

    private void getGoodsInfo(){
        params.put("user_id", userId);
        params.put("shop_id", shopId);
        if (mode == -1) {

        }
        if (mode == 1) {
            params.put("parent_shopId", "1");
        }else if (mode == 2) {
            params.put("on_sale", "0");
        }
        goodsData = new ArrayList<>();
        HttpUtil.postVolley(GoodsActivity.this, UrlUtil.GOODS_LIST, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                try {
                    JSONObject resp = new JSONObject(s);
                    JSONArray goodsArray = resp.getJSONArray("response");
                    for (int i = 0; i < goodsArray.length(); i ++){
                        GoodsInfo goodsInfo = new GoodsInfo();
                        goodsInfo.setGoodsName(goodsArray.getJSONObject(i).getString("goodsName"));
                        goodsInfo.setShopPrice(goodsArray.getJSONObject(i).getString("shopPrice"));
                        goodsInfo.setGoodsThums(goodsArray.getJSONObject(i).getString("goodsThums"));
                        goodsData.add(goodsInfo);
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("goods---List", s);
            }
        });
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter = new OrderListAdapter(GoodsActivity.this, goodsData);
                    goodsList.setLayoutManager(new LinearLayoutManager(GoodsActivity.this));
                    goodsList.setAdapter(adapter);
                    break;
            }
        }
    };

    private void initView(){
        goodsListTitle = (TextView) findViewById(R.id.goods_list_title);
        goodsListBack = (TextView) findViewById(R.id.goods_list_back);
        goodsListBack.setOnClickListener(this);
        goodsList = (RecyclerView) findViewById(R.id.mall_goods_list);
    }

    private void setPageTitle(){
        Intent intent = getIntent();
        pageTitle = intent.getStringExtra("pageTitle");
        if(!TextUtils.isEmpty(pageTitle)){
            goodsListTitle.setText(pageTitle);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.goods_list_back:
                finish();
                break;
        }
    }
}
