package com.example.fikus.yasa;

import android.content.pm.ActivityInfo;
import android.graphics.LightingColorFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener {

    public static float speedAv; public static float speedBv;
    private Sensor maSensor;
    private SensorManager maSM;


    private boolean isFirstTimeAnimateGameOver = true;

    private GameView gameView;

    private TextView TextViewCurrCount;

    public static int parsecsPassedStatic = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        maSM = (SensorManager)getSystemService(SENSOR_SERVICE);
        maSensor = maSM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        maSM.registerListener(this,maSensor,SensorManager.SENSOR_DELAY_GAME);


        gameView = new GameView(this, false); // создаём gameView

        LinearLayout gameLayout = findViewById(R.id.gameLayout); // находим gameLayout
        gameLayout.addView(gameView); // и добавляем в него gameView

        timer(1);

    }

    public void checkForGameOver() {
        if (gameView.gameOver) {
            if (isFirstTimeAnimateGameOver) {
                isFirstTimeAnimateGameOver = false;
                ImageView gameOverImage = findViewById(R.id.GameOverImageView);
                Animation gameOverAnimation = AnimationUtils.loadAnimation      //gameover imaage draawig
                        (this, R.anim.gameoveranim);
                gameOverImage.startAnimation(gameOverAnimation);
                gameOverImage.setVisibility(View.VISIBLE);

                Button buttonFinish = findViewById(R.id.ButtonFinish);
                buttonFinish.getBackground().setColorFilter(new LightingColorFilter(0x770409, 0x00770409));     // gameoberButton drawing
                buttonFinish.setOnClickListener(this);
                Animation buttonFinishGameAnim = AnimationUtils.loadAnimation(this, R.anim.buttonfinishgameanim);
                buttonFinish.startAnimation(buttonFinishGameAnim);
                buttonFinish.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        speedAv = event.values[0];
        speedBv = event.values[2];
    }

    public void timer(final int timel){
        ScheduledExecutorService service2 = Executors.newSingleThreadScheduledExecutor();
        final Handler handler = new Handler(Looper.getMainLooper());
        service2.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        checkForGameOver();

                        if (gameView.shipAlive && !gameView.gameOver) {
                            TextViewCurrCount = findViewById(R.id.TextViewCurrCount);
                            TextViewCurrCount.setText("Parsecs Passed: "+gameView.parsecsPassed);
                            parsecsPassedStatic = gameView.parsecsPassed;
                        }

                    }
                });
            }
        }, 0, timel, TimeUnit.SECONDS);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        this.finish();
        overridePendingTransition(R.anim.activityanim1, R.anim.activityanim2);
    }

}
