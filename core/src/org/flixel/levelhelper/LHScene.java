package org.flixel.levelhelper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by tian on 2016/10/18.
 */

public class LHScene extends Stage{
    public static LHScene createWithContentOfFile(String plistLevelFile){
        LHScene lhScene = new LHScene();
        lhScene.initWithContentOfFile(plistLevelFile);

        return lhScene;
    }

    public boolean initWithContentOfFile(String plistLevelFile){
        NSDictionary dict = null;
        try {
            dict = (NSDictionary) PropertyListParser.parse(Gdx.files.internal(plistLevelFile).readString());
            //LogHelper.log(dict.toString());
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
}
