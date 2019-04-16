package com.example.fikus.yasa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Menu_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String GAME_RECORD = "saved_record";

    private ImageView LogoImage;
    private Animation LogoAnimation;
    private Animation ButtonAnim;
    private Animation EditAnim;
    private Button PlayButton;
    private Button HelpButton;

    EditText HighScoreEdit;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        GameView gameViewMenu = new GameView(this, true);
        LinearLayout psevdoGameLayout = findViewById(R.id.psevdoGameLayout); // находим gameLayout

        psevdoGameLayout.addView(gameViewMenu); // и добавляем в него gameView

        LogoImage = findViewById(R.id.LogoImage);
        PlayButton = findViewById(R.id.PlayButton);
        HelpButton = findViewById(R.id.HelpButton);
        HighScoreEdit = findViewById(R.id.HighScoreEdit);


        PlayButton.getBackground().setColorFilter(new LightingColorFilter(0xCE3429, 0x00CE3429));
        HelpButton.getBackground().setColorFilter(new LightingColorFilter(0xCE3429, 0x00CE3429));
        HighScoreEdit.getBackground().setColorFilter(new LightingColorFilter(0xCE584B, 0x00CE584B));

        PlayButton.setOnClickListener(this);
        HelpButton.setOnClickListener(this);

        LogoAnimation = AnimationUtils.loadAnimation(this, R.anim.logoanim);
        ButtonAnim = AnimationUtils.loadAnimation(this, R.anim.playbuttonanim);
        EditAnim = AnimationUtils.loadAnimation(this, R.anim.highscoreeditanim);

        PlayButton.startAnimation(ButtonAnim);
        HelpButton.startAnimation(ButtonAnim);
        HighScoreEdit.startAnimation(EditAnim);
        LogoImage.startAnimation(LogoAnimation);

        PlayButton.setVisibility(View.VISIBLE);
        HelpButton.setVisibility(View.VISIBLE);
        HighScoreEdit.setVisibility(View.VISIBLE);
        LogoImage.setVisibility(View.VISIBLE);

        loadText();

    }

    private void saveText() {

        String toWrite = String.valueOf(MainActivity.parsecsPassedStatic);


        if (toWrite.compareTo(sPref.getString(GAME_RECORD, "0   ")) > 0) {
            sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(GAME_RECORD, toWrite);
            ed.commit();
        }
    }


    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(GAME_RECORD, "0");
        HighScoreEdit.setText(savedText);

//        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
////        String savedText = sPref.getString(GAME_RECORD, "0");
//        int savedRecord = sPref.getInt(GAME_RECORD, 0);
//        HighScoreEdit.setText(savedRecord);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.PlayButton:

                Intent intentMain = new
                        Intent(getApplicationContext(), MainActivity.class);
                startActivity(intentMain);
                overridePendingTransition(R.anim.activityanim1, R.anim.activityanim2);
            break;

            case R.id.HelpButton:

                Intent intentHelp = new
                        Intent(getApplicationContext(), HelpActivity.class);
                startActivity(intentHelp);
                overridePendingTransition(R.anim.activityanim1, R.anim.activityanim2);
            break;

        }


    }

    @Override
    public void onResume(){
        super.onResume();
        saveText();
        loadText();
    }

    @Override
    public void onPause() {
        super.onPause();
        saveText();

    }

    @Override
    public void onStop() {
        super.onStop();
        saveText();
    }
}

// TODO добавить счет и добавление рекорда check
// TODO добавить счетчик текущего счета check
// TODO сделать лого check
// TODO установить портретный режим check
// TODO добавить движение корабля по Y check
// TODO музыка и звук взрыва
// TODO анимация между активити check
// TODO переделать кнопки и их дизайн check
// TODO добавить уровни сложности
// TODO добавить бонусы
// TODO добавить спрайты действующих баффов
