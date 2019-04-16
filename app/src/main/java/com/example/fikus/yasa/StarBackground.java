package com.example.fikus.yasa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class StarBackground extends SpaceBody {

    public StarBackground(Context context, float settedSpeed, String currStarBack, float initialY) {

        switch (currStarBack) {
            case"back":
                bitmapId = R.drawable.starsback;
                break;
            case"above":
                bitmapId = R.drawable.starsback1;
                break;
        }
        y = initialY;
        x = 0;
        size = 20;
        speed = settedSpeed;

        init(context);
    }

    void init(Context context) { // сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size * GameView.unitW), (3000), false);
        cBitmap.recycle();
    }

    @Override
    public void update() {
        y += speed;
    }

}
