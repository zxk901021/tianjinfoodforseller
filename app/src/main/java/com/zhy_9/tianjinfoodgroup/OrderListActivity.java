package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OrderListActivity extends Activity implements View.OnClickListener{

    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        initView();
    }

    private void initView(){
        back = (TextView) findViewById(R.id.order_list_back);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_list_back:
                finish();
                break;
        }
    }
}
