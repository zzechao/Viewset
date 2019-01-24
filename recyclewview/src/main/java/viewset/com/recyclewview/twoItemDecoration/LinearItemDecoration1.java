package viewset.com.recyclewview.twoItemDecoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import viewset.com.recyclewview.R;
import viewset.com.recyclewview.one.RecyclerAdapter;

public class LinearItemDecoration1 extends RecyclerView.ItemDecoration implements View.OnTouchListener {

    private final Bitmap mMedalBmp;
    Paint paint;
    Paint qqPointPaint;

    SparseArray<Point> qqPoints = new SparseArray<>();

    private int currentTouchPos = -1;
    private Point point0 = new Point();
    private Point point1 = new Point();

    private Point point2 = new Point();
    private Point point3 = new Point();

    private Point point4 = new Point();
    private Point point5 = new Point();

    private boolean _isTouchIn; // 接触点是否是point0

    private OnPointInListener onPointInListener;

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

            if (position % 5 == 0) {
                int cx = child.getLeft() + child.getWidth() - 40;
                int cy = child.getTop() + child.getHeight() / 2;
                Log.e("ttt", "--noset--" + currentTouchPos + "--" + position + "--_isTouchIn--" + _isTouchIn);
                if (currentTouchPos == position && !_isTouchIn) {

                    continue;
                }
                Log.e("ttt", "x0--" + point0.x + "--x--" + cx);
                Log.e("ttt", "y0--" + point0.y + "--y--" + cy);
                qqPoints.put(position, new Point(cx, cy));
                canvas.drawCircle(cx, cy, 10, qqPointPaint);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
//            case MotionEvent.ACTION_MOVE:
//                if (_isTouchIn) {
//                    point1.set((int) event.getX(event.findPointerIndex(0))
//                            , (int) event.getY(event.findPointerIndex(0)));
//                    v.postInvalidate();
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                _isTouchIn = false;
//                point1.set(0, 0);
//                v.postInvalidate();
//                break;
        }
        return false;
    }

    public void setOnPointInListener(OnPointInListener onPointInListener) {
        this.onPointInListener = onPointInListener;
    }

    public interface OnPointInListener {
        void onPointIn(Point point);
    }
}
