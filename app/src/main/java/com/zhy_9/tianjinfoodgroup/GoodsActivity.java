package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class GoodsActivity extends Activity implements View.OnClickListener{

    private TextView goodsListTitle;
    private String pageTitle;
    private TextView goodsListBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        initView();
        setPageTitle();
    }

    private void initView(){
        goodsListTitle = (TextView) findViewById(R.id.goods_list_title);
        goodsListBack = (TextView) findViewById(R.id.goods_list_back);
        goodsListBack.setOnClickListener(this);
    }

    private void setPageTitle(){
        Intent intent = getIntent();
        pageTitle = intent.getStringExtra("pageTitle");
        if(!TextUtils.isEmpty(pageTitle)){
            goodsListTitle.setText(pageTitle);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.goods_list_back:
                finish();
                break;
        }
    }
}
