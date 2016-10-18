package org.flixel.levelhelper.Utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by tian on 2016/10/16.
 */

public class LHUtils {

    public static String jsonFileContent(String path){
        return Gdx.files.internal(path).readString();
    }

    public static String filenameExtension(String name){
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex == -1) return "";

        return name.substring(dotIndex + 1);
    }

    public static String stripExtension(String name){
        int dotIndex = name.lastIndexOf('.');
        if (dotIndex == -1) return name;
        return name.substring(0, dotIndex);
    }

    public static String getPathFromFilename(String name){
        int dotIndex = name.lastIndexOf('/');
        if (dotIndex == -1) return name;
        return name.substring(0, dotIndex + 1);
    }

    public static String getFileFromFilename(String name){
        int dotIndex = name.lastIndexOf('/');
        if (dotIndex == -1) return name;
        return name.substring(dotIndex + 1);
    }

    public static String replaceOccuranceOfStringWithString(String str, String toFind, String toReplace){
        return str.replaceAll(toFind, toReplace);
    }

    public static float LHDegreesToRadians(float angle){
        return MathUtils.degreesToRadians * angle;
    }

    public static float LHRadiansToDegrees(float radian){
        return MathUtils.radiansToDegrees * radian;
    }

    public static Vector2 pointFromString(String str){
        int s = str.indexOf(',', 1);
        if (s != -1 && str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') {
            try {
                float x = Float.parseFloat(str.substring(1, s));
                float y = Float.parseFloat(str.substring(s + 1, str.length() - 1));
                return Vector2.Zero.set(x, y);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public static Vector2 sizeFromString(String str){
        int s = str.indexOf(',', 1);
        if (s != -1 && str.charAt(0) == '{' && str.charAt(str.length() - 1) == '}') {
            try {
                float x = Float.parseFloat(str.substring(1, s));
                float y = Float.parseFloat(str.substring(s + 1, str.length() - 1));
                return Vector2.Zero.set(x, y);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        return null;
    }

    public static Rect rectFromString(String str){
        //"{{255, 200}, {255, 255}}"
        String[] strs = str.split(",");
        for (int i = 0; i < strs.length; i++){
            strs[i] = strs[i].replaceAll("\\{", "").replaceAll("}", "").trim();
        }

        return new Rect(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]),
                Integer.parseInt(strs[2]), Integer.parseInt(strs[3]));
    }

    
}
