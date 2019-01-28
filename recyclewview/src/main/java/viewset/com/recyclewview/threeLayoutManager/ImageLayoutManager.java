package viewset.com.recyclewview.threeLayoutManager;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.ViewGroup;

public class ImageLayoutManager extends RecyclerView.LayoutManager {

    private SparseArray<Rect> mItemRects = new SparseArray<>();
    private SparseBooleanArray mHasAttachedItems = new SparseBooleanArray();

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

        if (getItemCount() == 0 || state.isPreLayout()) {//没有Item，界面空着吧
            return;
        }

        mItemRects.clear();
        mHasAttachedItems.clear();
    }
}
