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

        MaskActor maskActor = new MaskActor(new Texture("monster_hufasu.png"));
        maskActor.setPosition(200, 100);
        getStage().addActor(maskActor);
        maskActor.debug();
        maskActor.setSize(maskActor.getWidth() * 1.5f, maskActor.getHeight() * 1.5f);
    }

    @Override
    public void update(float dt) {

    }
}
