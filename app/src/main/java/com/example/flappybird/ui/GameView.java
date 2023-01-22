package com.example.flappybird.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.flappybird.R;
import com.example.flappybird.model.Bird;
import com.example.flappybird.model.Pipe;

import org.w3c.dom.Text;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    public static final int FPS = 300;
    private final Bitmap background;
    private DrawThread drawThread;
    private Bird bird;
    private Pipe pipe;
    private Paint paint;

    private Bitmap restart;
    private Bitmap restartBtn;
    private int xRestart = 310;
    private int yRestart = 1180;

    private int score;
    private int bestScore;

    public GameView(Context context) {
        super(context);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.back);
        drawThread = new DrawThread();
        getHolder().addCallback(this);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {

        paint = new Paint();

        restart = BitmapFactory.decodeResource(getResources(), R.drawable.restart_panel);
        restartBtn = BitmapFactory.decodeResource(getResources(), R.drawable.restart_btn);

        this.surfaceHolder = holder;
        init();
        drawThread.start();
        Log.d("SSS", "surfaceCreated");
    }



    private void init() {
        bird = new Bird(getContext(), 200, getHeight() / 2f);
        pipe = new Pipe(getContext(), getHeight(), getWidth());
    }

    public boolean onTouchEvent(MotionEvent event) {
        bird.fly();
        if (!drawThread.running) {
            float x = event.getX();
            float y = event.getY();
            if (x > xRestart && x < xRestart + restartBtn.getWidth() && y > yRestart&& y < yRestart + restartBtn.getHeight()) {
                drawThread = new DrawThread();
                init();
                drawThread.start();
                score = 0;
            }
        }
        return super.onTouchEvent(event);
    }

    private void drawFrames(Canvas canvas) {
        Rect backgroundRect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawBitmap(background, null, backgroundRect, null);
        canvas.drawBitmap(bird.getSprite(), bird.x, bird.y, null);
        canvas.drawBitmap(pipe.getPipeTop(), pipe.x, 0, null);
        canvas.drawBitmap(pipe.getPipeBottom(), pipe.x, getHeight() - pipe.getPipeBottom().getHeight(), null);
        paint.setTextSize(100f);
        canvas.drawText(String.valueOf(score), getWidth() / 2f - 20f, 100, paint);

        Log.d("SSS", "drawFrame");
    }

    private void update() {
        bird.update();
        pipe.update();
        if (pipe.isCollision(bird) || bird.y <= 0 || bird.y >= getHeight()) {
            drawThread.running = false;
        } else if (pipe.x == 0) {
            score++;
        }

        if(score+1 % 10 == 0){
            Pipe.xSpeed++;
        }
    }

    private class DrawThread extends Thread {
        private volatile boolean running = true;

        @Override
        public void run() {
            Canvas canvas;
            while (running) {
                canvas = surfaceHolder.lockCanvas();
                try {
                    sleep(1000 / FPS);
                    drawFrames(canvas);
                    update();
                } catch (Exception e) {
                    Log.e("SSS", "kk", e);
                } finally {
                    if (!drawThread.running) {
                        canvas.drawBitmap(restart, 40, 700, null);
                        paint.setTextSize(80f);
                        canvas.drawText("Score: " + score, 180, 1000, paint);
                        if (score > bestScore){
                            bestScore = score;
                        }
                        canvas.drawText("Best score: " + bestScore, 180, 1100, paint);
                        canvas.drawBitmap(restartBtn, xRestart, yRestart, null);
                        Log.d("RRR", "restart");
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {

    }

}











