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
import com.zhy_9.tianjinfoodgroup.model.UserInfo;
import com.zhy_9.tianjinfoodgroup.util.Constant;
import com.zhy_9.tianjinfoodgroup.util.RandomString;
import com.zhy_9.tianjinfoodgroup.util.ToastUtil;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity implements View.OnClickListener{

    private Button loginSubmit;
    private TextView forgetPassword;
    private EditText userName;
    private EditText password;
    private String usernameStr;
    private String passwordStr;
    private String noncestr;
    private String timeStr;
    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

    }

    private void initView(){
        loginSubmit = (Button) findViewById(R.id.seller_login_submit);
        forgetPassword = (TextView) findViewById(R.id.forget_password);
        loginSubmit.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        userName = (EditText) findViewById(R.id.seller_login_username);
        password = (EditText) findViewById(R.id.seller_login_password);
    }

    private boolean getUserData(){
        boolean isDataNull= false;
        usernameStr = userName.getText().toString();
        passwordStr = password.getText().toString();
        if (TextUtils.isEmpty(usernameStr)){
            ToastUtil.showToast(LoginActivity.this, "用户名不能为空！");
            isDataNull = true;
            return  isDataNull;
        }else if (TextUtils.isEmpty(passwordStr)){
            ToastUtil.showToast(LoginActivity.this, "密码不能为空！");
            isDataNull = true;
            return  isDataNull;
        }else return isDataNull;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seller_login_submit:
                if (!getUserData()){
                    Map<String, String> params = new HashMap<>();
                    params.put("username", usernameStr);
                    params.put("password", passwordStr);
                    noncestr = RandomString.getRandomString(10);
                    params.put("noncestr", noncestr);
                    long time = System.currentTimeMillis();
                    timeStr = time + "";
                    params.put("timestamp", timeStr);
                    params.put("salt", Constant.salt);
                    String string = EncryptParams.getString(params);
                    sign = EncryptParams.md5(EncryptParams.sha1(string));
                    params.put("sign", sign);

                    Log.e("params", params.entrySet().toString());
                    HttpUtil.postVolley(LoginActivity.this, UrlUtil.USER_LOGIN, params, new VolleyListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("onErrorResponse", volleyError.toString());
                        }

                        @Override
                        public void onResponse(String s) {
                            try {
                                JSONObject resp = new JSONObject(s);
                                String status = resp.getString("status");
                                if (status.equals("1")){
                                    ToastUtil.showToast(LoginActivity.this, "登录成功！");
                                    UserInfo info = new UserInfo();
                                    JSONObject data = resp.getJSONObject("data");
                                    String shopId = data.getString("shopId");
                                    String shopSn = data.getString("shopSn");
                                    String shopName = data.getString("shopName");
                                    String shopImg = data.getString("shopImg");
                                    String userId = data.getString("userId");
                                    String loginName = data.getString("loginName");
                                    String userSex = data.getString("userSex");
                                    String userType = data.getString("userType");
                                    String userName = data.getString("userName");
                                    String userQQ = data.getString("userQQ");
                                    String userPhone = data.getString("userPhone");
                                    String userEmail = data.getString("userEmail");
                                    String userScore = data.getString("userScore");
                                    String userPhoto = data.getString("userPhoto");
                                    info.setShopId(shopId);
                                    info.setShopImg(shopImg);
                                    info.setShopName(shopName);
                                    info.setShopSn(shopSn);
                                    info.setUserEmail(userEmail);
                                    info.setUserId(userId);
                                    info.setUserName(userName);
                                    info.setUserPhone(userPhone);
                                    info.setUserQQ(userQQ);
                                    info.setUserSex(userSex);
                                    info.setLoginName(loginName);
                                    info.setUserType(userType);
                                    info.setUserScore(userScore);
                                    info.setUserPhoto(userPhoto);
                                    Intent intent = new Intent(LoginActivity.this, SellerManagerActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("user_model", info);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e("onResponse", s);
                        }
                    });
                }
                break;

            case R.id.forget_password:
                Intent forgetIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetIntent);
                break;
        }
    }
}
