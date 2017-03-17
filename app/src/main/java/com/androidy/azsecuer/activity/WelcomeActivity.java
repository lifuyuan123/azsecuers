package com.androidy.azsecuer.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.androidy.azsecuer.R;

public class WelcomeActivity extends AppCompatActivity implements Animation.AnimationListener{
    private ImageView iv_wlecome_icon1,iv_wlecome_icon2;
    public static final String TAG="Activitylife";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        find();
        start();
    }
    private void find(){
        iv_wlecome_icon1=(ImageView)findViewById(R.id.iv_wlecome_icon1);
        iv_wlecome_icon2=(ImageView)findViewById(R.id.iv_wlecome_icon2);
    }
    private  void start(){
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim_welcome_icon1);
        Animation animation1=AnimationUtils.loadAnimation(this,R.anim.anim_welcome_icon2);
        iv_wlecome_icon1.startAnimation(animation1);
        iv_wlecome_icon2.startAnimation(animation);
        animation.setAnimationListener(this);
    }

    public WelcomeActivity() {
        super();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        Log.i(TAG,"onAnimationRepeat");
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Log.i(TAG,"onAnimationEnd");
        Intent intent=new Intent(WelcomeActivity.this,HomeAcivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAnimationStart(Animation animation) {
        Log.i(TAG,"onAnimationStart");
    }
}
