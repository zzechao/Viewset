package viewset.com.setcolorfilter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class View6 extends View {

    private final int width;
    private final int height;
    private final Bitmap dstB;
    Paint paint;
    Paint paintText;
    Bitmap srcB;
    private int dx;

    public View6(Context context) {
        this(context, null);
    }

    public View6(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View6(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);

        dstB = BitmapFactory.decodeResource(getResources(), R.mipmap.bolang, null);

        width = 500;
        height = 500;
        srcB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    /**
     * 圆角图片，重点是
     * Canvas c = new Canvas(dstB); 把bitmap的dstB传进画布重新构图
     * c.drawPath(path, paint);
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.restoreToCount(layerId);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, dstB.getWidth());
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
