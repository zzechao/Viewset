package viewset.com.canvascontrol;

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

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        paintSaveAndRestore(canvas);

        paintTranslate(canvas);
    }

    /**
     * 1、每次调用canvas.drawXXXX系列函数来绘图进，都会产生一个全新的Canvas画布。
     * 2、如果在DrawXXX前，调用平移、旋转等函数来对Canvas进行了操作，那么这个操作是不可逆的！每次产生的画布的最新位置都是这些操作后的位置。（关于Save()、Restore()的画布可逆问题的后面再讲）
     * 3、在Canvas与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
     *
     * @param canvas
     */
    private void paintTranslate(Canvas canvas) {
        //构造两个画笔，一个红色，一个绿色
        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3);
        Paint paint_red = generatePaint(Color.RED, Paint.Style.STROKE, 3);

        //构造一个矩形
        Rect rect1 = new Rect(0, 0, 150, 150);

        //在平移画布前用绿色画下边框
        canvas.drawRect(rect1, paint_green);

        //平移画布后,再用红色边框重新画下这个矩形
        canvas.translate(100, 100);
        canvas.drawRect(rect1, paint_red);
    }

    private void paintSaveAndRestore(Canvas canvas) {
        canvas.drawColor(Color.RED);

        //保存当前画布大小即整屏
        canvas.save();

        canvas.clipRect(new Rect(50, 50, 250, 250));
        canvas.drawColor(Color.GREEN);

        canvas.clipRect(new Rect(60, 50, 250, 250));
        canvas.drawColor(Color.RED);

        //恢复整屏画布
        canvas.restore();

        canvas.drawColor(Color.BLUE);
    }

    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

}
