package com.zhy_9.tianjinfoodgroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MallMessageActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView back;
    private String userId, shopId;
    private TextView messageAll, messageUnRead, messageRead;
    private RecyclerView messageList;
    private Map<String, String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_message);
        initView();
        getIntentData();
        getMessageData();
    }

    private void initView(){
        back = (TextView) findViewById(R.id.mall_msg_back);
        back.setOnClickListener(this);
        messageAll = (TextView) findViewById(R.id.mall_msg_all);
        messageUnRead = (TextView) findViewById(R.id.mall_msg_unread);
        messageRead = (TextView) findViewById(R.id.mall_msg_read);
        messageAll.setOnClickListener(this);
        messageUnRead.setOnClickListener(this);
        messageRead.setOnClickListener(this);
        messageList = (RecyclerView) findViewById(R.id.mall_message_list);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        shopId = intent.getStringExtra("shopId");
    }

    private void getMessageData(){
        params = new HashMap<>();
        params.put("userId", userId);
        params.put("shopId", shopId);
        HttpUtil.postVolley(MallMessageActivity.this, UrlUtil.MALL_MESSAGE_LIST, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                try {
                    JSONObject resp = new JSONObject(s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mall_msg_back:
                finish();
                break;

            case R.id.mall_msg_all:

                break;
            case R.id.mall_msg_read:

                break;

            case R.id.mall_msg_unread:

                break;
        }
    }
}
