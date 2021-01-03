package com.example.snow;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private androidx.constraintlayout.widget.ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayout = (androidx.constraintlayout.widget.ConstraintLayout) findViewById(R.id.parent_layout);

        LayoutInflater inflater = LayoutInflater.from(this);

//        View ballView = inflater.inflate(R.layout.drop, null);
//        mLayout.addView(ballView);

        for (int i = 0; i < 100; i++) {
            mLayout.addView(inflater.inflate(R.layout.drop, null));
        }
    }
}