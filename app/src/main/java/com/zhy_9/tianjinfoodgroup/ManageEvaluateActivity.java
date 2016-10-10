package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;

public class ManageEvaluateActivity extends Activity implements View.OnClickListener{

    private String userId, shopId;
    private RecyclerView evaluateList;
    private TextView back;
    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_evaluate);

        getIntentData();

        initView();

        getEvaluateInfo();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        shopId = intent.getStringExtra("shopId");
    }

    private void initView(){
        evaluateList = (RecyclerView) findViewById(R.id.evaluate_list);
        back = (TextView) findViewById(R.id.manager_evaluate_back);
        back.setOnClickListener(this);
    }

    private void getEvaluateInfo(){
        params = new HashMap<>();
        params.put("shop_id", shopId);
        params.put("user_id", userId);
        HttpUtil.postVolley(ManageEvaluateActivity.this, UrlUtil.EVALUATE_LIST, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("Evaluate--resp", s);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manager_evaluate_back:
                finish();
                break;
        }
    }
}
