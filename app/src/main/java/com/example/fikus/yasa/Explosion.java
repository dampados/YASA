package com.example.fikus.yasa;

import android.content.Context;
import java.util.Random;

public class Explosion extends SpaceBody {
    private int radius = 4; // радиус
//    private float minSpeed = (float) 0.1; // минимальная скорость
//    private float maxSpeed = (float) 0.5; // максимальная скорость


    public Explosion(Context context, float shipX, float shipY) {

        bitmapId = R.drawable.explosion;
        size = radius*2;

        y = shipY;
        x = shipX - radius/2;

        init(context);
    }

    @Override
    public void update() {
    }
}
