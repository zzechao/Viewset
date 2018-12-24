package viewset.com.setcolorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class View1 extends View {

    private final int width;
    private final int height;
    private final Bitmap dstB;
    Paint paint;
    Bitmap srcB;

    public View1(Context context) {
        this(context, null);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        srcB = BitmapFactory.decodeResource(getResources(), R.mipmap.timg1, null);
        width = 300;
        height = width * srcB.getHeight() / srcB.getWidth();

        dstB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

    }

    /**
     * 圆角图片，重点是
     * Canvas c = new Canvas(dstB); 把bitmap的dstB传进画布重新构图
     * c.drawRoundRect(new RectF(0, 0, width, height), 10, 10, paint);
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int layerID = canvas.saveLayer(0, 0, width, height, paint, Canvas.ALL_SAVE_FLAG);

        Canvas c = new Canvas(dstB);
        c.drawRoundRect(new RectF(0, 0, width, height), 10, 10, paint);

        canvas.drawBitmap(srcB, null, new RectF(0, 0, width, height), paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(dstB, null, new RectF(0, 0, width, height), paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerID);
    }
}
