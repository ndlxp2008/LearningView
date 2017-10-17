package com.jaj.aho.learningview.CustmerView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * 滑动的方式，平滑过度的简单实现
 */

public class MoveView extends View {

    private int lastX;
    private int lastY;
    private Context mContext;
    private Scroller mScroller;

    public MoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mScroller = new Scroller(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                //偏移量的正负，看情况而定
                int offseX = x - lastX;
                int offseY = y - lastY;
//                //method 1    用layout方法指定左上，右下的坐标，计算偏移量
//                layout(getLeft() + offseX, getTop() + offseY, getRight() + offseX, getBottom() + offseY);


//                //methond 2   用父类的方法
//                offsetLeftAndRight(offseX);
//                offsetTopAndBottom(offseY);


                //methond 3 RelativeLayout为父容器的情况下
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offseX;
//                layoutParams.topMargin = getTop() + offseY;
//                setLayoutParams(layoutParams);


                //methond 4   不确定时linearlayout或者Relativelayout的情况下
//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + offseX;
//                layoutParams.topMargin = getTop() + offseY;
//                setLayoutParams(layoutParams);


                //methond 5 动画的形式来移动   并没有什么效果
//                <set xmlns:android="http://schemas.android.com/apk/res/android">
//                <translate android:fromXDelta="0" android:toXDelta="300" android:duration="1000"/>
//                </set>
                //在Activity中调用  mCustomView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.translate));
//                setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.translate));
                //属性动画支持效果很好
//                ObjectAnimator.ofFloat(this, "translationX", 0, 300).setDuration(1000).start();


                //methon 6
                //通过scrollTo和scrollBy
                //scrollBy用于ViewGroup，则包含的子View都会被拖动。-offseX是跟随手势去滑动的
//                ((View) getParent()).scrollBy(-offseX, -offseY);

                //methon 7
                //通过scroller来过渡滑动
                //1.重写computeScroll()方法
                //2.创建smoothScrollTo（）方法.参数是偏移量x,y
                smoothScrollTo(-400, -500);
                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通过重绘来重新调用computeScroll()
            //直接调用invalidate()方法，请求重新draw()，但只会绘制调用者本身。
            //requestLayout()方法 ：会导致调用measure()过程 和 layout()过程
            invalidate();
        }
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();//当前Scroll的X坐标
        int deltaX = destX - scrollX;//目的的X坐标

        int scrollY = getScrollY();
        ;//当前Scroll的Y的坐标
        int deltaY = destY - scrollY;//目的的Y坐标

        //1000秒内滑向destX
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 10 * 1000);
        invalidate();
    }
}