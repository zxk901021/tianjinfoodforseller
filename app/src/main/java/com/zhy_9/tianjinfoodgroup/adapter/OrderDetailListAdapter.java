package com.zhy_9.tianjinfoodgroup.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy_9.tianjinfoodgroup.R;
import com.zhy_9.tianjinfoodgroup.model.OrderInfo;

import java.util.List;

/**
 * Created by ZHY_9 on 2016/10/11.
 */

public class OrderDetailListAdapter extends RecyclerView.Adapter<OrderDetailListAdapter.MyViewHolder>{


    private Context context;
    private List<OrderInfo> data;
    private OrderGoodsListAdapter adapter;

    public OrderDetailListAdapter(Context context, List<OrderInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.orderNumber.setText(data.get(position).getOrderNumber());
        holder.orderStatus.setText(data.get(position).getOrderStatus());
        holder.totalMoney.setText(data.get(position).getOrderTotalMoney());
        holder.goodsList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new OrderGoodsListAdapter(context, data.get(position).getOrderGoodsInfo());
        holder.goodsList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView orderNumber;
        private TextView orderStatus;
        private RecyclerView goodsList;
        private TextView totalMoney;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderNumber = (TextView) itemView.findViewById(R.id.order_list_number);
            orderStatus = (TextView) itemView.findViewById(R.id.order_list_status);
            goodsList = (RecyclerView) itemView.findViewById(R.id.order_goods_list);
            totalMoney = (TextView) itemView.findViewById(R.id.order_total_money);
        }
    }
}
