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
 * Created by ZHY_9 on 2016/10/9.
 */

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder>{

    private Context context;
    private List<GoodsInfo> data;

    public OrderListAdapter(Context context, List<GoodsInfo> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.goods_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.goodsName.setText(data.get(position).getGoodsName());
        holder.goodsPrice.setText(data.get(position).getShopPrice());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView goodsImg;
        private TextView goodsName;
        private TextView goodsPrice;
        private TextView goodsInventory;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodsImg = (ImageView) itemView.findViewById(R.id.order_list_item_img);
            goodsName = (TextView) itemView.findViewById(R.id.order_list_item_name);
            goodsPrice = (TextView) itemView.findViewById(R.id.order_list_item_price);
            goodsInventory = (TextView) itemView.findViewById(R.id.order_list_item_inventory);
        }
    }
}
