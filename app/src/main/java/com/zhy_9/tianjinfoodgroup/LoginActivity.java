package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends Activity implements View.OnClickListener{

    private Button loginSubmit;
    private TextView forgetPassword;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seller_login_submit:
                Intent intent = new Intent(LoginActivity.this, SellerManagerActivity.class);
                startActivity(intent);
                break;

            case R.id.forget_password:
                Intent forgetIntent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(forgetIntent);
                break;
        }
    }
}
