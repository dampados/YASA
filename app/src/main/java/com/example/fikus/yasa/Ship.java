package com.example.fikus.yasa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import static com.example.fikus.yasa.MainActivity.speedAv;
import static com.example.fikus.yasa.MainActivity.speedBv;
import static java.lang.StrictMath.abs;

public class Ship extends SpaceBody {

    public Ship(Context context) {

        bitmapId = R.drawable.ship; // определяем начальные параметры
        size = (float)4.5;
        y=GameView.maxY - size - 1;
        x=(GameView.maxX-size)/2;
        init(context); // инициализируем корабль
    }

    public void update() { // перемещаем корабль в зависимости от наклона

        speed = abs(speedAv)*(float)0.25;

               if (x>=-size && x <=GameView.maxX && speedAv>0.4) {
            x = x - speed;
        } else if (x>=-size && x <=GameView.maxX && speedAv<-0.4) {
            x = x + speed;
        } else if (x<-size) {x = -size + (float)0.001;}
          else if (x>GameView.maxX) {x=GameView.maxX - (float)0.00001;}

        speed = abs(speedBv)*(float)0.25;

        if (y<=GameView.maxY+size-1 && y <=GameView.maxY && speedBv>8.4) {
            y = y - speed*(float)0.15;
        } else if (y>=0 && y <=GameView.maxY && speedBv<7.4) {
            y = y + speed*(float)0.40;
        } else if (y>GameView.maxY) {y = GameView.maxY - (float)0.001;}
        else if (y<0) {y=0 + (float)0.00001;}
    }

    void init(Context context) { // сжимаем картинку до нужных размеров
        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);
        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int)(size * GameView.unitW), (int)(size * GameView.unitH), false);
        cBitmap.recycle();
    }

    void drow(Paint paint, Canvas canvas){ // рисуем картинку
        canvas.drawBitmap(bitmap, x*GameView.unitW, y*GameView.unitH, paint);
    }
}
