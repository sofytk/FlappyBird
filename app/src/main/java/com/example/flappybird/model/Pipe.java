package com.example.flappybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.flappybird.R;

public class Pipe extends GameObject {

    private Bitmap pipeTop, pipeBottom;

    public static float xSpeed = 15;
    private static final float spacerSize = 200;


    private final float height;
    private final float width;

    public Pipe(Context context, float height, float width) {
        super(width, 0);
        this.height = height;
        this.width = width;

        pipeTop = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe_rotated);
        pipeBottom = BitmapFactory.decodeResource(context.getResources(), R.drawable.pipe);

        generatePipes();

    }

    private void generatePipes() {
        y = random(height / 4f, height * 3 / 4f);

        pipeTop = Bitmap.createScaledBitmap(pipeTop, 200, (int) (y - spacerSize), false);
        pipeBottom = Bitmap.createScaledBitmap(pipeBottom, 200, (int) (height - y - spacerSize), false);

    }

    private float random(float min, float max) {
        return (float) (min + (Math.random() * (max - min)));
    }

    @Override
    public void update() {
        x -= xSpeed;
        if (x <= -pipeBottom.getWidth()) {
            x = width;
            generatePipes();
        }
    }

    public boolean isCollision(GameObject object) {
        if (x - 60 < object.x && x + pipeBottom.getWidth() > object.x) {
            if (object.y < pipeTop.getHeight()) return true;
            return object.y + 30 > height - pipeBottom.getHeight();
        }
        return false;
    }

    public Bitmap getPipeTop() {
        return pipeTop;
    }

    public Bitmap getPipeBottom() {
        return pipeBottom;
    }
}

















