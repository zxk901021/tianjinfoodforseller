package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

public class OnSellingGoodsActivity extends Activity implements View.OnClickListener{

    private TextView title;
    private TextView back;
    private RecyclerView goodsList;
    private OrderListAdapter adapter;
    private List<GoodsInfo> data;
    private String userId, shopId;
    private int mode;
    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_selling_goods);

        initView();
        setPageTitle();
        getIntentData();
        getGoodsData();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.goods_on_selling_title);
        back = (TextView) findViewById(R.id.goods_on_selling_back);
        goodsList = (RecyclerView) findViewById(R.id.goods_on_selling_list);
        back.setOnClickListener(this);
    }

    private void setPageTitle(){
        Intent intent = getIntent();
        String pageTitle = intent.getStringExtra("title");
        if (!TextUtils.isEmpty(pageTitle)){
            title.setText(pageTitle);
        }
        else title.setText("出售中的商品");
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        shopId = intent.getStringExtra("shopId");
        mode = intent.getIntExtra("mode", -1);
    }

    private void getGoodsData(){
        params = new HashMap<>();
        params.put("user_id", userId);
        params.put("shop_id", shopId);
        if (mode == 1) {
            params.put("onsale", "1");

        }else if (mode == 2) {
            params.put("less_stack", "1");
        }
        HttpUtil.postVolley(OnSellingGoodsActivity.this, UrlUtil.GOODS_LIST, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                data = new ArrayList<>();
                try {
                    JSONObject resp = new JSONObject(s);
                    JSONArray goodsArray = resp.getJSONArray("response");
                    for (int i = 0; i < goodsArray.length(); i ++){
                        GoodsInfo goodsInfo = new GoodsInfo();
                        goodsInfo.setGoodsName(goodsArray.getJSONObject(i).getString("goodsName"));
                        goodsInfo.setShopPrice(goodsArray.getJSONObject(i).getString("shopPrice"));
                        goodsInfo.setGoodsThums(goodsArray.getJSONObject(i).getString("goodsThums"));
                        data.add(goodsInfo);
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
            switch (msg.what) {
                case 1:
                    adapter = new OrderListAdapter(OnSellingGoodsActivity.this, data);
                    goodsList.setLayoutManager(new LinearLayoutManager(OnSellingGoodsActivity.this));
                    goodsList.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_on_selling_back:
                finish();
                break;
        }
    }
}
