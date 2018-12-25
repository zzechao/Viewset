package viewset.com.setcolorfilter;

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
import android.view.MotionEvent;
import android.view.View;

public class View3 extends View {

    private final int width;
    private final int height;
    private final Bitmap dstB;
    private final Path path;
    Paint paint;
    Bitmap srcB;
    private float currentX;
    private float currentY;
    private boolean is_init;

    public View3(Context context) {
        this(context, null);
    }

    public View3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(45);

        srcB = BitmapFactory.decodeResource(getResources(), R.mipmap.timg1, null);
        width = 350;
        height = width * srcB.getHeight() / srcB.getWidth();

        dstB = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

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
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(srcB, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawPath(path, paint);
        paint.setXfermode(null);

        canvas.restoreToCount(layerID);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                currentX = event.getX();
                currentY = event.getY();
                path.moveTo(currentX, currentY);
                event.setAction(MotionEvent.ACTION_MOVE);
                event.setLocation(currentX + 1, currentY + 1);
                onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = (currentX + event.getX()) / 2;
                float endY = (currentY + event.getY()) / 2;
                path.quadTo(currentX, currentY, endX, endY);
                currentX = event.getX();
                currentY = event.getY();
                postInvalidate();
                break;
        }

        return super.onTouchEvent(event);
    }
}
