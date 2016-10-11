package com.zhy_9.tianjinfoodgroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zhy_9.tianjinfoodgroup.R;
import com.zhy_9.tianjinfoodgroup.model.EvaluateInfo;
import com.zhy_9.tianjinfoodgroup.util.TextUtil;

import java.util.List;

/**
 * Created by ZHY_9 on 2016/10/10.
 */

public class EvaluateListAdapter extends RecyclerView.Adapter<EvaluateListAdapter.MyViewHolder>{


    private Context context;
    private List<EvaluateInfo> data;

    public EvaluateListAdapter(Context context, List<EvaluateInfo> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.evaluate_list_item, parent, false));

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.goodsName.setText(TextUtil.setNullToEmpty(data.get(position).getGoodsName()));
//        holder.goodsNumber.setText(0);
        holder.username.setText(TextUtil.setNullToEmpty(data.get(position).getLoginName()));
        holder.content.setText(TextUtil.setNullToEmpty(data.get(position).getContent()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView goodsName;
        private TextView goodsNumber;
        private ImageView goodsImg;
        private RatingBar goodsScore;
        private RatingBar timeScore;
        private RatingBar serviceScore;
        private TextView username;
        private TextView content;

        public MyViewHolder(View itemView) {
            super(itemView);
            goodsName = (TextView) itemView.findViewById(R.id.evaluate_goods_name);
            goodsNumber = (TextView) itemView.findViewById(R.id.evaluate_goods_number);
            goodsImg = (ImageView) itemView.findViewById(R.id.evaluate_goods_img);
            goodsScore = (RatingBar) itemView.findViewById(R.id.evaluate_goods_score);
            timeScore = (RatingBar) itemView.findViewById(R.id.evaluate_time_score);
            serviceScore = (RatingBar) itemView.findViewById(R.id.evaluate_service_score);
            username = (TextView) itemView.findViewById(R.id.evaluate_username);
            content = (TextView) itemView.findViewById(R.id.evaluate_content);
        }
    }
}