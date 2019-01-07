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
import android.widget.ScrollView;

public class PullScrollView extends ScrollView {

    Point mTouchPoint = new Point();
    private View mRootView;
    private int mpreY;
    private ValueAnimator anim;
    private Rect mNormalRect;

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
    public boolean onTouchEvent(MotionEvent event) {
        float curY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mRootView != null) {
                    mNormalRect = new Rect(mRootView.getLeft(), mRootView.getTop(), mRootView.getRight(), mRootView.getBottom());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int delta = (int) ((curY - mpreY) * 0.25);
                if (delta > 0) {
                    mRootView.layout(mRootView.getLeft(), mRootView.getTop() + delta, mRootView.getRight(), mRootView.getBottom() + delta);
                }
                break;
            case MotionEvent.ACTION_UP:
                int curTop = mRootView.getTop();
                Log.e("ttt", curTop - mNormalRect.top + "");
                mRootView.layout(mNormalRect.left, mNormalRect.top, mNormalRect.right, mNormalRect.bottom);
                TranslateAnimation animation = new TranslateAnimation(0, 0, curTop - mNormalRect.top, 0);
                animation.setDuration(200);
                mRootView.startAnimation(animation);
                break;
        }
        mpreY = (int) curY;
        return super.onTouchEvent(event);
    }
}
