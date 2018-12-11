package com.harvic.BlogValueAnimator3;

import android.animation.TypeEvaluator;

/**
 * Created by qijian on 16/1/13.
 */
public class MyEvaluator implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int)(200+startInt + fraction * (endValue - startInt));
    }
}
