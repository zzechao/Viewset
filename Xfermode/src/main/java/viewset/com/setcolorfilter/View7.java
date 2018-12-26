package viewset.com.setcolorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * drawBitmap第二个参数
 */
public class View7 extends View {

    private final int width;
    private final int height;
    private final Bitmap dstB;
    Paint paint;
    Bitmap srcB;
    private int dx;

    public View7(Context context) {
        this(context, null);
    }

    public View7(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View7(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        paint = new Paint();
        paint.setColor(Color.GREEN);
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
        canvas.drawRect(new RectF(0, 0, 500, 500), paint);
        int layerId1 = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        paint.setColor(Color.RED);
        canvas.drawRect(new RectF(100, 100, 400, 400), paint);
        int layerId2 = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        paint.setColor(Color.BLUE);
        canvas.drawRect(new RectF(200, 200, 300, 300), paint);
        canvas.rotate(30);
        int layerId3 = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.clipRect(new RectF(150, 200, 350, 400));
        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(new RectF(100, 100, 300, 400), paint);
        canvas.restoreToCount(layerId2);
        paint.setColor(Color.WHITE);
        canvas.drawRect(new RectF(15, 23, 45, 67), paint);
    }
}
