package com.abu.utilsmodule;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
* 说明: 自定义ScrollView，获取滑动事件，滑动时重置倒计时
* 作者: WenHugh
* 添加时间: 2019-8-8 15:06
* 修改人: WenHugh
* 修改时间: 2019-8-8 15:06
**/

public class SelfScrollView extends ScrollView {
    private OnScrollListener listener;
    public SelfScrollView(Context context) {
        super(context);
    }
    public void setOnScrollListener(OnScrollListener listener){
        this.listener = listener;
    }

    public SelfScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener{
        void onScroll(int scrolly);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (listener != null){
            listener.onScroll(getScrollY());
        }
    }
}
