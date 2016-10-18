package org.flixel.screens;

import org.flixel.levelhelper.LHScene;

/**
 * Created by tian on 2016/10/18.
 */

public class LHTest extends BaseScreen{
    @Override
    public void initData() {
        LHScene lhScene = LHScene.createWithContentOfFile("level/level1.lhplist");
    }

    @Override
    public void initUI() {

    }

    @Override
    public void update(float dt) {

    }
}
