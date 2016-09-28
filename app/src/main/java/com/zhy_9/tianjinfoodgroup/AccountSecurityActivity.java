package com.zhy_9.tianjinfoodgroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class AccountSecurityActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView back;
    private String userId;
    private EditText oldEdit;
    private EditText newEdit;
    private EditText newVerifyEdit;
    private Button submit;
    private String oldPassword;
    private String newPassword;
    private String newPasswordVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        initView();
        getIntentData();
    }

    private void initView() {
        back = (TextView) findViewById(R.id.account_security_back);
        back.setOnClickListener(this);
        oldEdit = (EditText) findViewById(R.id.old_password_edt);
        newEdit = (EditText) findViewById(R.id.new_password_edt);
        newVerifyEdit = (EditText) findViewById(R.id.new_password_verify_edt);
        submit = (Button) findViewById(R.id.submit_new_password);
        submit.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_security_back:
                finish();
                break;

            case R.id.submit_new_password:
                oldPassword = oldEdit.getText().toString();
                newPassword = newEdit.getText().toString();
                newPasswordVerify = newVerifyEdit.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("oldPwd", oldPassword);
                params.put("newPwd", newPassword);
                HttpUtil.postVolley(AccountSecurityActivity.this, UrlUtil.MODIFY_PASSWORD, HttpUtil.initParam(params), new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }

                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject resp = new JSONObject(s);
                            String status = resp.getString("status");
                            String message = resp.getString("message");
                            if (status.equals("1")) {
                                ToastUtil.showToast(AccountSecurityActivity.this, "修改成功！");
                            } else ToastUtil.showToast(AccountSecurityActivity.this, message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                break;
        }
    }
}
