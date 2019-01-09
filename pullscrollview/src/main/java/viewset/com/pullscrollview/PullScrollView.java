package viewset.com.pullscrollview;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;

public class PullScrollView extends ScrollView {

    Point mTouchPoint = new Point();
    private View mContentView;
    private Rect mHeadInitRect = new Rect();
    private Rect mContentInitRect = new Rect();
    private ImageView mHeaderView;
    private float SCROLL_RATIO = 0.4f;
    private boolean mIsMoving;

    public PullScrollView(Context context) {
        this(context, null);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mContentView = getChildAt(0);
        }
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mHeaderView != null) {
            mContentView.layout(l, mHeaderView.getBottom() + mContentView.getTop() - 50, r, mHeaderView.getBottom() + mContentView.getBottom() - 50);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //保存原始位置
            mTouchPoint.set((int) event.getX(), (int) event.getY());
            mHeadInitRect.set(mHeaderView.getLeft(), mHeaderView.getTop(), mHeaderView.getRight(), mHeaderView.getBottom());
            mContentInitRect.set(mContentView.getLeft(), mContentView.getTop(), mContentView.getRight(), mContentView.getBottom());
            mIsMoving = false;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //如果当前的事件是我们要处理的事件时，比如现在的下拉，这时候，我们就不能让子控件来处理这个事件
            //这里就需要把它截获，不传给子控件，更不能让子控件消费这个事件
            //不然子控件的行为就可能与我们的相冲突
            int deltaY = (int) event.getY() - mTouchPoint.y;
            deltaY = deltaY > mHeaderView.getHeight() ? mHeaderView.getHeight() : deltaY;
            if (deltaY > 0 && deltaY >= getScrollY()) {
                onTouchEvent(event);
                return true;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int deltaY = (int) event.getY() - mTouchPoint.y;
                deltaY = deltaY > mContentView.getHeight() ? mContentView.getHeight() : deltaY;
                if (deltaY > 0 && deltaY >= getScrollY()) {
                    float headerMoveHeight = deltaY * 0.5f * SCROLL_RATIO;
                    int mHeaderCurTop = (int) (mHeadInitRect.top + headerMoveHeight);
                    int mHeaderCurBottom = (int) (mHeadInitRect.bottom + headerMoveHeight);

                    float contentMoveHeight = deltaY * SCROLL_RATIO;
                    int mContentTop = (int) (mContentInitRect.top + contentMoveHeight);
                    int mContentBottom = (int) (mContentInitRect.bottom + contentMoveHeight);

                    if (mContentTop <= mHeaderCurBottom) {
                        mHeaderView.layout(mHeadInitRect.left, mHeaderCurTop, mHeadInitRect.right, mHeaderCurBottom);
                        mContentView.layout(mContentInitRect.left, mContentTop, mContentInitRect.right, mContentBottom);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                AnimatorSet set = new AnimatorSet();

                ValueAnimator animator = ValueAnimator.ofInt(mContentView.getTop() - mContentInitRect.top, 0);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mContentView.layout(mContentInitRect.left, mContentInitRect.top + (int) animation.getAnimatedValue(), mContentInitRect.right, mContentInitRect.bottom + (int) animation.getAnimatedValue());
                    }
                });

                ValueAnimator animator2 = ValueAnimator.ofInt(mHeaderView.getBottom() - mHeadInitRect.bottom, 0);
                animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mHeaderView.layout(mHeadInitRect.left, mHeadInitRect.top + (int) animation.getAnimatedValue(), mHeadInitRect.right, mHeadInitRect.bottom + (int) animation.getAnimatedValue());
                    }
                });

                set.playTogether(animator, animator2);
                set.setDuration(200);
                set.setInterpolator(new DecelerateInterpolator());
                set.start();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setScaleView(ImageView scaleView) {
        this.mHeaderView = scaleView;
        requestLayout();
    }
}
