package viewset.com.pullscrollview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ScrollView;

public class PullScrollView extends ScrollView {

    Point mTouchPoint = new Point();
    private View mRootView;
    private int mpreY;
    private ValueAnimator anim;
    private Rect mImageRect;
    private int endBottom;
    private Rect mNormalRect;
    private ImageView mScaleView;

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
            mRootView = getChildAt(0);
        }
        super.onFinishInflate();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mScaleView != null) {
            mRootView.layout(l, mScaleView.getBottom() + mRootView.getTop(), r, mScaleView.getBottom() + mRootView.getBottom());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float curY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mRootView != null) {
                    mNormalRect = new Rect(mRootView.getLeft(), mRootView.getTop(), mRootView.getRight(), mRootView.getBottom());
                    mImageRect = new Rect(mScaleView.getLeft(), mScaleView.getTop(), mScaleView.getRight(), mScaleView.getBottom());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int delta = (int) ((curY - mpreY) * 0.25);
                if (delta > 0) {
                    mRootView.layout(mRootView.getLeft(), mRootView.getTop() + delta, mRootView.getRight(), mRootView.getBottom() + delta);
                    mScaleView.layout(mScaleView.getLeft(), mScaleView.getTop(), mRootView.getRight(), mScaleView.getBottom() + delta);
                }
                break;
            case MotionEvent.ACTION_UP:
                int curTop = mRootView.getTop();
                Log.e("ttt", curTop - mNormalRect.top + "");
                mRootView.layout(mNormalRect.left, mNormalRect.top, mNormalRect.right, mNormalRect.bottom);
                TranslateAnimation animation = new TranslateAnimation(0, 0, curTop - mNormalRect.top, 0);
                animation.setDuration(200);
                mRootView.startAnimation(animation);

                endBottom = (mScaleView.getBottom() - mImageRect.bottom);
                ValueAnimator animator = ValueAnimator.ofInt(mScaleView.getBottom() - mImageRect.bottom, 0);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        mScaleView.layout(mScaleView.getLeft(), mScaleView.getTop(), mScaleView.getRight(), mImageRect.bottom + (int) animation.getAnimatedValue());
                    }
                });
                animator.setDuration(200);
                animator.start();
                break;
        }
        mpreY = (int) curY;
        return super.onTouchEvent(event);
    }

    public void setScaleView(ImageView scaleView) {
        this.mScaleView = scaleView;
        requestLayout();
    }
}
