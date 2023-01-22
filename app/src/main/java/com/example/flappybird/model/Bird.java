package com.example.flappybird.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.flappybird.R;

public class Bird extends GameObject {

    private static final float Y_ACCELERATION = 5f;
    private Bitmap sprite;
    private Bitmap straight;
    private Bitmap up;
    private Bitmap down;

    private float ySpeed = 0;

    public Bird(Context context, float x, float y) {
        super(x, y);
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_2);
        up = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_1);
        straight = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_2);
        down = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluebird_3);
    }

    public void fly() {
        ySpeed = -40;
    }

    public void update() {
        y += ySpeed;
        ySpeed += Y_ACCELERATION;
        if(ySpeed < -20) sprite = down;
        else if (ySpeed > 20) sprite = up;
        else sprite = straight;
    }
    public Bitmap getSprite(){
        return sprite;
    }
}









