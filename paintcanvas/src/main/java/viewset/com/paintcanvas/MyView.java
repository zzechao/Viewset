package viewset.com.paintcanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 1、void setStyle (Paint.Style style)     设置填充样式
     * Paint.Style.FILL    :填充内部
     * Paint.Style.FILL_AND_STROKE  ：填充内部和描边
     * Paint.Style.STROKE  ：仅描边
     * <p>
     * <p>
     * 2、setShadowLayer (float radius, float dx, float dy, int color)    添加阴影
     * 参数：
     * radius:阴影的倾斜度
     * dx:水平位移
     * dy:垂直位移
     * <p>
     * <p>
     * 2、多条直线
     * void drawLines (float[] pts, Paint paint)
     * void drawLines (float[] pts, int offset, int count, Paint paint)
     * <p>
     * <p>
     * 3、点
     * void drawPoint (float x, float y, Paint paint)
     * <p>
     * <p>
     * 4、多个点
     * void drawPoints (float[] pts, Paint paint)
     * void drawPoints (float[] pts, int offset, int count, Paint paint)
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆
        paintCircle(canvas);

        //画线
        paintLine(canvas);

        //画多条线
        paintLines(canvas);

        //画点
        paintPoint(canvas);

        //画点
        paintPoints(canvas);
    }

    private void paintCircle(Canvas canvas) {
        //设置画笔基本属性
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影

        //设置画布背景颜色
        canvas.drawRGB(255, 255, 255);

        //画圆
        canvas.drawCircle(190, 200, 150, paint);
    }

    private void paintLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(5);//设置画笔宽度

        canvas.drawLine(100, 100, 200, 200, paint);
    }

    private void paintLines(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(5);//设置画笔宽度

        float[] pts = {10, 10, 100, 100, 200, 200, 400, 400};
        canvas.drawLines(pts, paint);
    }

    private void paintPoint(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度
        canvas.drawPoint(100, 100, paint);
    }

    private void paintPoints(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度
        float[] pts = {10, 10, 100, 100, 200, 200, 400, 400};
        canvas.drawPoints(pts, 2, 4, paint);
    }
}
