package viewset.com.onmeasure;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FlowLayout extends ViewGroup {

    private String[] tags;
    private int DEFAULT_PAGGING = 5;
    private int DEFAULT_MARGAIN = 5;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int width = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量子控件
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            //获得子控件的高度和宽度
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            //得到最大宽度，并且累加高度
            width += childWidth;
            height += childHeight;
            if (i > 0) {
                if (width < measureWidth) {
                    height -= childHeight;
                }
            }
        }

        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(Color.BLUE);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int offLeft = 0;
        int offTop = 0;
        int width = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            //测量子控件
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            //获得子控件的高度和宽度
            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth();

            width += lp.leftMargin + childWidth + lp.rightMargin;
            if (width > getMeasuredWidth()) {
                offLeft = 0;
                width = 0;
                offTop += lp.topMargin + childHeight + lp.bottomMargin;
            }

            child.layout(offLeft + lp.leftMargin, lp.topMargin + offTop, offLeft + lp.leftMargin + childWidth, lp.topMargin + childHeight + offTop);

            offLeft += lp.leftMargin + childWidth + lp.rightMargin;
        }
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setStrs(String... strs) {
        tags = strs;
        for (String string : tags) {
            TextView textView = new TextView(getContext());
            textView.setText(string);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(dp2px(DEFAULT_PAGGING), dp2px(DEFAULT_PAGGING), dp2px(DEFAULT_PAGGING), dp2px(DEFAULT_PAGGING));
            textView.setBackgroundResource(R.drawable.shape_bg_layerlist_selecter);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = dp2px(DEFAULT_MARGAIN);
            lp.rightMargin = dp2px(DEFAULT_MARGAIN);
            lp.topMargin = dp2px(DEFAULT_MARGAIN);
            lp.bottomMargin = dp2px(DEFAULT_MARGAIN);
            textView.setLayoutParams(lp);

            addView(textView);
        }
        requestLayout();
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
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
