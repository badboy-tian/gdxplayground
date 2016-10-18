package org.flixel.levelhelper.Utilities;

import com.badlogic.gdx.Gdx;

/**
 * Created by tian on 2016/10/18.
 */

public class LogHelper {
    static String TAG = "libgdx TAG:";
    public static boolean DEBUG = true;

    public static void log(Object o){
        if (DEBUG)
        Gdx.app.log(TAG, "" + o.toString());
    }
}
