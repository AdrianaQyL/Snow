package com.example.snow;

import android.animation.TimeInterpolator;

public class DropInterpolator implements TimeInterpolator {
    @Override
    public float getInterpolation(float v) {
        float result;
        result = (float) Math.pow(v, 1.25);
        return result;
    };
}
