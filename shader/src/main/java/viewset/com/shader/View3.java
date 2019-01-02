package viewset.com.shader;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class View3 extends View {
    private Paint paint;

    private RadialGradient radialGradient;
    private float mCurrentX;
    private float mCurrentY;
    private AnimatorSet animSet;
    private int radiu;

    public View3(Context context) {
        this(context, null);
    }

    public View3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public View3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
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
            result = 50; //如果是wrap_content ,给个初始值
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
            result = 100; //如果是wrap_content ,给个初始值
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);

        if (radiu > 0) {
            radialGradient = new RadialGradient(mCurrentX, mCurrentY, radiu, Color.parseColor("#0000FFFF"), Color.parseColor("#00FFFF"), Shader.TileMode.CLAMP);
            paint.setShader(radialGradient);
        }
        canvas.drawCircle(mCurrentX, mCurrentY, radiu, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                radiu = 10;
                mCurrentX = event.getX();
                mCurrentY = event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                radiu = 10;
                mCurrentX = event.getX();
                mCurrentY = event.getY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (animSet == null) {
                    ValueAnimator animator = ValueAnimator.ofInt(10, getWidth());
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            radiu = (int) animation.getAnimatedValue();
                            postInvalidate();
                        }
                    });
                    ValueAnimator aph = ValueAnimator.ofInt(255, 0);
                    aph.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            paint.setAlpha((Integer) animation.getAnimatedValue());
                            postInvalidate();
                        }
                    });
                    animSet = new AnimatorSet();
                    animSet.playTogether(animator, aph);
                } else if (animSet.isRunning()) {
                    animSet.cancel();
                }

                animSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        radiu = 0;
                        paint.reset();
                        paint.setAlpha(255);
                        postInvalidate();
                    }
                });
                animSet.setDuration(800);
                animSet.start();
                break;
        }
        return super.onTouchEvent(event);
    }
}
