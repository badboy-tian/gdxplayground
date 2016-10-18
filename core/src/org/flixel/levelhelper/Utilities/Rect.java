package org.flixel.levelhelper.Utilities;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by tian on 2016/10/16.
 */

public class Rect {
    public Vector2 origin = Vector2.Zero.cpy();
    public Vector2 size = Vector2.Zero.cpy();

    public Rect(){

    }

    public Rect(float x, float y, float w, float h){
        origin.set(x, y);
        size.set(w, h);
    }

    @Override
    public String toString() {
        return "Rect{" +
                "origin=" + origin +
                ", size=" + size +
                '}';
    }
}
