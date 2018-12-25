package viewset.com.setcolorfilter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class View5 extends View {

    private final int width;
    private final int height;
    private final Bitmap dstB;
    private final Path path;
    private final int waveLen;
    private final Bitmap textB;
    Paint paint;
    Paint paintText;
    Bitmap srcB;
    private int dx;

    public View5(Context context) {
        this(context, null);
    }

    public View5(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);


        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setAntiAlias(true);
        paintText.setTextSize(36);

        srcB = BitmapFactory.decodeResource(getResources(), R.mipmap.timg1, null);
        width = 350;
        height = width * srcB.getHeight() / srcB.getWidth();

        waveLen = width / 3;

        dstB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        textB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        path = new Path();
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

        /**
         * 画布1画波浪
         */
        Canvas c = new Canvas(dstB);
        path.reset();
        path.moveTo(-waveLen + dx, height / 2);
        float halfWaveLen = waveLen / 2;
        for (int i = -waveLen; i <= width + waveLen; i += waveLen) {
            path.rQuadTo(halfWaveLen / 2, -10, halfWaveLen, 0);
            path.rQuadTo(halfWaveLen / 2, 10, halfWaveLen, 0);
        }

        path.lineTo(width, height);
        path.lineTo(0, height);
        path.close();
        c.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
        c.drawPath(path, paint);

        /**
         * 画布2画文字
         */
        Canvas textC = new Canvas(textB);
        String str = "测试测试";
        float textWidth = paintText.measureText(str);
        float textLeft = (width - textWidth) / 2;
        textC.drawColor(Color.TRANSPARENT);
        textC.drawText(str, textLeft, (height + 36) / 2, paintText);

        canvas.drawColor(Color.BLACK);

        /**
         * h
         */
        canvas.drawBitmap(textB, 0, 0, paint);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dstB, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(textB, 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, waveLen);
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
