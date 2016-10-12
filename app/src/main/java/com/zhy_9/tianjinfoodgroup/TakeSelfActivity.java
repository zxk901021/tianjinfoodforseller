package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.util.ToastUtil;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TakeSelfActivity extends Activity implements View.OnClickListener{

    private String userId;
    private String shopId;
    private TextView back;
    private MaterialEditText selfCode;
    private Button save;
    private Map<String, String> params;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_self);

        getIntentData();
        initView();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        shopId = intent.getStringExtra("shopId");
    }
    private void initView(){
        back = (TextView) findViewById(R.id.take_self_back);
        back.setOnClickListener(this);
        selfCode = (MaterialEditText) findViewById(R.id.take_self_edit);
        save = (Button) findViewById(R.id.take_self_save);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.take_self_back:
                finish();
                break;

            case R.id.take_self_save:
                code = selfCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    ToastUtil.showToast(TakeSelfActivity.this, "自提码不能为空！");
                    return;
                }
                params = new HashMap<>();
                params.put("user_id", userId);
                params.put("shop_id", shopId);
                params.put("code", code);
                HttpUtil.postVolley(TakeSelfActivity.this, UrlUtil.SELF_DELIVEREY, HttpUtil.initParam(params), new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }

                    @Override
                    public void onResponse(String s) {
                        Log.e("self", s);
                        try {
                            JSONObject resp = new JSONObject(s);
                            String message = resp.getString("message");
                            ToastUtil.showToast(TakeSelfActivity.this, message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
        }
    }
}
