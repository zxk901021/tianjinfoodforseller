package com.zhy_9.tianjinfoodgroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhy_9.tianjinfoodgroup.R;
import com.zhy_9.tianjinfoodgroup.model.EvaluateInfo;

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

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
