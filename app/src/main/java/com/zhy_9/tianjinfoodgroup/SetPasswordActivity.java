package com.zhy_9.tianjinfoodgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.util.ToastUtil;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SetPasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText newPassword;
    private EditText newPasswordVerify;
    private Button submitChange;
    private TextView back;
    private String tel;
    private String verifyCode;
    private String newPass;
    private String newPassVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);

        initView();
        getIntentData();
    }

    private void initView(){
        newPassword = (EditText) findViewById(R.id.set_new_password);
        newPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        newPasswordVerify = (EditText) findViewById(R.id.set_new_password_again);
        newPasswordVerify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submitChange = (Button) findViewById(R.id.submit_change_password);
        submitChange.setOnClickListener(this);
        back = (TextView) findViewById(R.id.set_password_back);
        back.setOnClickListener(this);
    }

    private void getIntentData(){
        Intent intent = getIntent();
        tel = intent.getStringExtra("tel");
        verifyCode = intent.getStringExtra("code");
    }

//    private boolean getAndCheckPassword(){
//        newPass = newPassword.getText().toString();
//        newPassVerify = newPasswordVerify.getText().toString();
//        if (TextUtils.isEmpty(newPass) || TextUtils.isEmpty(newPassVerify)){
//            ToastUtil.showToast(SetPasswordActivity.this, "密码不能为空！");
//            return false;
//        }
//
//        if (TextUtils.)
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_password_back:
                finish();
                break;

            case R.id.submit_change_password:
                newPass = newPassword.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("user_tel", tel);
                params.put("verify_code", verifyCode);
                params.put("new_password", newPass);
                params.remove("salt");
                Log.e("param", params.toString());
                HttpUtil.postVolley(SetPasswordActivity.this, UrlUtil.SET_NEW_PASSWORD, HttpUtil.initParam(params), new VolleyListener() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject resp = new JSONObject(s);
                            String status = resp.getString("status");
                            String message = resp.getString("message");
                            if (status.equals("1")){
                                ToastUtil.showToast(SetPasswordActivity.this, message);
                                Log.e("resp", message);
                            }else ToastUtil.showToast(SetPasswordActivity.this, message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });
                break;
        }
    }
}
