package viewset.com.recyclewview.threeLayoutManager;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

public class LayoutManager1 extends RecyclerView.LayoutManager {
    private int mCurrentY;
    private int mSumDy;

    private SparseArray<Rect> rect = new SparseArray<>();

    private int mTotalHeight;
    private int mItemWidth;
    private int mItemHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);

    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (state.getItemCount() == 0) {
            this.removeAndRecycleAllViews(recycler);
            return;
        }
        this.removeAndRecycleAllViews(recycler);

        View childView = recycler.getViewForPosition(0);
        measureChildWithMargins(childView, 0, 0);
        mItemWidth = getDecoratedMeasuredWidth(childView);
        mItemHeight = getDecoratedMeasuredHeight(childView);

        for (int i = 0; i < getItemCount(); i++) {

        }

        int visableCount = (int) Math.ceil(getVerticalSpace() * 1F / mItemHeight);
        if (state.getItemCount() < visableCount) {
            mTotalHeight = getVerticalSpace();
        } else {
            int count = state.getItemCount();
            int top = 0;
            for (int i = 0; i < visableCount; i++) {
                View view = recycler.getViewForPosition(i);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int width = getDecoratedMeasuredWidth(view);
                int height = getDecoratedMeasuredHeight(view);
                layoutDecoratedWithMargins(view, 0, top, width, top + height);
                top += height;
            }
            mTotalHeight = count * mItemHeight;
        }
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        Log.e("ttt", "----" + dy + "----");
        int travel = dy;
        //如果滑动到最顶部
        if (mSumDy + dy < 0) {
            travel = -mSumDy;
        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {
            travel = mTotalHeight - getVerticalSpace() - mSumDy;
        }

        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (travel > 0) {//需要回收当前屏幕，上越界的View
                if (getDecoratedBottom(child) - travel < 0) {
                    removeAndRecycleView(child, recycler);
                    continue;
                }
            } else {
                if (getDecoratedTop(child) + travel > getVerticalSpace()) {
                    removeAndRecycleView(child, recycler);
                    continue;
                }
            }
        }

        mSumDy += travel;
        // 平移容器内的item
        offsetChildrenVertical(-travel);
        return dy;
    }
}
