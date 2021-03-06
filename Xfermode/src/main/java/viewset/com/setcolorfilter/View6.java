package viewset.com.setcolorfilter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * drawBitmap第二个参数
 */
public class View6 extends View {

    private final int width;
    private final int height;
    private final Bitmap dstB;
    Paint paint;
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
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);

        dstB = BitmapFactory.decodeResource(getResources(), R.mipmap.bolang, null);

        width = 100;
        height = 100;
        srcB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    /**
     * 与“示例1、区域波纹”的原因一样，我们需要先画上圆形图，不然就看不出来整体的样式是什么样的。
     * 然后在使用xfermode时，最难的应该是画BmpDST的这句：
     * canvas.drawBitmap(dstB, new Rect(dx, 0, (dx + width) > dstB.getWidth() ? dstB.getWidth() : dx + width, dstB.getHeight()), new RectF(0, 0, width, height), paint);
     * 它的意思就是截取波浪图上new Rect(dx, 0, (dx + width) > dstB.getWidth() ? dstB.getWidth() : dx + width, dstB.getHeight())这个矩形位置，将其画在BmpSRC的位置：new RectF(0, 0, width, height)
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Canvas srcC = new Canvas(srcB);
        srcC.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        srcC.drawCircle(width / 2, height / 2, width / 2, paint);
        canvas.drawBitmap(srcB, 0, 0, paint);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(dstB, new Rect(dx, 0, (dx + width) > dstB.getWidth() ? dstB.getWidth() : dx + width, dstB.getHeight()), new RectF(0, 0, width, height), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(srcB, 0, 0, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, dstB.getWidth());
        animator.setDuration(4000);
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
