package com.example.fikus.yasa;

import android.graphics.LightingColorFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button BackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        BackButton = findViewById(R.id.BackButton);
        BackButton.setOnClickListener(this);
        BackButton.getBackground().setColorFilter(new LightingColorFilter(0xCE3429, 0x00CE3429));

    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.activityanim1, R.anim.activityanim2);
    }


}
