package com.poliveira.apps.example;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.poliveira.apps.imagewindow.ImageWindow;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageWindow imageWindow = (ImageWindow) findViewById(R.id.view);
        imageWindow.getImageView().setImageResource(R.drawable.a);
        imageWindow.setOnCloseListener(new ImageWindow.OnCloseListener() {
            @Override
            public void onCloseClick(final View imageWindow) {
                imageWindow.animate().scaleY(0).scaleX(0).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ((ViewGroup) imageWindow.getParent()).removeView(imageWindow);
                    }
                }).start();
            }
        });
    }
}
