package com.zhy_9.tianjinfoodgroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy_9.tianjinfoodgroup.R;
import com.zhy_9.tianjinfoodgroup.model.MallMessage;

import java.util.List;

/**
 * Created by ZHY_9 on 2016/10/12.
 */

public class MallMessageAdapter extends RecyclerView.Adapter<MallMessageAdapter.MyViewHolder> {

    private Context context;
    private List<MallMessage> data;

    public MallMessageAdapter(Context context, List<MallMessage> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.mall_message_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.msgTime.setText(data.get(position).getCreateTime());
        holder.msgStatus.setText(data.get(position).getStatus());
        holder.msgContent.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView msgTime;
        private TextView msgStatus;
        private TextView msgContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            msgTime = (TextView) itemView.findViewById(R.id.mall_msg_time);
            msgStatus = (TextView) itemView.findViewById(R.id.mall_msg_status);
            msgContent = (TextView) itemView.findViewById(R.id.mall_msg_content);

        }
    }
}
