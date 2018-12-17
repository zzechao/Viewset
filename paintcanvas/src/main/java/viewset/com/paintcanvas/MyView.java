package viewset.com.paintcanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
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
     * <p>
     * 5、矩形工具类RectF与Rect
     * 这两个都是矩形辅助类，区别不大，用哪个都行，根据四个点构建一个矩形结构；在画图时，利用这个矩形结构可以画出对应的矩形或者与其它图形Region相交、相加等等；
     * -RectF：
     * 构造函数有下面四个，但最常用的还是第二个，根据四个点构造出一个矩形；
     * RectF()
     * RectF(float left, float top, float right, float bottom)
     * RectF(RectF r)
     * RectF(Rect r)
     * -Rect
     * 构造函数如下，最常用的也是根据四个点来构造矩形
     * Rect()
     * Rect(int left, int top, int right, int bottom)
     * Rect(Rect r)
     * <p>
     * 7、圆角矩形
     * void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
     * <p>
     * 8、圆形
     * void drawCircle (float cx, float cy, float radius, Paint paint)
     * <p>
     * 9、椭圆
     * 椭圆是根据矩形生成的，以矩形的长为椭圆的X轴，矩形的宽为椭圆的Y轴，建立的椭圆图形
     * void drawOval (RectF oval, Paint paint)
     * 参数：
     * RectF oval：用来生成椭圆的矩形
     * <p>
     * 10、弧
     * 弧是椭圆的一部分，而椭圆是根据矩形来生成的，所以弧当然也是根据矩形来生成的；
     * void drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
     * 参数：
     * RectF oval:生成椭圆的矩形
     * float startAngle：弧开始的角度，以X轴正方向为0度
     * float sweepAngle：弧持续的角度
     * boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
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

        //画正方形
        paintRect(canvas);

        //圆角矩形
        paintRoundRect(canvas);

        //画圆
        paintTCircle(canvas);

        //画椭圆
        paintOval(canvas);

        //画弧
        paintArc(canvas);
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

    private void paintRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        canvas.drawRect(10, 10, 100, 100, paint);//直接构造

        RectF rect = new RectF(120, 10, 210, 100);
        canvas.drawRect(rect, paint);//使用RectF构造

        Rect rect2 = new Rect(230, 10, 320, 100);
        canvas.drawRect(rect2, paint);//使用Rect构造
    }

    private void paintRoundRect(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        RectF rect = new RectF(100, 10, 300, 100);
        canvas.drawRoundRect(rect, 20, 10, paint);
    }

    private void paintTCircle(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度
        canvas.drawCircle(150, 150, 100, paint);
    }

    private void paintOval(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        RectF rect = new RectF(100, 10, 300, 100);
        canvas.drawRect(rect, paint);//画矩形

        paint.setColor(Color.GREEN);//更改画笔颜色
        canvas.drawOval(rect, paint);//同一个矩形画椭圆
    }

    private void paintArc(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        RectF rect1 = new RectF(100, 10, 300, 100);
        canvas.drawArc(rect1, 0, 90, true, paint);

        RectF rect2 = new RectF(400, 10, 600, 100);
        canvas.drawArc(rect2, 0, 90, false, paint);
    }
}
