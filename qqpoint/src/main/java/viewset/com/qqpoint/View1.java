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


    private int radiu1;
    private int radiu0;
    private final Paint paint2;

    private Paint paint;
    private Paint textPaint;
    private Path path;

    private Point point0;
    private Point point1;

    private Point point2;
    private Point point3;

    private Point point4;
    private Point point5;

    private double mWidth;
    // 阻力
    private int resistance;

    private boolean _isTouchIn;
    private boolean _isTouchOut;

    public View1(Context context) {
        this(context, null);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        resistance = 20;

        radiu1 = 10;
        radiu0 = radiu1;

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

        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(16);

        path = new Path();
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (point1.x != 0 && point1.y != 0) {
            resetPoint();
            if (!_isTouchOut) {
                canvas.drawCircle(point2.x, point2.y, 2, paint2);
                canvas.drawCircle(point3.x, point3.y, 2, paint2);
                canvas.drawCircle(point4.x, point4.y, 2, paint2);
                canvas.drawCircle(point5.x, point5.y, 2, paint2);

                path.reset();
                path.moveTo(point2.x, point2.y);
                path.quadTo((point0.x + point1.x) / 2, (point0.y + point1.y) / 2, point4.x, point4.y);
                path.lineTo(point5.x, point5.y);
                path.quadTo((point0.x + point1.x) / 2, (point0.y + point1.y) / 2, point3.x, point3.y);
                path.lineTo(point2.x, point2.y);
                path.close();
                canvas.drawPath(path, paint);
            }

            canvas.drawCircle(point1.x, point1.y, radiu1, paint);
            String num = "11";
            int textWidth = (int) textPaint.measureText(num);
            canvas.drawText(num, point1.x - textWidth / 2, point1.y + 12 / 2, textPaint);
        }

        if (!_isTouchOut) {
            canvas.drawCircle(point0.x, point0.y, radiu0, paint);
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
     * <p>
     * point4:
     * x4 = x1 + r * sina;
     * y4 = y1 - r * sina;
     * <p>
     * point5:
     * x5 = x1 - r * sina;
     * y5 = y1 + r * sina;
     * 重新绘制点
     */
    private void resetPoint() {
        double a = Math.atan((point1.y - point0.y) * 1F / (point1.x - point0.x));

        mWidth = Math.abs((point1.y - point0.y) / Math.sin(a));
        radiu0 = (radiu1 - mWidth / resistance) < 5 ? 5 : (int) (radiu1 - mWidth / resistance);
        point2.x = (int) (point0.x + radiu0 * Math.sin(a));
        point2.y = (int) (point0.y - radiu0 * Math.cos(a));

        point3.x = (int) (point0.x - radiu0 * Math.sin(a));
        point3.y = (int) (point0.y + radiu0 * Math.cos(a));

        point4.x = (int) (point1.x + radiu1 * Math.sin(a));
        point4.y = (int) (point1.y - radiu1 * Math.cos(a));

        point5.x = (int) (point1.x - radiu1 * Math.sin(a));
        point5.y = (int) (point1.y + radiu1 * Math.cos(a));
        if ((radiu1 - mWidth / resistance) < 3) {
            _isTouchOut = true;
        }
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
                    _isTouchIn = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (_isTouchIn) {
                    point1.set((int) event.getX(), (int) event.getY());
                    postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (_isTouchIn) {
                    _isTouchIn = false;
                    _isTouchOut = false;
                    mWidth = 0;
                    radiu0 = radiu1;
                    point1.set(0, 0);
                    path.reset();
                    postInvalidate();
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}
