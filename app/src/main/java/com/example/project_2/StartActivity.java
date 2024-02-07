package com.example.project_2;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button start = (Button) findViewById(R.id.start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,SelectPlaces.class);
                startActivity(intent);
            }
        });

        ImageView imageView = findViewById(R.id.imageView2);

        imageView.post(new Runnable() {
            @Override
            public void run() {
                startVerticalMovement(imageView, imageView.getHeight());
            }
        });
    }

    private float startY = 0f; // 成员变量
    private float endY = 0f;

    private void startVerticalMovement(ImageView imageView, int imageHeight) {
        startY = (float) (0.5*imageHeight);
        endY = (float) (imageHeight);

        // 创建新的属性动画对象，设置要修改的属性为translationY，并应用起始和结束位置
        ObjectAnimator newAnimY = ObjectAnimator.ofFloat(imageView, "translationY", startY, endY);
        newAnimY.setDuration(3500); // 设置动画持续时间为2秒

        // 设置动画监听器
        Animator.AnimatorListener newAnimatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                // 在新动画结束时反转起始和结束位置
                float tempY = startY;
                startY = endY;
                endY = tempY;

                // 创建新的属性动画对象，设置要修改的属性为translationY，并应用反转后的起始和结束位置
                ObjectAnimator reverseAnimY = ObjectAnimator.ofFloat(imageView, "translationY", startY, endY);
                reverseAnimY.setDuration(3500); // 设置动画持续时间为2秒

                // 设置新动画的监听器
                reverseAnimY.addListener(this);

                // 启动反转后的新动画
                reverseAnimY.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };

        // 设置动画监听器
        newAnimY.addListener(newAnimatorListener);

        // 启动动画
        newAnimY.start();
    }

}