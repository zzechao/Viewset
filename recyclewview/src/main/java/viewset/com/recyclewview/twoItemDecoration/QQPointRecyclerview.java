package viewset.com.recyclewview.twoItemDecoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import viewset.com.recyclewview.R;
import viewset.com.recyclewview.one.RecyclerAdapter;

public class QQPointRecyclerview extends FrameLayout {

    RecyclerView rv;

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
    private boolean _isOut; // 是否达到消失距离

    // point1和point的0的长度
    private double mWidth;
    // 阻力
    private int resistance;
    // point1的半径
    private final int radiu1;
    // point0的半径
    private int radiu0;
    private Path path = new Path();


    public QQPointRecyclerview(@NonNull Context context) {
        this(context, null);
    }

    public QQPointRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQPointRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_qqpoint_recyclerview, this, true);
        rv = findViewById(R.id.rv);

        qqPointPaint = new Paint();
        qqPointPaint.setColor(Color.parseColor("#ffff4444"));
        qqPointPaint.setAntiAlias(true);
        qqPointPaint.setStyle(Paint.Style.FILL);

        resistance = 20;

        radiu1 = 10;
        radiu0 = radiu1;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        rv.setLayoutManager(layoutManager);

        LinearItemDecoration1 itemDecoration1 = new LinearItemDecoration1(getContext());
        rv.addItemDecoration(itemDecoration1);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        //Log.e("ttt","onInterceptTouchEvent--" + event.getAction());
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //Log.e("ttt", "dispatchTouchEvent--" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float preX = event.getX(event.findPointerIndex(0));
                float preY = event.getY(event.findPointerIndex(0));
                for (int i = 0; i < qqPoints.size(); i++) {
                    Point point = qqPoints.get(qqPoints.keyAt(i));
                    if (point != null) {
                        float x = preX - point.x;
                        float y = preY - point.y;
                        if (Math.abs(x) <= 10 && Math.abs(y) <= 10) {
                            _isTouchIn = true;
                            currentTouchPos = qqPoints.keyAt(i);
                            point0.set(point.x, point.y);
                            //rv.getLayoutManager().get
                            break;
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (_isTouchIn) {
                    return this.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (_isTouchIn) {
                    return this.onTouchEvent(event);
                }
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.e("ttt", "onTouchEvent--" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (_isTouchIn) {
                    point1.set((int) event.getX(event.findPointerIndex(0))
                            , (int) event.getY(event.findPointerIndex(0)));
                    postInvalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (_isTouchIn) {
                    _isTouchIn = false;
                    _isOut = false;
                    point1.set(0, 0);
                    currentTouchPos = -1;
                    postInvalidate();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //Log.e("ttt", "dispatchDraw");
        super.dispatchDraw(canvas);

        if (point1.x != 0 && point1.y != 0) { // 如果point1的xy有值
            resetPoint(); // 重新计算每个点
            if (!_isOut) { // 超出拖拽的最大距离，不用绘制贝塞尔曲线
                //canvas.drawCircle(point2.x, point2.y, 2, paint2);
                //canvas.drawCircle(point3.x, point3.y, 2, paint2);
                //canvas.drawCircle(point4.x, point4.y, 2, paint2);
                //canvas.drawCircle(point5.x, point5.y, 2, paint2);

                path.reset();
                path.moveTo(point2.x, point2.y);
                path.quadTo((point0.x + point1.x) / 2, (point0.y + point1.y) / 2, point4.x, point4.y);
                path.lineTo(point5.x, point5.y);
                path.quadTo((point0.x + point1.x) / 2, (point0.y + point1.y) / 2, point3.x, point3.y);
                path.lineTo(point2.x, point2.y);
                path.close();
                canvas.drawPath(path, qqPointPaint);
            }

            canvas.drawCircle(point1.x, point1.y, radiu1, qqPointPaint);
//            if (!_isAnimStart) {
//                String num = "11";
//                int textWidth = (int) textPaint.measureText(num);
//                canvas.drawText(num, point1.x - textWidth / 2, point1.y + 10 / 2, textPaint);
//            }
        }
    }

    private void resetPoint() {
        double a = Math.atan((point1.y - point0.y) * 1F / (point1.x - point0.x));

        mWidth = Math.abs((point1.y - point0.y) / Math.sin(a));
        radiu0 = (radiu1 - mWidth / resistance) < 4 ? 4 : (int) (radiu1 - mWidth / resistance);
        point2.x = (int) (point0.x + radiu0 * Math.sin(a));
        point2.y = (int) (point0.y - radiu0 * Math.cos(a));

        point3.x = (int) (point0.x - radiu0 * Math.sin(a));
        point3.y = (int) (point0.y + radiu0 * Math.cos(a));

        point4.x = (int) (point1.x + radiu1 * Math.sin(a));
        point4.y = (int) (point1.y - radiu1 * Math.cos(a));

        point5.x = (int) (point1.x - radiu1 * Math.sin(a));
        point5.y = (int) (point1.y + radiu1 * Math.cos(a));
        if ((radiu1 - mWidth / resistance) < 3) {
            _isOut = true;
        }
    }

    class LinearItemDecoration1 extends RecyclerView.ItemDecoration {

        private final Bitmap mMedalBmp;
        Paint paint;

        public LinearItemDecoration1(Context context) {
            paint = new Paint();
            paint.setColor(Color.parseColor("#ff669900"));
            paint.setAntiAlias(true);

            BitmapFactory.Options options = new BitmapFactory.Options();
            // 缩小至原大小的1/2
            options.inSampleSize = 2;
            mMedalBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.jiang, options);
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
            //Log.e("ttt", "--onDrawOver");
            super.onDrawOver(canvas, parent, state);

            int childCount = parent.getChildCount();
            int left;
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                // 根据child获取对应 adapter中的position
                int position = parent.getChildAdapterPosition(child);
                Log.e("ttt", position + "---" + i);
                RecyclerView.LayoutManager manager = parent.getLayoutManager();
                left = manager.getLeftDecorationWidth(child);

                if (parent.getAdapter().getItemViewType(position) == RecyclerAdapter.ITEM_TYPE.ITEM_TYPE_SECTION.ordinal()) {
                    canvas.drawBitmap(mMedalBmp, left + mMedalBmp.getWidth() / 2, child.getTop() + child.getHeight() / 2 - mMedalBmp.getHeight() / 2, paint);
                }

                QQPointRecyclerview.this.drawQQPoint(canvas, parent, position, child);
            }
        }


        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //outRect.top = 1;
            //outRect.left = 100;
        }

    }

    /**
     * 根据position绘制出红点
     *
     * @param canvas
     * @param parent
     * @param position
     * @param child
     */
    private void drawQQPoint(Canvas canvas, RecyclerView parent, int position, View child) {
        //Log.e("ttt", "drawQQPoint");
        if (position % 2 == 0 && parent.getAdapter().getItemViewType(position) == RecyclerAdapter2.ITEM_TYPE.ITEM_TYPE_ITEM.ordinal()) {
            RecyclerAdapter2.NormalHolder normalHolder = (RecyclerAdapter2.NormalHolder) parent.getChildViewHolder(child);
            int cx = child.getLeft() + normalHolder.qqpoint.getLeft() + normalHolder.qqpoint.getWidth() / 2;
            int cy = child.getTop() + normalHolder.qqpoint.getTop() + normalHolder.qqpoint.getHeight() / 2;
//            Log.e("ttt", cy + "----");
//            //Log.e("ttt", "--noset--" + currentTouchPos + "--" + position + "--_isTouchIn--" + _isTouchIn);
//            if (currentTouchPos == position && !_isTouchIn) {
//                return;
//            }
//            //Log.e("ttt", "x0--" + point0.x + "--x--" + cx);
//            //Log.e("ttt", "y0--" + point0.y + "--y--" + cy);
            qqPoints.put(position, new Point(cx, cy));
            //canvas.drawCircle(cx, cy, radiu1, qqPointPaint);
        }
    }
}