package com.example.fikus.yasa;

import android.animation.ValueAnimator;
import android.content.Context;
import java.util.Random;

public class Asteroid extends SpaceBody {
    private int radius = 2; // радиус
    double wind;
    private float minSpeed = (float) 0.3; // минимальная скорость
    private float maxSpeed = (float) 0.9; // максимальная скорость


    public Asteroid(Context context) {
        Random random = new Random();

        int switchAsteroid = random.nextInt(3)+1;

        switch (switchAsteroid) {
            case 1: bitmapId = R.drawable.asteroid1; break;
            case 2: bitmapId = R.drawable.asteroid2; break;
            case 3: bitmapId = R.drawable.asteroid3; break;
        }

        y=-2*radius;
        x = random.nextInt(GameView.maxX) - radius;
        size = radius*2;
        speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();
        wind = random.nextFloat()-0.5;



        init(context);
    }

    @Override
    public void update() {

        y += speed;
        x += wind*0.1;


    }

    public boolean isCollision(float shipX, float shipY, float shipSize) {
        float corr = (float)0.4*shipSize; //correction
        return !(((x+size) < shipX + corr) || (x > (shipX+shipSize - corr)) ||
                 ((y+size) < shipY + corr) || (y > (shipY+shipSize - corr)));
    }

//    public boolean isAvoided(float shipX, float shipY, float shipSize) {
//        return !(shipY+shipSize >= y+size);
//    }
}
