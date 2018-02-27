package com.example.lqqq.careui.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lqqq.careui.R;

import java.util.List;

/**
 * 医生列表适配
 * Created by LQQQ1 on 2018/2/24.
 */

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    private List<String> names;//医生数据源
    public DoctorsAdapter(List<String> names){
        this.names = names;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext().getApplicationContext();
        return new ViewHolder(View.inflate(context,R.layout.item_doctors,null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = names.get(position);
        holder.textView.setText(name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;//医生名称

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.doctor_name);
        }
    }
}
