package org.flixel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PixmapTest extends BaseScreen{

	@Override
	public void show() {


	}
	PixmapActor pixmapActor;
	int count;

    @Override
    public void render(float delta) {
        super.render(delta);

        count ++;
        if (count == 30) {
            count = 0;
            pixmapActor.eraseCircle(MathUtils.random(10, 500), MathUtils.random(10, 500), MathUtils.random(10, 50));
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        pixmapActor = new PixmapActor(new Pixmap(Gdx.files.internal("sprite.png")));
        getStage().addActor(pixmapActor);
        pixmapActor.eraseCircle(200, 200, 100);
    }

    @Override
    public void update(float dt) {

    }
}
