package game.my.killthemall;

import android.content.Context;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.Canvas;

import android.graphics.Color;

import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Роман on 29.03.2017.
 */
public class GameView extends SurfaceView {
    private final Bitmap bmp;
    private final SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private int x = 0;
    private int y = 0;
    private int xSpeed = 1;
    private int ySpeed = 1;

    public GameView(Context context) {
        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);

                while (retry) {

                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {

                    }
                }
            }
        });

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (x == getWidth() - bmp.getWidth()) {
            xSpeed = 0;
            ySpeed = 1;
        }
        if (y != getHeight() - bmp.getHeight() && x == 0) {
            xSpeed = 1;
            ySpeed = 0;
        }
        if(y == getHeight() - bmp.getHeight()){
            ySpeed = 0;
            xSpeed = -1;
        }
        if(y > 0 && x == 0){
            ySpeed = -1;
            xSpeed = 0;
        }
        x += xSpeed;
        y += ySpeed;
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(bmp, x, y, null);
    }
}
