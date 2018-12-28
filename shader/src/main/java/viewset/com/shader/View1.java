package viewset.com.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class View1 extends View {
    private Bitmap bitmap;
    private Paint paint;
    private int mWidth;
    private int mHeight;
    private Paint paintT;
    private Path path;
    private BitmapShader shader;
    private Matrix matrix;
    private int a;

    public View1(Context context) {
        this(context, null);
    }

    public View1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.touxiang);

        path = new Path();

        paintT = new Paint();
        paintT.setColor(Color.WHITE);
        paintT.setStrokeWidth(1);

        paint = new Paint();
        paint.setAntiAlias(true);
        mWidth = bitmap.getWidth();
        mHeight = bitmap.getHeight();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    //设置高的大小
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        //获取模式和大小
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mHeight; //如果是wrap_content ,给个初始值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    //设置宽的大小
    private int measureWidth(int widthMeasureSpec) {
        // TODO Auto-generated method stub
        int result = 0;
        //获取模式和大小
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = mWidth * 8; //如果是wrap_content ,给个初始值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLUE);

        // 原图
        canvas.drawBitmap(bitmap, null, new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);

        int first = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(bitmap.getWidth(), 0);


        // 图像1
        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, 50, paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(bitmap.getWidth(), 0);

        // 图像2
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        a = Math.min(bitmap.getHeight(), bitmap.getWidth());
        matrix = new Matrix();
        matrix.setScale(80f / a, 80f / a, a / 2, a / 2);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        canvas.drawRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(bitmap.getWidth(), 0);

        // 图像4
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        matrix = new Matrix();
        matrix.setScale(80f / a, 80f / a, a / 2, a / 2);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        path.reset();
        path.addCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, 40f, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(bitmap.getWidth(), 0);

        // 图像5
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        matrix = new Matrix();
        matrix.setScale(80f / a, 80f / a, a / 2, a / 2);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        path.reset();
        path.addRoundRect(new RectF((a - 80f) / 2, (a - 80f) / 2, (a - 80f) / 2 + 80f, (a - 80f) / 2 + 80f), 15, 15, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(bitmap.getWidth(), 0);

        // 图像6
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        matrix = new Matrix();
        matrix.setScale(80f / a, 80f / a, a / 2, a / 2);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        path.reset();
        path.moveTo((a - 80) / 2, (a - 80) / 2 + 20);
        path.rQuadTo(40, -20, 80, 0);
        path.lineTo((a - 80) / 2 + 80, (a - 80) / 2 + 60);
        path.rQuadTo(-40, 20, -80, 0);
        path.lineTo((a - 80) / 2, (a - 80) / 2);
        path.close();
        canvas.drawPath(path, paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(bitmap.getWidth(), 0);

        // 图像7
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        matrix = new Matrix();
        matrix.setScale(80f / a, 80f / a, a / 2, a / 2);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        path.reset();
        path.moveTo((a - 80) / 2, (a - 80) / 2);
        path.rQuadTo(40, 20, 80, 0);
        path.lineTo((a - 80) / 2 + 80, (a - 80) / 2 + 80);
        path.rQuadTo(-40, -20, -80, 0);
        path.lineTo((a - 80) / 2, (a - 80) / 2);
        path.close();
        canvas.drawPath(path, paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(bitmap.getWidth(), 0);

        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        matrix = new Matrix();
        matrix.setScale(80f / a, 80f / a, a / 2, a / 2);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
        path.reset();
        path.moveTo(a / 2, a / 2 - 30);
        path.rCubicTo(30, -10, 80, 0, 0, 60);
        path.rCubicTo(-60, -60, -30, -80, 0, -60);
        path.close();
        canvas.drawPath(path, paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
    }
}
