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
import com.zhy_9.tianjinfoodgroup.adapter.EvaluateListAdapter;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.model.EvaluateInfo;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageEvaluateActivity extends Activity implements View.OnClickListener{

    private String userId, shopId;
    private RecyclerView evaluateList;
    private TextView back;
    private Map<String, String> params;
    private EvaluateListAdapter adapter;
    private List<EvaluateInfo> evaluataData;

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
        evaluataData = new ArrayList<>();
        HttpUtil.postVolley(ManageEvaluateActivity.this, UrlUtil.EVALUATE_LIST, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("Evaluate--resp", s);
                try {
                    JSONObject resp = new JSONObject(s);
                    JSONArray array = resp.getJSONArray("response");
                    for (int i = 0; i < array.length(); i ++) {
                        EvaluateInfo info= new EvaluateInfo();
                        info.setGoodsName(array.getJSONObject(i).getString("goodsName"));
                        info.setContent(array.getJSONObject(i).getString("content"));
                        info.setLoginName(array.getJSONObject(i).getString("loginName"));
                        evaluataData.add(info);
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
                    adapter = new EvaluateListAdapter(ManageEvaluateActivity.this, evaluataData);
                    evaluateList.setLayoutManager(new LinearLayoutManager(ManageEvaluateActivity.this));
                    evaluateList.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manager_evaluate_back:
                finish();
                break;
        }
    }
}
