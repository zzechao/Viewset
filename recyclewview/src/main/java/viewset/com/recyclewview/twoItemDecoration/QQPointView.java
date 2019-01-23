package viewset.com.recyclewview.twoItemDecoration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * QQ红点的拖拽效果
 */
public class QQPointView extends View {

    // point1的半径
    private final int radiu1;
    // point0的半径
    private int radiu0;

    private final Paint paint;
    private final Paint paint2;
    private final Paint textPaint;
    private final Path path;

    //
    private Point point0;
    private Point point1;

    private Point point2;
    private Point point3;

    private Point point4;
    private Point point5;

    // point1和point的0的长度
    private double mWidth;
    // 阻力
    private int resistance;

    private boolean _isAnimStart; // 回弹是否开始
    private boolean _isTouchIn; // 接触点是否是point0
    private boolean _isOut; // 红点消失

    public QQPointView(Context context) {
        this(context, null);
    }

    public QQPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.TRANSPARENT);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        resistance = 20;

        radiu1 = 10;
        radiu0 = radiu1;

        // 第一坐标
        point0 = new Point();
        point1 = new Point();

        // point0对应的点,计算Path的quadTo用
        point2 = new Point();
        point3 = new Point();

        // point1对应的点,计算Path的quadTo用
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
        textPaint.setTextSize(14);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        textPaint.setTypeface(font);

        path = new Path();
    }

    public void setPoint0(int x, int y) {
        point0.set(x, y);
        invalidate();
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (point1.x != 0 && point1.y != 0) { // 如果point1的xy有值
            resetPoint(); // 重新计算每个点
            if (!_isOut) { // 超出拖拽的最大距离，不用绘制贝塞尔曲线
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
            if (!_isAnimStart) {
                String num = "11";
                int textWidth = (int) textPaint.measureText(num);
                canvas.drawText(num, point1.x - textWidth / 2, point1.y + 10 / 2, textPaint);
            }
        }

        if (!_isOut) {
            canvas.drawCircle(point0.x, point0.y, radiu0, paint);
        }
        if (!_isTouchIn && !_isAnimStart) {
            String num = "11";
            int textWidth = (int) textPaint.measureText(num);
            canvas.drawText(num, point0.x - textWidth / 2, point0.y + 10 / 2, textPaint);
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
     * 重新计算每个点
     */
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ttt", "QQPointView--onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX() - point0.x;
                float y = event.getY() - point0.y;
                if (Math.abs(x) <= 10 && Math.abs(y) <= 10) {
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
                if (!_isOut) {
                    startResetAnim();
                } else {
                    point1.set(0, 0);
                    postInvalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void startResetAnim() {
        _isAnimStart = true;
        ValueAnimator animatorX = ValueAnimator.ofInt(point1.x, point0.x);
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                point1.x = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        ValueAnimator animatorY = ValueAnimator.ofInt(point1.y, point0.y);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                point1.y = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        animatorSet.setInterpolator(new OvershootInterpolator());
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset();
            }
        });
    }

    public void reset() {
        _isTouchIn = false;
        _isOut = false;
        _isAnimStart = false;
        mWidth = 0;
        radiu0 = radiu1;
        point1.set(0, 0);
        path.reset();
        postInvalidate();
    }
}
