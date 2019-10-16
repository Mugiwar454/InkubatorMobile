package com.banisaleh.stmik.inkubatormobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.banisaleh.stmik.inkubatormobile.Helper.AppConstant;
import com.banisaleh.stmik.inkubatormobile.Models.GetStart;

public class SplashActivity extends AppCompatActivity {
    Animation fromtop, frombottom;
    SharedPreferences sharedPreferences;
    String res = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        ImageView lv1 = (ImageView) findViewById(R.id.splashimg1);
        TextView lv2 = (TextView) findViewById(R.id.splashimg2);

        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        lv2.setAnimation(frombottom);
        lv1.setAnimation(fromtop);

        sharedPreferences = getSharedPreferences(AppConstant.myPreferenced, MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                GetStart respone = new GetStart();

                respone.setStatus(sharedPreferences.getString("mess", ""));
                if ("".equals(sharedPreferences.getString("mess", "")) && "".equals(res)) {
                    Intent in = new Intent(SplashActivity.this, StartActivity.class);
                    startActivity(in);
                    finish();
                } else if (respone.getStatus().equalsIgnoreCase("success")) {
                    Intent in = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(in);
                } else if (respone.getStatus().equalsIgnoreCase("")) {
                    Intent in = new Intent(SplashActivity.this, StartActivity.class);
                    startActivity(in);
                }


            }
        }, AppConstant.SPALSH_SCREEN);
    }
}
