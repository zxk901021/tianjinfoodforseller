package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class OnSellingGoodsActivity extends Activity implements View.OnClickListener{

    private TextView title;
    private TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_selling_goods);

        initView();
        setPageTitle();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.goods_on_selling_title);
        back = (TextView) findViewById(R.id.goods_on_selling_back);
        back.setOnClickListener(this);
    }

    private void setPageTitle(){
        Intent intent = getIntent();
        String pageTitle = intent.getStringExtra("title");
        if (!TextUtils.isEmpty(pageTitle)){
            title.setText(pageTitle);
        }
        else title.setText("出售中的商品");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goods_on_selling_back:
                finish();
                break;
        }
    }
}
