package com.example.fikus.yasa;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable{
    public static int maxX = 20; // размер по горизонтали
    public static int maxY = 34; // размер по вертикали
    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали
    private boolean firstTime = true;
    public boolean gameRunning = true; public boolean shipAlive = true;
    private Ship ship;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Asteroid> asteroids = new ArrayList<>(); // тут будут харанится астероиды
    private final int ASTEROID_INTERVAL = 15; // время через которое появляются астероиды (в итерациях)
    private int currentTime = 0;

    private StarBackground starBackground;
    private StarBackground starBackgroundDop;

    private StarBackground starBackgroundFast;
    private StarBackground starBackgroundFastDop;

    protected boolean gameOver = false;

    protected int parsecsPassed = 0;

    public GameView(Context context, boolean Menu) {
        super(context);
        //инициализируем обьекты для рисования
        surfaceHolder = getHolder();
        paint = new Paint();
        // инициализируем поток
        gameThread = new Thread(this);
        gameThread.start();
        if (Menu) {
            shipAlive = false;
        }
    }

    @Override
    public void run() {
//        asteroidsAvoided = 0;

        while (gameRunning) {
            update();
            draw();
            checkCollision();
            checkIfNewAsteroid();
            control();


        }
    }

    private void update() {
        if(!firstTime) {

            if (shipAlive) {
                ship.update();
            }

            for (Asteroid asteroid : asteroids) {
                asteroid.update();
            }

            starBackground.update();
            starBackgroundDop.update();
            starBackgroundFast.update();
            starBackgroundFastDop.update();


        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface

            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

        starBackground = new StarBackground(getContext(), (float) 0.18, "back", 0);
        starBackgroundDop = new StarBackground(getContext(), (float)0.18, "back",-52);
        starBackgroundFast = new StarBackground(getContext(), (float) 0.35, "above", 0);
        starBackgroundFastDop = new StarBackground(getContext(), (float)0.35, "above",-52);


                ship = new Ship(getContext()); // добавляем корабль
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawColor(Color.BLACK); // заполняем фон чёрным

                starBackground.drow(paint, canvas);
                starBackgroundDop.drow(paint, canvas);

                    if (starBackground.getCurrentY() >= maxY) {
                        starBackground.changeY(-53);
                        parsecsPassed++;
                    }
                    if (starBackgroundDop.getCurrentY() >= maxY) {
                        starBackgroundDop.changeY(-53);
                        parsecsPassed++;
                    }

                starBackgroundFast.drow(paint, canvas);
                starBackgroundFastDop.drow(paint, canvas);

                    if (starBackgroundFast.getCurrentY() >= maxY) {
                        starBackgroundFast.changeY(-53);
                    }
                    if (starBackgroundFastDop.getCurrentY() >= maxY) {
                        starBackgroundFastDop.changeY(-53);
                    }


            if (shipAlive)
                {ship.drow(paint, canvas);}// рисуем корабль


            for(Asteroid asteroid: asteroids){ // рисуем астероиды
                if (asteroid.y <= GameView.maxY) {
                    asteroid.drow(paint, canvas);
                }

            }

            for (Asteroid asteroid : asteroids) { //а тут взрыв...ужасно реализованны
                if(asteroid.isCollision(ship.x, ship.y, ship.size) && shipAlive){
                    //игрок проиграл
                    Explosion exp = new Explosion(getContext(), ship.x, ship.y); //взрыв
                    exp.drow(paint,canvas);
                }
            }

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas
        }
    }

    private void control() { // пауза на 17 миллисекунд
        try {
            Thread.sleep(17);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void checkCollision() { // перебираем все астероиды и проверяем не касается ли один из них корабля
        for (Asteroid asteroid : asteroids) {

            if(asteroid.isCollision(ship.x, ship.y, ship.size)){
                // игрок проиграл
                shipAlive = false;
                gameOver = true;
            }

        }
    }

    private void checkIfNewAsteroid(){ // каждые CONST итераций добавляем новый астероид
        if(currentTime >= ASTEROID_INTERVAL){
            Asteroid asteroid = new Asteroid(getContext());
            asteroids.add(asteroid);
            currentTime = 0;


        }else{
            currentTime ++;
        }
    }

}
