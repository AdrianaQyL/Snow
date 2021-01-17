package com.example.snow;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;

public class MyAnimView extends View {

    public float RADIUS;

    private Point currentPoint;

    private Paint mPaint;

    private float picHeight, picWidth;

//    private int randomRotate;


    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        RADIUS = (float) Math.random() * 40 + 10; // 随机半径[10, 50]
        RADIUS = (float) (Math.random() * 0.3 + 0.1); // 随机半径[0.1, 0.4]
//        randomRotate = (int)((Math.random() - 0.5) * 30);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
//            currentPoint = new Point(RADIUS, RADIUS);
            currentPoint = new Point(RADIUS, -RADIUS); // 放在左上角屏幕边缘外
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
            if (currentPoint.getY() == getHeight() + picHeight/2) {
//                RectF oval3 = new RectF(currentPoint.getX(), getHeight()-50f, getHeight() + 200, getHeight() + 300);
//                canvas.drawRoundRect(oval3, 20, 15, mPaint);
////                canvas.drawCircle(currentPoint.getX(), getHeight(), 100f, mPaint);
                drawSnow(canvas);
            }
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        // 画圆版本
        //canvas.drawCircle(x, y, RADIUS, mPaint);

        // 画图版本
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake2);
        Matrix matrix = new Matrix();
        matrix.setScale(1f * RADIUS, 1f * RADIUS);
        float h = bitmap.getHeight(), w = bitmap.getWidth();
        matrix.postTranslate(x * ((getWidth() - w * RADIUS)/getWidth()), y - h / 2);
//        matrix.postRotate(randomRotate); // 至多顺时针或逆时针转30度

        picHeight = h;
        picWidth = w;

        canvas.drawBitmap(bitmap, matrix, mPaint); // 此时x为left, y为top
    }

    private void drawSnow(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowflake2);
        float h = bitmap.getHeight() * RADIUS, w = bitmap.getWidth() * RADIUS;

        RectF oval3 = new RectF(x - w/2, getHeight() - h/4, x + w/2, getHeight() + h/4);
        canvas.drawRoundRect(oval3, 40, 40, mPaint);
//                canvas.drawCircle(currentPoint.getX(), getHeight(), 100f, mPaint);

    }

    private void startAnimation() {
        // 固定从中间下落
        //Point startPoint = new Point(getWidth() / 2, RADIUS);
        //Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);

        // 随机位置下落
        float randomStart = (float)(getWidth() * Math.random());
        float randomRange = (float)(getWidth() * (Math.random() - 0.5) / 2);

//        Point startPoint = new Point(randomStart, -2*RADIUS); // 从屏幕上边缘外高出半个球的位置下落（一个RADIUS是半径，一个RADIUS是空隙）
//        Point endPoint = new Point(randomStart + randomRange, getHeight() + RADIUS); // 落到屏幕下边缘外
        Point startPoint = new Point(randomStart, -picHeight); // 从屏幕上边缘外高出一个雪花的位置下落
        Point endPoint = new Point(randomStart + randomRange, getHeight() + picHeight/2); // 落到屏幕下边缘外

        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentPoint = (Point) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.setInterpolator(new DropInterpolator());
        anim.setDuration(5000);
        long delay = (long) (Math.random()*50000);
        anim.setStartDelay(delay);
        Log.d("TAG", "delay =" + delay);
        anim.start();

//        anim.setRepeatCount(20);
//        anim.setRepeatMode(ValueAnimator.RESTART);


//        anim.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
////                super.onAnimationEnd(animation);
//                Log.d("TAG", "onAnimationEnd");
//            }
//        });
    }
}
