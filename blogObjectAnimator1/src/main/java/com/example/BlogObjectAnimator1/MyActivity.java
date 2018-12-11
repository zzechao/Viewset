package com.example.BlogObjectAnimator1;

import android.animation.*;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
    private TextView tv;
    private Button btnStart, btnCancel;
    private MyPointView mPointView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tv = (TextView) findViewById(R.id.tv);

        btnStart = (Button) findViewById(R.id.btn);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        mPointView = (MyPointView) findViewById(R.id.pointview);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 一、概述之Alpha
                 */
//                doAlphaAnimation();
                /**
                 * 一、概述之rotationX
                 */
//                doRatateXAnimation();
                /**
                 * 一、概述之rotationY
                 */
//                doRatateYAnimation();
                /**
                 * 一、概述之rotation
                 */
//                doRotateAnimation();
                /**
                 * 一、概述之translationX
                 */
//                doTranslationXAnimation();
                /**
                 * 一、概述之translationY
                 */
//                doTranslationYAnimation();
                /**
                 * 一、概述之scaleX
                 */
//                doScaleXAnimation();
                /**
                 * 一、概述之scaleY
                 */
//                doScaleYAnimation();

                /**
                 * 二、1 自定义ObjectAnimator属性
                 */
                doPointViewAnimation();
                /**
                 * 二、4 注意——何时需要实现对应属性的get函数
                 */
                doPointViewAnimation2();
                /**
                 * 三.1、使用ArgbEvaluator
                 */
//                doBackgroundColor();

//                doAnimatorSetAnimation();
            }
        });

    }


    /**
     * playSequentially,playTogether(要将不同开始的延时问题),与无限循环一同讲(重复执行)
     * 自由设置顺序:play(alphaAnim).with(radiusAnimator);
     * 讲个人设置与set设置的区别
     * 最后进阶,讲下如何重复进行
     */
    private void doAnimatorSetAnimation() {
        final String tag = "qijian";
        //个人setDuration与animatorSet的关系为animatorSet一但设置,个人的设置将变得无效
        ObjectAnimator radiusAnimator = ObjectAnimator.ofInt(mPointView, "pointRadius", 0, 300, 100);
        radiusAnimator.setInterpolator(new BounceInterpolator());
        radiusAnimator.setDuration(50);
        radiusAnimator.setStartDelay(0);
        radiusAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator bgAnimator = ObjectAnimator.ofInt(mPointView, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        bgAnimator.setEvaluator(new ArgbEvaluator());
        bgAnimator.setStartDelay(10000);
        bgAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(mPointView, "alpha", 1, 0, 1);
        alphaAnim.setInterpolator(new BounceInterpolator());
        alphaAnim.setDuration(2000);
        alphaAnim.setStartDelay(20);

        //设置动画对象的顺序
        AnimatorSet animatorSet = new AnimatorSet();
        //先加速下落然后再执行挤压动画1

        //一起
//        animatorSet.playTogether(alphaAnim,radiusAnimator,bgAnimator);
        //逐个
//        animatorSet.playSequentially(alphaAnim, radiusAnimator, bgAnimator);


        animatorSet.play(alphaAnim).after(radiusAnimator);

        //添加listener
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(tag,"animator start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(tag,"animator end");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(tag,"animator cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(tag,"animator repeat");
            }
        });


        //一时设置了target,那么个人设置的animator对象就全部失效了,所有动画就只会对这个target起作用
//        animatorSet.setTarget(mPointView);
        //个人设置一样失效
//        animatorSet.setInterpolator(new AccelerateInterpolator());
        //如果不设置,每个动画则按每个动画自己的处理方式来处理,如果用playTogether只是同时开始而已,开始以后,每个动画自己处理自己的,互不干扰,如果某个动画设置有多少延迟,开始之后仍有多少延时
//        animatorSet.setStartDelay(1000);

        animatorSet.setDuration(3000);
        animatorSet.start();
//        bouncer.playTogether(bounceAnim,pointAnimator);
        //播放挤压动画1的同事播放挤压动画2
//        bouncer.play(squashAnim1).with(squashAnim2);
//        bouncer.play(squashAnim1).with(stretchAnim1);
//        bouncer.play(squashAnim1).with(stretchAnim2);
        //执行完挤压动画后执行小球弹起动画
//        bouncer.play(bounceBackAnim).after(stretchAnim2);

        AnimatorSet.Builder builder = new AnimatorSet().play(alphaAnim);
    }


    /**
     * 一、概述之Alpha
     */
    private void doAlphaAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "alpha", 1, 0, 1);
        animator.setDuration(2000);
        animator.start();
    }


    /**
     * 一、概述之rotationX
     */
    private void doRatateXAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "rotationX", 0, 180, 0);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 一、概述之rotationY
     */
    private void doRatateYAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "rotationY", 0, 180, 0);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 一、概述之rotation
     */
    private void doRotateAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "rotation", 0, 270, 0);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 一、概述之translationX
     */
    private void doTranslationXAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "translationX", 0, 200, -200, 0);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 一、概述之translationY
     */
    private void doTranslationYAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "translationY", 0, 200, -100, 0);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 一、概述之scaleX
     */
    private void doScaleXAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "scaleX", 0, 3, 1);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 一、概述之scaleY
     */
    private void doScaleYAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofInt(tv, "scaleY", 0, 3, 1);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 二、1 自定义ObjectAnimator属性
     */
    private void doPointViewAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofInt(mPointView, "pointRadius", 0, 300, 100);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 二、4 注意——何时需要实现对应属性的get函数
     */
    private void doPointViewAnimation2() {
        ObjectAnimator animator = ObjectAnimator.ofInt(mPointView, "pointRadius",100);
        animator.setDuration(2000);
        animator.start();
    }

    /**
     * 三.1、使用ArgbEvaluator
     */
    private void doBackgroundColor() {
        ObjectAnimator animator = ObjectAnimator.ofInt(tv, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        animator.setDuration(8000);
        animator.setEvaluator(new ArgbEvaluator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e("qijian",System.currentTimeMillis()+"");
            }
        });
        animator.start();
    }
}
