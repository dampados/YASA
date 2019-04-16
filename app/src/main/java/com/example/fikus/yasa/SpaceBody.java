package com.example.fikus.yasa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class SpaceBody {
    protected float x; // координаты
    protected float y;
    protected float size; // размер
    protected float speed; // скорость
    protected int bitmapId; // id картинки
    protected Bitmap bitmap; // картинка

    void init(Context context) { // сжимаем картинку до нужных размеров
        if (!(bitmapId == R.drawable.ship) || !(bitmapId == R.drawable.starsback)
                                           || !(bitmapId == R.drawable.starsback1)) {
            Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
            bitmap = Bitmap.createScaledBitmap(
                    cBitmap, (int)(size * GameView.unitW), (int)(size * GameView.unitH), false);
            cBitmap.recycle();
        }

    }

    void update() { // тут будут вычисляться новые координаты
    }

    void drow(Paint paint, Canvas canvas) { // рисуем картинку

        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }

    public float getCurrentY() {
        return y;
    }

    public void changeY(float changedY) {
        y = changedY;
    }
}
