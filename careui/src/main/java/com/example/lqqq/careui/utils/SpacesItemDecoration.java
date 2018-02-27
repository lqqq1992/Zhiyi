package com.example.lqqq.careui.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recyclerView 间距设置帮助类
 * Created by LQQQ1 on 2018/2/27.
 */

public final class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = 8;
        outRect.right = 8;
        outRect.bottom = 30;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = 10;
    }
}
