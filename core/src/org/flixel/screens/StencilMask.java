package org.flixel.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by tian on 2016/10/10.
 */

public class StencilMask extends BaseScreen {

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        Image image = new Image(new Texture("sprite.png"));
        image.setPosition(100, 100);
        getStage().addActor(image);

        MaskActor maskActor = new MaskActor(new Texture("line.png"), 1.5f);
        maskActor.setPosition(200, 100);
        getStage().addActor(maskActor);
        maskActor.debug();
        maskActor.setSize(maskActor.getWidth() * 1.5f, maskActor.getHeight() * 1.5f);

        //maskActor.fileMask();
        /*EraserActor eraserActor = new EraserActor(new Texture("monster_hufasu.png"), 1.5f);
        eraserActor.setPosition(200, 100);
        getStage().addActor(eraserActor);
        eraserActor.setSize(eraserActor.getWidth() * 1.5f, eraserActor.getHeight() * 1.5f);*/

        MaskActor maskActor1 = new MaskActor(new Texture("line.png"), 1.5f);
        maskActor1.setPosition(200, 400);
        getStage().addActor(maskActor1);
        maskActor1.debug();
        maskActor1.setSize(maskActor1.getWidth() * 1.5f, maskActor1.getHeight() * 1.5f);
    }

    @Override
    public void update(float dt) {

    }
}
