package org.flixel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;

import net.mwplay.cocostudio.ui.CocoStudioUIEditor;

/**
 * Created by Administrator on 2016/10/14.
 */

public class TestCocos extends BaseScreen{
    @Override
    public void initData() {
    }

    @Override
    public void initUI() {
        CocoStudioUIEditor studio = new CocoStudioUIEditor(Gdx.files.internal("MainScene.json"),
                null, null, null, null);
        Group group = studio.createGroup();
        getStage().addActor(group);
    }

    @Override
    public void update(float dt) {

    }
}
