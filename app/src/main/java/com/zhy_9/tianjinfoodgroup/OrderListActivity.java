package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;

public class OrderListActivity extends Activity implements View.OnClickListener{

    private TextView back;
    private String userId;
    private String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initView();
        getIntentData();
        initOrderListData();
    }

    private void initView(){
        back = (TextView) findViewById(R.id.order_list_back);
        back.setOnClickListener(this);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        shopId = intent.getStringExtra("shopId");
    }

    private void initOrderListData(){
        Map<String, String> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("shop_id", shopId);
        params.put("order_type", "all");

        HttpUtil.postVolley(OrderListActivity.this, UrlUtil.ORDER_CATEGORY, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("resp", s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_list_back:
                finish();
                break;
        }
    }
}
