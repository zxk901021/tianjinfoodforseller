package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SellerManagerActivity extends Activity implements View.OnClickListener {

    private RelativeLayout orderList;
    private RelativeLayout headQuarterFoods;
    private RelativeLayout onStoreGoods;
    private RelativeLayout onSellingGoods;
    private TextView lowInventory, waitHandle, handled, packaging, deliverying, problemPackage;
    private RelativeLayout managerOrder;
    private RelativeLayout accountSecurity;
    private RelativeLayout mallMessage;
    private Button quitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_manager);

        initView();
    }

    private void initView() {
        orderList = (RelativeLayout) findViewById(R.id.my_order);
        orderList.setOnClickListener(this);
        headQuarterFoods = (RelativeLayout) findViewById(R.id.goods_head_quarter);
        headQuarterFoods.setOnClickListener(this);
        onStoreGoods = (RelativeLayout) findViewById(R.id.goods_on_store);
        onStoreGoods.setOnClickListener(this);
        onSellingGoods = (RelativeLayout) findViewById(R.id.goods_on_selling);
        onSellingGoods.setOnClickListener(this);
        lowInventory = (TextView) findViewById(R.id.low_inventory);
        waitHandle = (TextView) findViewById(R.id.wait_handle);
        handled = (TextView) findViewById(R.id.handled);
        packaging = (TextView) findViewById(R.id.packaging);
        deliverying = (TextView) findViewById(R.id.deliverying);
        problemPackage = (TextView) findViewById(R.id.problem_package);
        lowInventory.setOnClickListener(this);
        waitHandle.setOnClickListener(this);
        handled.setOnClickListener(this);
        packaging.setOnClickListener(this);
        deliverying.setOnClickListener(this);
        problemPackage.setOnClickListener(this);
        managerOrder = (RelativeLayout) findViewById(R.id.manager_orders);
        managerOrder.setOnClickListener(this);
        accountSecurity = (RelativeLayout) findViewById(R.id.account_security);
        accountSecurity.setOnClickListener(this);
        mallMessage = (RelativeLayout) findViewById(R.id.mall_msg);
        mallMessage.setOnClickListener(this);
        quitLogin = (Button) findViewById(R.id.quit_login);
        quitLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_order:
                Intent orderListIntent = new Intent(SellerManagerActivity.this, OrderListActivity.class);
                startActivity(orderListIntent);
                break;

            case R.id.goods_head_quarter:
                Intent headQuarterIntent = new Intent(SellerManagerActivity.this, GoodsActivity.class);
                headQuarterIntent.putExtra("pageTitle", "总店商品");
                startActivity(headQuarterIntent);
                break;

            case R.id.goods_on_store:
                Intent onStoreIntent = new Intent(SellerManagerActivity.this, GoodsActivity.class);
                onStoreIntent.putExtra("pageTitle", "仓库中的商品");
                startActivity(onStoreIntent);
                break;

            case R.id.goods_on_selling:
                Intent onSellingIntent = new Intent(SellerManagerActivity.this, OnSellingGoodsActivity.class);
                startActivity(onSellingIntent);
                break;

            case R.id.low_inventory:
                Intent lowInventoryIntent = new Intent(SellerManagerActivity.this, OnSellingGoodsActivity.class);
                lowInventoryIntent.putExtra("title", "缺货中的商品");
                startActivity(lowInventoryIntent);
                break;

            case R.id.wait_handle:
                Intent waitHandleIntent = new Intent(SellerManagerActivity.this, OrderListActivity.class);
                startActivity(waitHandleIntent);
                break;

            case R.id.handled:
                Intent handledIntent = new Intent(SellerManagerActivity.this, OrderListActivity.class);
                startActivity(handledIntent);
                break;

            case R.id.packaging:
                Intent packagingIntent = new Intent(SellerManagerActivity.this, OrderListActivity.class);
                startActivity(packagingIntent);
                break;

            case R.id.deliverying:
                Intent deliveryingIntent = new Intent(SellerManagerActivity.this, OrderListActivity.class);
                startActivity(deliveryingIntent);
                break;

            case R.id.problem_package:

                break;

            case R.id.manager_orders:
                Intent managerOrderIntent = new Intent(SellerManagerActivity.this, OrderListActivity.class);
                startActivity(managerOrderIntent);
                break;

            case R.id.account_security:
                Intent accountSecurityIntent = new Intent(SellerManagerActivity.this, AccountSecurityActivity.class);
                startActivity(accountSecurityIntent);
                break;

            case R.id.mall_msg:
                Intent mallMsgIntent = new Intent(SellerManagerActivity.this, MallMessageActivity.class);
                startActivity(mallMsgIntent);
                break;

            case R.id.quit_login:
                AlertDialog quitDialog = new AlertDialog.Builder(this).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent quitIntent = new Intent(SellerManagerActivity.this, LoginActivity.class);
                        startActivity(quitIntent);
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }).setTitle("确定退出登录？").create();
                quitDialog.show();
                break;
        }
    }
}
