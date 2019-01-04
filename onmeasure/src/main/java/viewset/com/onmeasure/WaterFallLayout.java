package viewset.com.onmeasure;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WaterFallLayout extends ViewGroup {
    private int columns = 3;
    private int hSpace = 10;
    private int vSpace = 10;
    private int childWidth = 0;
    private int top[];


    public WaterFallLayout(Context context) {
        this(context, null);
    }

    public WaterFallLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterFallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        top = new int[columns];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        childWidth = (measureWidth - ((columns - 1) * columns) * vSpace) / columns;

        int width = 0;
        int height = 0;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int childWidth = lp.leftMargin + child.getMeasuredWidth() + lp.rightMargin;
            int childHeight = lp.topMargin + child.getMeasuredHeight() + lp.bottomMargin;

            width = Math.max(childWidth, width);

            int t = 0;
            for (int j = 0; j < columns; j++) {
                t = Math.min(top[j], t);
            }
            height = t + childHeight;
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? measureWidth : width, (heightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int width = 0;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            width = Math.max(childWidth, width);

            int to = 0;
            for (int j = 0; j < columns; j++) {
                to = Math.min(top[j], to);
            }
        }
    }

    public void addImage(int imgR) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(imgR);
        LayoutParams lp = new LayoutParams(childWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = hSpace;
        lp.bottomMargin = hSpace;
        lp.leftMargin = vSpace;
        lp.rightMargin = vSpace;
        imageView.setLayoutParams(lp);
        addView(imageView);
        requestLayout();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private static class LayoutParams extends MarginLayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
