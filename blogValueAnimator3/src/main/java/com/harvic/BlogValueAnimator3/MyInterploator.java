package com.harvic.BlogValueAnimator3;

import android.animation.TimeInterpolator;

/**
 * Created by qijian on 16/1/11.
 */
public class MyInterploator implements TimeInterpolator {
    @Override
    public float getInterpolation(float input) {
        return 1-input;
    }
}
