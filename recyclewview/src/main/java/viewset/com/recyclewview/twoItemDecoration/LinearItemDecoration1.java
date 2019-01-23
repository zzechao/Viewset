package viewset.com.recyclewview.twoItemDecoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import viewset.com.recyclewview.R;
import viewset.com.recyclewview.one.RecyclerAdapter;

public class LinearItemDecoration1 extends RecyclerView.ItemDecoration {

    private final Bitmap mMedalBmp;
    Paint paint;
    Paint qqPointPaint;

    public LinearItemDecoration1(Context context) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#ff669900"));
        paint.setAntiAlias(true);

        BitmapFactory.Options options = new BitmapFactory.Options();
        // 缩小至原大小的1/2
        options.inSampleSize = 2;
        mMedalBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.jiang, options);

        qqPointPaint = new Paint();
        qqPointPaint.setColor(Color.parseColor("#ffff4444"));
        qqPointPaint.setAntiAlias(true);
        qqPointPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(canvas, parent, state);

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            int left = manager.getLeftDecorationWidth(child);

            int cx = left / 2;
            int cy = child.getTop() + child.getHeight() / 2;
            canvas.drawCircle(cx, cy, 20, paint);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        Log.e("ttt", "--onDrawOver");
        super.onDrawOver(canvas, parent, state);

        int childCount = parent.getChildCount();
        int left;
        boolean ok = true;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            // 根据child获取对应 adapter中的position
            int position = parent.getChildAdapterPosition(child);
            RecyclerView.LayoutManager manager = parent.getLayoutManager();
            left = manager.getLeftDecorationWidth(child);

            if (parent.getAdapter().getItemViewType(position) == RecyclerAdapter.ITEM_TYPE.ITEM_TYPE_SECTION.ordinal()) {
                canvas.drawBitmap(mMedalBmp, left - mMedalBmp.getWidth() / 2, child.getTop() + child.getHeight() / 2 - mMedalBmp.getHeight() / 2, paint);
            }
        }

//        if (_isTouchIn) {
//            canvas.drawCircle(point1.x, point1.y, 10, qqPointPaint);
//        }
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = 1;
        outRect.left = 100;
    }
}
