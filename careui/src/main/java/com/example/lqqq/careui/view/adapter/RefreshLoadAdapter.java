package com.example.lqqq.careui.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lqqq.careui.R;

/**
 * 添加上拉加载适配器
 * Created by LQQQ1 on 2018/2/24.
 */

public class RefreshLoadAdapter extends RecyclerView.Adapter{
    /** 线性 */
    public static final int ADAPTER_TYPE_LINEAR = 0x01;
    /** 网格 */
    public static final int ADAPTER_TYPE_GRID = 0x02;
    /** view type : "上拉加载更多" */
    private static final int ITEM_TYPE_LOAD = Integer.MAX_VALUE / 2;


    private RecyclerView.Adapter mAdapter;

    private boolean mShowLoadItem = true;

    private WrapperHolder mWrapperHolder;

    private int mAdapterType = ADAPTER_TYPE_LINEAR;

    private int mSpanCount;

    public RefreshLoadAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;
    }

    /** 设置Wrapper的类型, 默认是线性的 */
    public void setAdapterType(int type) {
        if (mAdapterType != type) {
            mAdapterType = type;
        }
    }

    /** 网格布局的网格数量 */
    public void setSpanCount(int count) {
        if (count != mSpanCount) {
            mSpanCount = count;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_LOAD) {
            if (mWrapperHolder == null) {
                mWrapperHolder = new WrapperHolder(View.inflate(parent.getContext(), R.layout.item_load_more, null));
            }
            return mWrapperHolder;
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    // 允许显示"加载更多"item, 并且position为末尾时,拦截
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mShowLoadItem && position == getItemCount() - 1) {
            // 最后一项 不需要做什么额外的事
        } else if (position < mAdapter.getItemCount()){
            // 正常情况
            mAdapter.onBindViewHolder(holder, position);
        } else {
            // 网格的补空的情况
            holder.itemView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapterType == ADAPTER_TYPE_LINEAR) {
            // 线性布局
            return mShowLoadItem ? mAdapter.getItemCount() + 1 : mAdapter.getItemCount();
        } else {
            // 网格布局
            if (!mShowLoadItem)
                return mAdapter.getItemCount();// 不显示load more时直接返回真实数量
            int remain = mAdapter.getItemCount() % mSpanCount; // 余数
            if (remain == 0) {
                return mAdapter.getItemCount() + 1;
            }
            // 余数不为0时,先凑满再加1
            return mAdapter.getItemCount() + 1 + (mSpanCount - remain);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 当显示"加载更多"条目, 并且位置是最后一个时, wrapper进行拦截
        if (mShowLoadItem && position == getItemCount() - 1) {
            return ITEM_TYPE_LOAD;// 注意要避免和原生adapter返回值重复
        }
        // 其他情况交给原生adapter处理
        return mAdapter.getItemViewType(position);
    }

    public void setLoadItemVisibility(boolean isShow) {
        if (mShowLoadItem != isShow) {
            mShowLoadItem = isShow;
            notifyDataSetChanged();
        }
    }

    public void setLoadItemState(boolean isLoading) {
        if (isLoading) {
            mWrapperHolder.setLoadText("正在加载...");
            mWrapperHolder.setLoadPbVisibility(true);
        } else {
            mWrapperHolder.setLoadText("上拉加载更多");
            mWrapperHolder.setLoadPbVisibility(false);
        }
    }

    class WrapperHolder extends RecyclerView.ViewHolder {
        TextView mLoadTv;
        ProgressBar mLoadPb;
        WrapperHolder(View itemView) {
            super(itemView);
            mLoadTv = itemView.findViewById(R.id.item_load_tv);
            mLoadPb = itemView.findViewById(R.id.item_load_pb);
        }

        void setLoadText(CharSequence text) {
            mLoadTv.setText(text);
        }

        void setLoadPbVisibility(boolean show) {
            mLoadPb.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }
}
