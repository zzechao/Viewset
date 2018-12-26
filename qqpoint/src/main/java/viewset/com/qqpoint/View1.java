package viewset.com.qqpoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * QQ红点的拖拽效果
 */
public class View1 extends View {


    private final int radiu;
    private final Paint paint2;

    private Paint paint;
    private Path path;
    private boolean isOk;

    private Point point0;
    private Point point1;

    private Point point2;
    private Point point3;

    private Point point4;
    private Point point5;

    public View1(Context context) {
        this(context, null);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        radiu = 10;

        // 第一坐标
        point0 = new Point(50, 50);
        point1 = new Point();

        // point0对应的点
        point2 = new Point();
        point3 = new Point();

        // point1对应的点
        point4 = new Point();
        point5 = new Point();

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        paint2 = new Paint();
        paint2.setColor(Color.BLUE);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);

        path = new Path();
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        canvas.drawCircle(point0.x, point0.y, radiu, paint);

        if (point1.x != 0 && point1.y != 0) {
            path.reset();
            resetPoint();
            canvas.drawCircle(point2.x, point2.y, 2, paint2);
            canvas.drawCircle(point3.x, point3.y, 2, paint2);
            canvas.drawCircle(point1.x, point1.y, radiu, paint);
        }
    }

    /**
     * point2:
     * x2 = x0 + r * sina;
     * y2 = y0 - r * cosa;
     * <p>
     * point3:
     * x3 = x0 - r * sina;
     * y3 = x0 + r * sina;
     */
    private void resetPoint() {
        double a = Math.atan((point1.y - point0.y) * 1F / (point1.x - point0.x));
        point2.x = (int) (point0.x + radiu * Math.sin(a));
        point2.y = (int) (point0.y - radiu * Math.cos(a));

        point3.x = (int) (point0.x - radiu * Math.sin(a));
        point3.y = (int) (point0.y + radiu * Math.cos(a));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX() - point0.x;
                float y = event.getY() - point0.y;
                if (Math.abs(x) <= 10 && Math.abs(y) <= 10) {
                    event.setAction(MotionEvent.ACTION_MOVE);
                    onTouchEvent(event);
                    isOk = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isOk) {
                    point1.set((int) event.getX(), (int) event.getY());
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isOk) {
                    isOk = false;
                    point1.set(0, 0);
                    path.reset();
                    postInvalidate();
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}
