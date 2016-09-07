package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.encrypt.EncryptParams;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.util.Constant;
import com.zhy_9.tianjinfoodgroup.util.RandomString;
import com.zhy_9.tianjinfoodgroup.util.ToastUtil;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgetPasswordActivity extends Activity implements View.OnClickListener{

    private TextView back;
    private EditText phoneNumber;
    private EditText verifyCode;
    private Button sendVerifyButton;
    private Button nextStep;
    private String userPhone;
    private String sign;
    private String verifyCodeFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        initView();
    }

    private void initView(){
        back = (TextView) findViewById(R.id.forget_password_back);
        back.setOnClickListener(this);
        phoneNumber = (EditText) findViewById(R.id.forget_password_edt);
        verifyCode = (EditText) findViewById(R.id.forget_password_verify_edt);
        sendVerifyButton = (Button) findViewById(R.id.send_forget_verify_code);
        sendVerifyButton.setOnClickListener(this);
        nextStep = (Button) findViewById(R.id.forget_password_next);
        nextStep.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.forget_password_back:
                finish();
                break;

            case R.id.send_forget_verify_code:
                userPhone = phoneNumber.getText().toString();
                if (TextUtils.isEmpty(userPhone)){
                    ToastUtil.showToast(ForgetPasswordActivity.this, "请输入手机号");
                    return;
                }else userPhone = phoneNumber.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put(Constant.USER_TEL, userPhone);
                params.put(Constant.NONCESTR, RandomString.getRandomString(10));
                params.put(Constant.TIMESTAMP, System.currentTimeMillis() + "");
                params.put(Constant.SALT, Constant.salt);
                String signStr = EncryptParams.getString(params);
                sign = EncryptParams.md5(EncryptParams.sha1(signStr));
                params.put(Constant.SIGN, sign);
                params.remove(Constant.SALT);
                HttpUtil.postVolley(ForgetPasswordActivity.this, UrlUtil.SEND_VERIFY_CODE, params, new VolleyListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("onErrorResponse", volleyError.toString());
                    }

                    @Override
                    public void onResponse(String s) {
                        Log.e("onResponse", s);
                        if (!TextUtils.isEmpty(s)){
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                String status = jsonObject.getString("status");
                                if (status.equals("1")){
                                    String data = jsonObject.getString("data");
                                    verifyCodeFromServer = data;
                                }else {
                                    String message = jsonObject.getString("message");
                                    ToastUtil.showToast(ForgetPasswordActivity.this, message);
                                }


//                                Log.e("params", "message：" + message + "status: " + status + "data: " + data);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                });
                break;

            case R.id.forget_password_next:

                String code = verifyCode.getText().toString();
                if (TextUtils.isEmpty(code)){
                    ToastUtil.showToast(ForgetPasswordActivity.this, "请填写正确格式的验证码！");
                }
                if (TextUtils.isEmpty(verifyCodeFromServer)){
                    ToastUtil.showToast(ForgetPasswordActivity.this, "验证码不正确");
                }
                if (TextUtils.equals(verifyCodeFromServer, code)){
                    Intent intent = new Intent(ForgetPasswordActivity.this, SetPasswordActivity.class);
                    intent.putExtra("tel", userPhone);
                    intent.putExtra("code", verifyCodeFromServer);
                    startActivity(intent);
                }else {
                    ToastUtil.showToast(ForgetPasswordActivity.this, "验证码不正确");
                }

                break;
        }
    }
}
