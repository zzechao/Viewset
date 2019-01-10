package viewset.com.recyclewview.twoItemDecoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LinearItemDecoration1 extends RecyclerView.ItemDecoration {

    Paint paint;

    public LinearItemDecoration1() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            int left = manager.getLeftDecorationWidth(child);
            int top = manager.getTopDecorationHeight(child);
            int right = manager.getRightDecorationWidth(child);
            int bottom = manager.getBottomDecorationHeight(child);

            int cx = left / 2;
            int cy = child.getTop() + child.getHeight() / 2;
            canvas.drawCircle(cx, cy, 20, paint);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = 1;
        outRect.left = 100;
        outRect.right = 50;
    }
}
