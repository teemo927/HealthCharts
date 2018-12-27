package com.timo.swipelayoutlib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * @Description: 列表中左滑删除
 * @Copyright 2018 中金慈云健康科技有限公司
 * @Created by 汤迪 on 2018/12/27
 */
public class SwipeDeleteItem extends RelativeLayout {

    private ViewDragHelper mDragHelper;

    public SwipeDeleteItem(Context context) {
        this(context,null);
    }

    public SwipeDeleteItem(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwipeDeleteItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this,1.0f,callback);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        requestDisallowInterceptTouchEvent(true);
        if (mDragHelper.shouldInterceptTouchEvent(event)) {
            return true;
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return true;//所有的子元素都可以移动
        }


        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }
    };

}
