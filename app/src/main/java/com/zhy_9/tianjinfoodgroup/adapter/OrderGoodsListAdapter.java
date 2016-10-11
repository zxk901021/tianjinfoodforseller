package com.zhy_9.tianjinfoodgroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy_9.tianjinfoodgroup.R;
import com.zhy_9.tianjinfoodgroup.model.GoodsInfo;

import java.util.List;

/**
 * Created by ZHY_9 on 2016/10/11.
 */

public class OrderGoodsListAdapter extends RecyclerView.Adapter<OrderGoodsListAdapter.MyViewHolder>{

    private Context context;
    private List<GoodsInfo> data;

    public OrderGoodsListAdapter(Context context, List<GoodsInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.order_goods_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.goodsName.setText(data.get(position).getGoodsName());
        holder.goodsPrice.setText(data.get(position).getShopPrice());
        holder.goodsNumber.setText(data.get(position).getSaleCount());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView goodsImg;
        TextView goodsName;
        TextView goodsPrice;
        TextView goodsNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodsImg = (ImageView) itemView.findViewById(R.id.order_goods_list_img);
            goodsName = (TextView) itemView.findViewById(R.id.order_goods_list_name);
            goodsPrice = (TextView) itemView.findViewById(R.id.order_goods_list_price);
            goodsNumber = (TextView) itemView.findViewById(R.id.order_goods_list_number);
        }
    }
}
