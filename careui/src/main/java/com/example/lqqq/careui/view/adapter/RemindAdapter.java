package com.example.lqqq.careui.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lqqq.careui.R;

import java.util.List;

/**
 * 提醒列表适配
 * Created by LQQQ1 on 2018/2/27.
 */

public class RemindAdapter extends RecyclerView.Adapter<RemindAdapter.ViewHolder> {

    private List<String> remindNames;//提醒消息数据源
    public RemindAdapter(List<String> remindNames){
        this.remindNames = remindNames;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext().getApplicationContext();
        return new ViewHolder(View.inflate(context, R.layout.item_remind,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.remindName.setText(remindNames.get(position));
    }

    @Override
    public int getItemCount() {
        return remindNames.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView remindName;//提醒的标题

        public ViewHolder(View itemView) {
            super(itemView);
            remindName = itemView.findViewById(R.id.remind_name);
        }
    }
}
