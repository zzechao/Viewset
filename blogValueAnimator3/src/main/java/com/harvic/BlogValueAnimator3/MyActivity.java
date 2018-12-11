package com.harvic.BlogValueAnimator3;

import android.animation.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends Activity {
    private TextView tv;
    private Button btnStart, btnCancel;
    private ValueAnimator repeatAnimator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = (TextView) findViewById(R.id.tv);

        btnStart = (Button) findViewById(R.id.btn);
        btnCancel = (Button) findViewById(R.id.btn_cancel);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 一.1 使用插值器
                 */
//                doInterpolatorAnim();

                /**
                 * 一.2 自定义加速器
                 */
//                doMyInterpolatorAnim();
                /**
                 * 二.3.1 简单实现MyEvalutor
                 */
//                doMyEvaluator();
                /**
                 * 二.3.2 实现倒序输出实例
                 */
//                doReverseEvaluator();
                /**
                 * 二.4.1 使用ArgbEvalutor
                 */
                doColorAnimation();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyActivity.this, "clicked me", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 一.1 使用插值器
     */
    private void doInterpolatorAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 600);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer curValue = (Integer) animation.getAnimatedValue();
                tv.layout(tv.getLeft(), curValue, tv.getRight(), curValue + tv.getHeight());
            }
        });
        animator.setDuration(1000);
        animator.setEvaluator(new IntEvaluator());
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    /**
     * 一.2 自定义加速器
     */
    private void doMyInterpolatorAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 600);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer curValue = (Integer) animation.getAnimatedValue();
                tv.layout(tv.getLeft(), curValue, tv.getRight(), curValue + tv.getHeight());
            }
        });
        animator.setDuration(1000);
        animator.setInterpolator(new MyInterploator());
        animator.start();
    }

    /**
     * 二.3.1 简单实现MyEvalutor
     */
    private void doMyEvaluator() {
        ValueAnimator animator = ValueAnimator.ofInt(0, 400);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer curValue = (Integer) animation.getAnimatedValue();
                tv.layout(tv.getLeft(), curValue, tv.getRight(), curValue + tv.getHeight());
            }
        });
        animator.setDuration(1000);
        animator.setEvaluator(new MyEvaluator());
        animator.start();
    }

    /**
     * 二.3.2 实现倒序输出实例
     */
    private void doReverseEvaluator() {
        ValueAnimator animator = ValueAnimator.ofInt(100, 400);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer curValue = (Integer) animation.getAnimatedValue();
                tv.layout(tv.getLeft(), curValue, tv.getRight(), curValue + tv.getHeight());
            }
        });
        animator.setDuration(3000);
        animator.setEvaluator(new ReverseEvaluator());
        animator.start();
    }


    /**
     * 二.4.1 使用ArgbEvalutor
     */
    private void doColorAnimation() {
        ValueAnimator animator = ValueAnimator.ofInt(0xffffff00, 0xff0000ff);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(4000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer curValue = (Integer) animation.getAnimatedValue();
                tv.setBackgroundColor(curValue);

            }
        });
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }

    private ValueAnimator doTestAnimation() {
        //Keyframe是一个时间/值对，用于定义在某个时刻动画的状态。
        // 比如Keyframe.ofInt(.5f, Color.RED)定义了当动画进行了50%的时候，颜色的值应该是Color.RED。
        Keyframe kf0 = Keyframe.ofFloat(0f, 0f);
        Keyframe kf1 = Keyframe.ofFloat(0.1f, 360f);
        Keyframe kf2 = Keyframe.ofFloat(1f, 0f);
        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1, kf2);
        ValueAnimator animator = ValueAnimator.ofPropertyValuesHolder(pvhRotation);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float curValue = (Float) animation.getAnimatedValue("rotation");
                tv.layout((int) curValue.floatValue(), (int) curValue.floatValue(), (int) curValue.floatValue() + tv.getWidth(), (int) curValue.floatValue() + tv.getHeight());
//                Log.d("qijian","curValue: "+curValue);
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d("qijian", "start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("qijian", "end");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d("qijian", "cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d("qijian", "reprat");
            }
        });
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
        return animator;
    }

}
