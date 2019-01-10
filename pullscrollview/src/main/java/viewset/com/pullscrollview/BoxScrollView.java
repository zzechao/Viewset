package viewset.com.pullscrollview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class BoxScrollView extends ScrollView {

    Point changePoint = new Point();
    private BoxLinearLayout layout;

    public BoxScrollView(Context context) {
        this(context, null);
    }

    public BoxScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addBox();
        changePoint.set(0, BoxLinearLayout.maxHeight);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
//        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
//
//        int width = 0;
//        int height = 0;
//
//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
//            LayoutParams lp = (LayoutParams) child.getLayoutParams();
//
//            int childWidth = lp.leftMargin + child.getMeasuredWidth() + lp.rightMargin;
//            int childHeight = lp.topMargin + child.getMeasuredHeight() + lp.bottomMargin;
//
//            width = Math.max(childWidth, width);
//
//            height += BoxLinearLayout.dip2px(getContext(), BoxLinearLayout.maxHeight);
//        }
//
//        height = height - BoxLinearLayout.dip2px(getContext(), BoxLinearLayout.maxHeight)
//                + BoxLinearLayout.dip2px(getContext(), BoxLinearLayout.minHeight);
//
//        setMeasuredDimension(measureWidth, (heightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
//    }

    public void addBox() {
        layout = new BoxLinearLayout(getContext());
        for (int i = 0; i < 4; i++) {
            BoxView boxView = new BoxView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            switch (i) {
                case 0:
                    boxView.setBackgroundColor(Color.parseColor("#ff0000"));
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BoxLinearLayout.maxHeight);
                    break;
                case 1:
                    boxView.setBackgroundColor(Color.parseColor("#00ff00"));
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BoxLinearLayout.minHeight);
                    break;
                case 2:
                    boxView.setBackgroundColor(Color.parseColor("#0000ff"));
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BoxLinearLayout.minHeight);
                    break;
                case 3:
                    boxView.setBackgroundColor(Color.parseColor("#00ffff"));
                    lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, BoxLinearLayout.minHeight);
                    break;
            }
            boxView.setLayoutParams(lp);
            layout.addView(boxView);
        }
        addView(layout);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int dealY = t - oldt;
        if ((t - oldt) > 0) {
            layout.up(dealY);
        } else {
            layout.down(dealY);
        }
    }

    public static class BoxLinearLayout extends LinearLayout {

        public static int maxHeight;
        public static int minHeight;

        public BoxLinearLayout(Context context) {
            this(context, null);
        }

        public BoxLinearLayout(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public BoxLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setOrientation(LinearLayout.VERTICAL);
            maxHeight = dip2px(context, 350);
            minHeight = dip2px(context, 150);
        }

        public static int dip2px(Context context, float dpValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {
            super.onLayout(changed, l, t, r, b);
            int count = getChildCount();
            int tt = 0;
            for (int i = 0; i < count; i++) {
                BoxView child = (BoxView) getChildAt(i);
                if (i == 0) {
                    int left = l;
                    int top = tt;
                    int right = r;
                    int bottom = tt + child.getMeasuredHeight();
                    tt = bottom;
                    child.layout(left, top, right, bottom);
                }
            }
        }

        /**
         * 上滑
         *
         * @param dealY
         */
        public void up(int dealY) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                BoxView child = (BoxView) getChildAt(i);
                Rect rect = new Rect();

                final int[] l = new int[2];
                child.getLocationOnScreen(l);
                boolean visiable = child.getLocalVisibleRect(rect);
                if (visiable && rect.bottom < maxHeight && rect.bottom >= minHeight && l[1] < maxHeight) {
                    child.getLayoutParams().height = (child.getLayoutParams().height + dealY) > maxHeight ? maxHeight : (child.getLayoutParams().height + dealY);
                    requestLayout();
                    break;
                }
            }
        }

        /**
         * 下滑
         *
         * @param dealY
         */
        public void down(int dealY) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                BoxView child = (BoxView) getChildAt(i);
                Rect rect = new Rect();

                final int[] l = new int[2];
                child.getLocationOnScreen(l);
                boolean visiable = child.getLocalVisibleRect(rect);
                if (visiable && rect.bottom <= maxHeight && rect.bottom > minHeight && l[1] > minHeight) {
                    child.getLayoutParams().height = (child.getLayoutParams().height + dealY) < minHeight ? minHeight : (child.getLayoutParams().height + dealY);
                    requestLayout();
                    break;
                }
            }
        }
    }

    public static class BoxView extends ViewGroup {
        public BoxView(Context context) {
            super(context);
        }

        public BoxView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BoxView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onLayout(boolean changed, int l, int t, int r, int b) {

        }
    }
}
