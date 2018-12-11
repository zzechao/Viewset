package com.harvic.BlogValueAnimator3;

import android.animation.TypeEvaluator;

/**
 * Created by qijian on 16/1/13.
 */
public class ReverseEvaluator implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (endValue - fraction * (endValue - startInt));
    }
}
