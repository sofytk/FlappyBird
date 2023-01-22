package com.example.flappybird.model;

public abstract class GameObject {
    public float x, y;
    GameObject(float x, float y){
        this.x = x;
        this.y = y;
    }
    public abstract void update();
}


















