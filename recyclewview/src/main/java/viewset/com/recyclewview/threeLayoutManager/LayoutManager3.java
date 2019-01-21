package viewset.com.recyclewview.threeLayoutManager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * https://kymjs.com/code/2016/07/10/01/
 * https://blog.csdn.net/zxt0601/article/details/52948009
 */
public class LayoutManager3 extends RecyclerView.LayoutManager {
    private int mSumDy;

    private SparseArray<Rect> mItemRects = new SparseArray<>();

    private int mTotalHeight;
    private int mItemWidht;
    private int mItemHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //super.onLayoutChildren(recycler, state);
        if (getItemCount() == 0) {//没有Item，界面空着吧
            detachAndScrapAttachedViews(recycler);
            return;
        }
        detachAndScrapAttachedViews(recycler);

        //将item的位置存储起来
        if (mItemWidht == 0 && mItemHeight == 0) {
            View childView = recycler.getViewForPosition(0);
            measureChildWithMargins(childView, 0, 0);
            measureChildWithMargins(childView, 0, 0);
            mItemWidht = getDecoratedMeasuredWidth(childView);
            mItemHeight = getDecoratedMeasuredHeight(childView);
        }

        int count = state.getItemCount();
        int top = 0;
        for (int i = 0; i < count; i++) {
            Rect rect = new Rect(0, top, mItemWidht, top + mItemHeight);
            top += mItemHeight;
            mItemRects.append(i, rect);
        }

        int visableCount = (int) Math.ceil(getVerticalSpace() * 1F / mItemHeight);
        for (int i = 0; i < visableCount; i++) {
            View view = recycler.getViewForPosition(i);
            int pos = getPosition(view);
            Rect rect = mItemRects.get(pos);
            addView(view);
            // 重点
            measureChildWithMargins(view, 0, 0);
            layoutDecoratedWithMargins(view, rect.left, rect.top, rect.right, rect.bottom);
        }
        mTotalHeight = Math.max(top, getVerticalSpace());
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
        Log.e("ttt", dy + "----");
        int travel = dy;
        //如果滑动到最顶部
        if (mSumDy + dy < 0) {
            travel = -mSumDy;
        } else if (mSumDy + dy > mTotalHeight - getVerticalSpace()) {
            travel = mTotalHeight - getVerticalSpace() - mSumDy;
        }

        Rect visibleRect = getVisibleArea(travel);
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int position = getPosition(child);
            Rect rect = mItemRects.get(position);

            if (!Rect.intersects(rect, visibleRect)) {
                removeAndRecycleView(child, recycler); // remove和回收超出屏幕的view
            }
        }

        View firstView = getChildAt(0);
        View lastView = getChildAt(getChildCount() - 1);
        detachAndScrapAttachedViews(recycler); // 全局的离屏缓存
        //布局子View阶段
        if (travel >= 0) {
            int minPos = getPosition(firstView);
            for (int i = minPos; i <= getItemCount() - 1; i++) {
                Rect rect = mItemRects.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child);
                    measureChildWithMargins(child, 0, 0);
                    layoutDecorated(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);

                    child.setRotationY((child.getRotationY() + 1) % 360);
                } else {
                    break;
                }
            }
        } else {
            int maxPos = getPosition(lastView);
            for (int i = maxPos; i >= 0; i--) {
                Rect rect = mItemRects.get(i);
                if (Rect.intersects(visibleRect, rect)) {
                    View child = recycler.getViewForPosition(i);
                    addView(child, 0);//将View添加至RecyclerView中，childIndex为1，但是View的位置还是由layout的位置决定
                    measureChildWithMargins(child, 0, 0);
                    layoutDecoratedWithMargins(child, rect.left, rect.top - mSumDy, rect.right, rect.bottom - mSumDy);

                    child.setRotationY((child.getRotationY() - 1) % 360);
                } else {
                    break;
                }
            }
        }

        mSumDy += travel;
        return dy;
    }

    private Rect getVisibleArea(int travel) {
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mSumDy + travel, getWidth() + getPaddingRight(), getVerticalSpace() + mSumDy + travel);
        return result;
    }
}
