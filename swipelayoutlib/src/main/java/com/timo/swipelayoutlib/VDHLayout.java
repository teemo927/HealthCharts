package com.timo.swipelayoutlib;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * https://blog.csdn.net/lmj623565791/article/details/46858663
 *
 * @author teem
 * @since 2018/12/27
 */
public class VDHLayout extends LinearLayout {

    private ViewDragHelper mDragHelper;

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    private Point mAutoBackOriginPos = new Point();

    public VDHLayout(Context context) {
        this(context, null);
    }

    public VDHLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VDHLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDragHelper = ViewDragHelper.create(this, 1.0f, callback);

        mDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 边界检测、加速度检测(eg：DrawerLayout边界触发拉出)
     * 回调Drag Release（eg：DrawerLayout部分，手指抬起，自动展开/收缩）
     * 移动到某个指定的位置(eg:点击Button，展开/关闭Drawerlayout)
     */
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View view, int i) {
            return view != mEdgeTrackerView;
        }


        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //mAutoBackView手指释放时可以自动回去
            if (releasedChild == mAutoBackView) {
                mDragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            }
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mDragHelper.captureChildView(mEdgeTrackerView, pointerId);
        }

        //用Button测试，或者给TextView添加了clickable = true
        @Override
        public int getViewHorizontalDragRange(View child) {
            return getMeasuredWidth() - child.getMeasuredWidth();
        }

        //用Button测试，或者给TextView添加了clickable = true
        @Override
        public int getViewVerticalDragRange(View child) {
            return getMeasuredHeight() - child.getMeasuredHeight();
        }

    };

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        //保存了最开启的位置信息
        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 3) {
            return;
        }

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }
}
