package viewset.com.recyclewview.threeLayoutManager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * https://blog.csdn.net/zxt0601/article/details/52948009
 */
public class LayoutManager1 extends RecyclerView.LayoutManager {
    private int mSumDy;

    private SparseArray<Rect> mItemRects = new SparseArray<>();

    private int mTotalHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        if (state.getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        detachAndScrapAttachedViews(recycler);

        Log.e("ttt","onLayoutChildren");
        View childView = recycler.getViewForPosition(0);
//        measureChildWithMargins(childView, 0, 0);
//        int mItemWidht = getDecoratedMeasuredWidth(childView);
//        int mItemHeight = getDecoratedMeasuredHeight(childView);

//        int count = state.getItemCount();
//        int top = 0;
//        for (int i = 0; i < count; i++) {
//            Rect rect = new Rect(0, top, mItemWidht, top + mItemHeight);
//            top += mItemHeight;
//            mItemRects.append(i, rect);
//        }
//
//        int visableCount = (int) Math.ceil(getVerticalSpace() * 1F / mItemHeight);
//        for (int i = 0; i < visableCount; i++) {
//            View view = recycler.getViewForPosition(i);
//            int pos = getPosition(view);
//            Rect rect = mItemRects.get(pos);
//            addView(view);
//            // 重点
//            measureChildWithMargins(view, 0, 0);
//            layoutDecoratedWithMargins(view, rect.left, rect.top, rect.right, rect.bottom);
//        }
//        mTotalHeight = Math.max(top, getVerticalSpace());
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
            } else if (travel < 0) {//回收当前屏幕，下越界的View
                if (getDecoratedTop(child) - travel > getHeight() - getPaddingBottom()) {
                    removeAndRecycleView(child, recycler);
                    continue;
                }
            }
        }

        Rect visibleRect = getVisibleArea(travel);
        //布局子View阶段
        if (travel >= 0) {
            View lastView = getChildAt(getChildCount() - 1);

            int minPos = getPosition(lastView) + 1;//从最后一个View+1开始吧\
            for (int i = minPos; i <= getItemCount() - 1; i++) {
                Rect rect = mItemRects.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecorated(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);
                } else {
                    break;
                }
            }
        } else {
            View firstView = getChildAt(0);
            int maxPos = getPosition(firstView) - 1;

            for (int i = maxPos; i >= 0; i--) {
                Rect rect = mItemRects.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child, 0);//将View添加至RecyclerView中，childIndex为1，但是View的位置还是由layout的位置决定
                    measureChildWithMargins(child, 0, 0);
                    layoutDecoratedWithMargins(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);
                } else {
                    break;
                }
            }
        }

        mSumDy += travel;
        // 平移容器内的item
        offsetChildrenVertical(-travel);
        return dy;
    }

    private Rect getVisibleArea(int travel) {
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mSumDy + travel, getWidth() + getPaddingRight(), getVerticalSpace() + mSumDy + travel);
        return result;
    }
}
