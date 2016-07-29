package com.zhy_9.tianjinfoodgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AccountSecurityActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);

        initView();
    }

    private void initView(){
        back = (TextView) findViewById(R.id.account_security_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_security_back:
                finish();
                break;
        }
    }
}
