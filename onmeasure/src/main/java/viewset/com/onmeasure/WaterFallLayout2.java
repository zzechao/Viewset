package viewset.com.onmeasure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WaterFallLayout2 extends ViewGroup {
    private int columns = 3;
    private int hSpace = 20;
    private int vSpace = 20;
    private int childWidth = 0;
    private int top[];
    private OnItemClick mItemClick;


    public WaterFallLayout2(Context context) {
        this(context, null);
    }

    public WaterFallLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterFallLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
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

        childWidth = (measureWidth - (columns - 1) * vSpace) / columns;

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


            int t = top[0];
            int z = 0;
            for (int j = columns - 1; j >= 0; j--) {
                if (top[j] <= t) {
                    t = top[j];
                    z = j;
                }
            }
            int h = t + childHeight + hSpace;
            top[z] = h;

            // 计算出对应imageview的left、top、right、bottom
            lp.left = z * (childWidth + vSpace);
            lp.top = t + hSpace;
            lp.right = lp.left + childWidth;
            lp.bottom = lp.top + childHeight;

            // 计算出最大的height
            height = Math.max(lp.bottom, height);
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? measureWidth : width, (heightMode == MeasureSpec.EXACTLY) ? measureHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            child.layout(lp.left, lp.top, lp.right, lp.bottom);

            final int finalI = i;
            child.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClick != null) {
                        mItemClick.itemClick(finalI + 1);
                    }
                }
            });
        }
    }

    public void addImage(int imgR) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imgR);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageResource(imgR);
        LayoutParams lp = new LayoutParams(childWidth, (int) ((childWidth * 1F / bitmap.getWidth()) * bitmap.getHeight()));
        imageView.setLayoutParams(lp);
        imageView.setClickable(true);
        addView(imageView);
        top = new int[columns];
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

    public interface OnItemClick {
        void itemClick(int position);
    }

    public void setOnItemClick(final OnItemClick itemClick) {
        mItemClick = itemClick;
    }

    private static class LayoutParams extends MarginLayoutParams {
        public int left;
        public int top;
        public int right;
        public int bottom;

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
