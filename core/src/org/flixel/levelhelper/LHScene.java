package org.flixel.levelhelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;

import org.flixel.levelhelper.Bean.LHChildData;
import org.flixel.levelhelper.Bean.LHRootData;
import org.flixel.levelhelper.Utilities.LHUtils;
import org.flixel.levelhelper.Utilities.LogHelper;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by tian on 2016/10/18.
 */

public class LHScene extends Stage {

    NSDictionary dict = null;

    public static LHScene createWithContentOfFile(String plistLevelFile) {
        LHScene lhScene = new LHScene();
        lhScene.initWithContentOfFile(plistLevelFile);

        return lhScene;
    }

    public boolean initWithContentOfFile(String plistLevelFile) {
        try {
            dict = (NSDictionary) PropertyListParser.parse(Gdx.files.internal(plistLevelFile).file());
            dispatchData();
            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void dispatchData(){
        LHRootData rootData = new LHRootData();
        rootData.setAspect(((NSNumber)dict.objectForKey("aspect")).intValue());
        rootData.setBackgroundColor(dict.objectForKey("backgroundColor").toJavaObject().toString());
        rootData.setUuid(dict.objectForKey("uuid").toJavaObject().toString());
        rootData.setGlobalGravityDirection(sizeFromString(dict, "globalGravityDirection"));
        rootData.setGlobalGravityForce(((NSNumber)dict.objectForKey("globalGravityForce")).intValue());
        rootData.setNodeType(dict.objectForKey("nodeType").toJavaObject().toString());
        rootData.setSceneFormat(((NSNumber)dict.objectForKey("sceneFormat")).intValue());
        rootData.setUseGlobalGravity((Boolean) dict.objectForKey("useGlobalGravity").toJavaObject());
        rootData.setDesignResolution(sizeFromString(dict, "designResolution"));

        NSObject[] tags = array(dict, "tags").getArray();
        String[] tagsStr = new String[tags.length];
        int i = 0;
        for (NSObject tag: tags){
            tagsStr[i] = tag.toJavaObject().toString();
            i++;
        }
        rootData.setTags(tagsStr);

        //rootData.setChildren(parseChildren());
        parseChildren();
    }

    private LHChildData parseChildren() {
        NSObject[] children = array(dict, "children").getArray();
        for (NSObject child: children){
            NSDictionary dictionary = (NSDictionary) child;

            LHChildData childData = new LHChildData();
            childData.setAlpha(number(dictionary, "alpha").intValue());
            childData.setAnchor(sizeFromString(dictionary, "anchor"));
            childData.setColorOverlay(string(dictionary,  "colorOverlay"));
            childData.setGeneralPosition(sizeFromString(dictionary, "generalPosition"));
            childData.setName(string(dictionary, "name"));
            childData.setNodeType(string(dictionary, "nodeType"));
            childData.setParentUUID(string(dictionary, "parentUUID"));
            childData.setRotation(number(dictionary, "rotation").floatValue());
            childData.setScale(sizeFromString(dictionary, "scale"));
            childData.setSize(sizeFromString(dictionary, "size"));
            childData.setzOrder(number(dictionary, "zOrder").intValue());

            NSObject[] tags = array(dictionary, "tags").getArray();
            String[] tagsStr = new String[tags.length];
            int i = 0;
            for (NSObject tag: tags){
                tagsStr[i] = tag.toJavaObject().toString();
                i++;
            }
            childData.setTags(tagsStr);

        }

        return null;
    }

    public String string(NSDictionary dict, String key){
        return dict.objectForKey(key).toJavaObject().toString();
    }

    public NSNumber number(NSDictionary dictionary, String key){
        return (NSNumber) dictionary.objectForKey(key);
    }

    public NSArray array(NSDictionary dict, String key){
        return (NSArray)dict.objectForKey(key);
    }

    public Vector2 sizeFromString(NSDictionary dict, String key){
        return LHUtils.sizeFromString(dict.objectForKey(key).toJavaObject().toString());
    }
}
