package viewset.com.shader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class View2 extends View {
    private Bitmap bitmap;
    private Paint paint;
    private int mWidth;
    private int mHeight;
    private Paint paintT;
    private BitmapShader shader;
    private int min;

    public View2(Context context) {
        this(context, null);
    }

    public View2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.li);
        paint = new Paint();
        paint.setAntiAlias(true);

        paintT = new Paint();
        paintT.setColor(Color.WHITE);
        paintT.setStrokeWidth(1);

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
            result = mWidth + mHeight * 7; //如果是wrap_content ,给个初始值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, null, new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), paint);

        int first = canvas.saveLayer(0, 0, getWidth(), bitmap.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        canvas.translate(bitmap.getWidth(), 0);

        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float scale = min * 1f / bitmap.getWidth();
        Matrix matrix = new Matrix();
        //matrix.setScale(scale, scale);
        shader.getLocalMatrix(matrix);
        paint.setShader(shader);
        canvas.drawBitmap(bitmap, null, new RectF(0, (min - (bitmap.getHeight() * scale)) / 2, min, bitmap.getHeight() * scale + (min - (bitmap.getHeight() * scale)) / 2), paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(min, 0);

        shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        float m = (bitmap.getWidth() - min) / 2f;
        paint.setShader(shader);
        canvas.drawBitmap(bitmap, new Rect((int) m, 0, (int) m + min, bitmap.getHeight()), new RectF(0, 0, min, min), paint);

        canvas.drawLine(0, 0, 0, bitmap.getHeight(), paintT);
        canvas.translate(min, 0);
    }
}
