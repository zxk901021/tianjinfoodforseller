package com.zhy_9.tianjinfoodgroup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.zhy_9.tianjinfoodgroup.httputil.HttpUtil;
import com.zhy_9.tianjinfoodgroup.httputil.VolleyListener;
import com.zhy_9.tianjinfoodgroup.model.OrderCounts;
import com.zhy_9.tianjinfoodgroup.model.UserInfo;
import com.zhy_9.tianjinfoodgroup.util.TextUtil;
import com.zhy_9.tianjinfoodgroup.util.UrlUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SellerManagerActivity extends Activity implements View.OnClickListener {

    private RelativeLayout orderList;
    private RelativeLayout headQuarterFoods;
    private RelativeLayout onStoreGoods;
    private RelativeLayout onSellingGoods;
    private TextView lowInventory, waitHandle, handled, packaging, deliverying, problemPackage;
    private TextView lowInventoryCount, waitHandleCount, handledCount, packagingCount, deliveryingCount, problemPackageCount;
    private RelativeLayout managerOrder;
    private RelativeLayout accountSecurity;
    private RelativeLayout mallMessage;
    private RelativeLayout numberCounts;
    private RelativeLayout manageEvaluate;
    private Button quitLogin;
    private UserInfo info;
    private TextView userNameAndIntegral;
    private String userId;
    private String shopId;
    private OrderCounts orderCounts;
    private ImageView userPortrait;
    private Map<String, String> params = new HashMap<>();
    private String orderResponse;
    private TextView mallMessageCount;
    private String messageResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_manager);

        initView();
        initParams();
        getOrdersCount();
        initWidgetData();

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
        numberCounts = (RelativeLayout) findViewById(R.id.number_counts);
        numberCounts.setOnClickListener(this);
        manageEvaluate = (RelativeLayout) findViewById(R.id.manager_evaluate);
        manageEvaluate.setOnClickListener(this);
        userNameAndIntegral = (TextView) findViewById(R.id.user_name_and_integral);
        userPortrait = (ImageView) findViewById(R.id.seller_portrait);
        lowInventoryCount = (TextView) findViewById(R.id.low_inventory_count);
        waitHandleCount = (TextView) findViewById(R.id.wait_handle_count);
        handledCount = (TextView) findViewById(R.id.handled_count);
        packagingCount = (TextView) findViewById(R.id.packaging_count);
        deliveryingCount = (TextView) findViewById(R.id.deliverying_count);
        problemPackageCount = (TextView) findViewById(R.id.problem_package_count);
        mallMessageCount = (TextView) findViewById(R.id.mall_msg_count);
    }

    private void initParams() {
        Intent intent = getIntent();
        info = new UserInfo();
        info = (UserInfo) intent.getSerializableExtra("user_model");
        userId = info.getUserId();
        shopId = info.getShopId();
        params.put("user_id", userId);
        params.put("shop_id", shopId);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    try {
                        JSONObject resp = new JSONObject(orderResponse);
                        String status = resp.getString("status");
                        if (status.equals("1")) {
                            JSONObject data = resp.getJSONObject("data");
                            orderCounts = new OrderCounts();
                            orderCounts.setLow_stack(TextUtil.setNullToEmpty(data.getString("low_stack")));
                            orderCounts.setAppraisesCount(TextUtil.setNullToEmpty(data.getString("appraisesCount")));
                            orderCounts.setBeAcceptedCount(TextUtil.setNullToEmpty(data.getString("beAcceptedCount")));
                            orderCounts.setHasAcceptedCount(TextUtil.setNullToEmpty(data.getString("hasAcceptedCount")));
                            orderCounts.setPackagedCount(TextUtil.setNullToEmpty(data.getString("packagedCount")));
                            Log.e("counts", orderCounts.toString());
                            addTipCounts();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case 2:
                    try {
                        JSONObject resp = new JSONObject(messageResponse);
                        String status = resp.getString("status");
                        if (status.equals("1")) {
                            String data = resp.getString("data");
                            mallMessageCount.setText(data);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

            }
        }
    };

    private void getOrdersCount() {
        HttpUtil.postVolley(SellerManagerActivity.this, UrlUtil.ORDER_COUNTS_DATA, HttpUtil.initParam(params), new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("Order", s);
                orderResponse = s;
                handler.sendEmptyMessage(1);

            }
        });
        HttpUtil.postVolley(SellerManagerActivity.this, UrlUtil.MALL_MESSAGE_COUNT, params, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("Message", s);
                messageResponse = s;
                handler.sendEmptyMessage(2);

            }
        });

    }


    private void initWidgetData() {
        userNameAndIntegral.setText(TextUtil.setNullToEmpty(info.getUserName() + "  积分:" + TextUtil.setNullToEmpty(info.getUserScore())));
    }

    private void addTipCounts() {
        lowInventoryCount.setText(TextUtils.isEmpty(orderCounts.getLow_stack()) ? "" : orderCounts.getLow_stack());
        waitHandleCount.setText(TextUtils.isEmpty(orderCounts.getBeAcceptedCount()) ? "" : orderCounts.getBeAcceptedCount());
        handledCount.setText(TextUtils.isEmpty(orderCounts.getHasAcceptedCount()) ? "" : orderCounts.getHasAcceptedCount());
        packagingCount.setText(TextUtils.isEmpty(orderCounts.getPackagedCount()) ? "" : orderCounts.getPackagedCount());
        deliveryingCount.setText(TextUtils.isEmpty(orderCounts.getAppraisesCount()) ? "" : orderCounts.getAppraisesCount());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_order:
                Intent orderListIntent = new Intent(SellerManagerActivity.this, OrderListActivity.class);
                orderListIntent.putExtra("userId", userId);
                orderListIntent.putExtra("shopId", shopId);
                startActivity(orderListIntent);
                break;

            case R.id.goods_head_quarter:
                Intent headQuarterIntent = new Intent(SellerManagerActivity.this, GoodsActivity.class);
                headQuarterIntent.putExtra("userId", userId);
                headQuarterIntent.putExtra("shopId", shopId);
                headQuarterIntent.putExtra("mode", 1);
                headQuarterIntent.putExtra("pageTitle", "总店商品");
                startActivity(headQuarterIntent);
                break;

            case R.id.goods_on_store:
                Intent onStoreIntent = new Intent(SellerManagerActivity.this, GoodsActivity.class);
                onStoreIntent.putExtra("userId", userId);
                onStoreIntent.putExtra("shopId", shopId);
                onStoreIntent.putExtra("mode", 2);
                onStoreIntent.putExtra("pageTitle", "仓库中的商品");
                startActivity(onStoreIntent);
                break;

            case R.id.goods_on_selling:
                Intent onSellingIntent = new Intent(SellerManagerActivity.this, OnSellingGoodsActivity.class);
                onSellingIntent.putExtra("userId", userId);
                onSellingIntent.putExtra("shopId", shopId);
                onSellingIntent.putExtra("mode", 1);
                startActivity(onSellingIntent);
                break;

            case R.id.low_inventory:
                Intent lowInventoryIntent = new Intent(SellerManagerActivity.this, OnSellingGoodsActivity.class);
                lowInventoryIntent.putExtra("userId", userId);
                lowInventoryIntent.putExtra("shopId", shopId);
                lowInventoryIntent.putExtra("mode", 2);
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
                managerOrderIntent.putExtra("userId", userId);
                managerOrderIntent.putExtra("shopId", shopId);
                startActivity(managerOrderIntent);
                break;

            case R.id.manager_evaluate:
                Intent managerEvaluateIntent = new Intent(SellerManagerActivity.this, ManageEvaluateActivity.class);
                managerEvaluateIntent.putExtra("userId", userId);
                managerEvaluateIntent.putExtra("shopId", shopId);
                startActivity(managerEvaluateIntent);
                break;

            case R.id.account_security:
                Intent accountSecurityIntent = new Intent(SellerManagerActivity.this, AccountSecurityActivity.class);
                accountSecurityIntent.putExtra("userId", userId);
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

            case R.id.number_counts:
                //TODO
                break;
        }
    }
}
