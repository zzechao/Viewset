package viewset.com.drawpath;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {
    private Paint paint;

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
     * canvas中绘制路径利用：
     * void drawPath (Path path, Paint paint)
     * <p>
     * 1、直线路径
     * void moveTo (float x1, float y1):直线的开始点；即将直线路径的绘制点定在（x1,y1）的位置；
     * void lineTo (float x2, float y2)：直线的结束点，又是下一次绘制直线路径的开始点；lineTo（）可以一直用；
     * void close ():如果连续画了几条直线，但没有形成闭环，调用Close()会将路径首尾点连接起来，形成闭环；
     * <p>
     * 2、矩形路径
     * void addRect (float left, float top, float right, float bottom, Path.Direction dir)
     * void addRect (RectF rect, Path.Direction dir)
     * 这里Path类创建矩形路径的参数与上篇canvas绘制矩形差不多，唯一不同的一点是增加了Path.Direction参数；
     * Path.Direction有两个值：
     * Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
     * Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；
     * <p>
     * 3、圆角矩形路径
     * void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
     * void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
     * 这里有两个构造函数，部分参数说明如下：
     * 第一个构造函数：可以定制每个角的圆角大小：
     * float[] radii：必须传入8个数值，分四组，分别对应每个角所使用的椭圆的横轴半径和纵轴半径，如｛x1,y1,x2,y2,x3,y3,x4,y4｝，其中，x1,y1对应第一个角的（左上角）用来产生圆角的椭圆的横轴半径和纵轴半径，其它类推……
     * 第二个构造函数：只能构建统一圆角大小
     * float rx：所产生圆角的椭圆的横轴半径；
     * float ry：所产生圆角的椭圆的纵轴半径；
     * <p>
     * （2）、指定个个文字位置
     * void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
     * void drawPosText (String text, float[] pos, Paint paint)
     * 说明：
     * 第一个构造函数：实现截取一部分文字绘制；
     * 参数说明：
     * char[] text：要绘制的文字数组
     * int index:：第一个要绘制的文字的索引
     * int count：要绘制的文字的个数，用来算最后一个文字的位置，从第一个绘制的文字开始算起
     * float[] pos：每个字体的位置，同样两两一组，如｛x1,y1,x2,y2,x3,y3……｝
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        //画线
        paintPathLine(canvas);

        //绘制矩形
        paintPathRectf(canvas);
        drawTextOnPath(canvas);

        //指定位置写字
        drawPosText(canvas);
    }

    private void paintPathLine(Canvas canvas) {
        Path path = new Path();

        path.moveTo(10, 10); //设定起始点
        path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
        path.lineTo(300, 100);//画第二条直线
        path.lineTo(500, 100);//第三条直线
        path.close();//闭环

        canvas.drawPath(path, paint);
    }

    private void paintPathRectf(Canvas canvas) {
        //先创建两个大小一样的路径
        //第一个逆向生成
        Path CCWRectpath = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);

        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 = new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);

        //先画出这两个路径
        canvas.drawPath(CCWRectpath, paint);
        canvas.drawPath(CWRectpath, paint);
    }

    private void drawTextOnPath(Canvas canvas) {
        //先创建两个大小一样的路径
        //第一个逆向生成
        Path CCWRectpath = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);

        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 = new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);

        //先画出这两个路径
        canvas.drawPath(CCWRectpath, paint);
        canvas.drawPath(CWRectpath, paint);

        //依据路径写出文字
        String text = "风萧萧兮易水寒，壮士一去兮不复返";
        paint.setColor(Color.GRAY);
        paint.setTextSize(35);
        canvas.drawTextOnPath(text, CCWRectpath, 0, 18, paint);//逆时针生成
        canvas.drawTextOnPath(text, CWRectpath, 0, 18, paint);//顺时针生成
    }

    private void drawPosText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(80);//设置文字大小
        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充

        float[] pos = new float[]{80, 100,
                80, 200,
                80, 300,
                80, 400};
        canvas.drawPosText("画图示例", pos, paint);//两个构造函数
    }
}
