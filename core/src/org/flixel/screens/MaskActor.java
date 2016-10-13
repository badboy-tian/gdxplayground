package org.flixel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by tian on 2016/10/10.
 */

public class MaskActor extends Actor {
    private static final String Vertex = "uniform mat4 u_projTrans;\n" +
            "\n" +
            "attribute vec2 a_position;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "attribute vec4 a_color;\n" +
            "\n" +
            "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            " \n" +
            "void main() {\n" +
            "\tv_color = a_color;\n" +
            "\tv_texCoords = a_texCoord0;\n" +
            "\tgl_Position = u_projTrans * vec4(a_position, 0.0, 1.0);\n" +
            "}";

    private static final String Fragment = "varying vec4 v_color;\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "\n" +
            "uniform sampler2D u_texture;\n" +
            "uniform sampler2D u_texture1; \n" +
            "uniform sampler2D u_mask;\n" +
            "\n" +
            "void main(void) {\n" +
            "\tvec4 texColor0 = texture2D(u_texture, v_texCoords);\n" +
            "\tvec4 texColor1 = texture2D(u_texture1, v_texCoords);\n" +
            "\n" +
            "\tfloat mask = texture2D(u_mask, v_texCoords).a;\n" +
            "\n" +
            "\tgl_FragColor = v_color * mix(texColor0, texColor1, mask);\n" +
            "}";

    //our grass texture
    private Texture tex0;

    //our dirt texture
    private Texture tex1;

    //our mask texture
    private Texture mask;

    //our program
    private ShaderProgram program;

    private DrawablePixmap drawblePixmap;

    public MaskActor(Texture hair) {
        this.tex1 = hair;
        setSize(tex1.getWidth(), tex1.getHeight());

        Pixmap pixmap = new Pixmap((int)(tex1.getWidth() * 1.5f), (int)(tex1.getHeight() * 1.5f), Pixmap.Format.Alpha);
        pixmap.fill();

        tex0 = new Texture(pixmap);
        tex0.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tex0.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        mask = new Texture(pixmap);
        mask.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mask.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        pixmap.dispose();

        program = new ShaderProgram(Vertex, Fragment);
        if (!program.isCompiled()) {
            System.out.println(program.getLog());
        }

        program.begin();
        program.setUniformi("u_texture1", 1);
        program.setUniformi("u_mask", 2);
        program.end();

        tex1.bind(1);
        mask.bind(2);

        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);

        drawblePixmap = new DrawablePixmap(mask);

        initListener();
    }

    private Vector2 last = null;
    private boolean leftDown = false;
    private void initListener() {
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    Vector2 curr = new Vector2(x, getHeight() - y);
                    drawblePixmap.draw(curr);
                    last = curr;
                    leftDown = true;
                    return true;
                }

                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (button == Input.Buttons.LEFT) {
                    Vector2 curr = new Vector2(x, getHeight() - y);
                    drawblePixmap.draw(curr);
                    last = null;
                    leftDown = false;
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                if (leftDown) {
                    Vector2 curr = new Vector2(x, getHeight() - y);
                    drawblePixmap.drawLerped(last, curr);
                    last = curr;
                }
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        drawblePixmap.update();

        ShaderProgram tmp = batch.getShader();
        batch.setShader(program);
        batch.draw(tex0, getX(), getY(), getWidth(), getHeight());
        batch.setShader(tmp);
    }

    private class DrawablePixmap implements Disposable {
        private float brushSize = 20;
        private Color clearColor = new Color(0f, 0f, 0f, 0f);
        private Color drawColor = new Color(1f, 1f, 1f, 1f);

        private Pixmap pixmap;
        private boolean dirty = false;
        Texture t;

        public DrawablePixmap(Texture t) {
            this.t = t;
            this.pixmap = new Pixmap(t.getWidth(), t.getHeight(), Pixmap.Format.Alpha);
            pixmap.setColor(drawColor);
            this.dirty = false;
        }

        public void update() {
            if (dirty) {
                t.draw(pixmap, 0, 0);
                dirty = false;
            }
        }

        public void clear() {
            pixmap.setColor(clearColor);
            pixmap.fill();
            pixmap.setColor(drawColor);
            dirty = true;
        }

        private void drawDot(Vector2 spot) {
            pixmap.fillCircle((int) spot.x, (int) spot.y, (int) brushSize);
        }

        public void draw(Vector2 spot) {
            drawDot(spot);
            dirty = true;
        }

        public void drawLerped(Vector2 from, Vector2 to) {
            float dist = to.dst(from);
            //计算一个alpha步骤，以大约每1/8刷子半径放一个点。 1/8是任意的，但结果是相当不错的。
            float alphaStep = brushSize / (8f * dist);

            float a = 0f;
            while (a < 1f) {
                Vector2 lerped = from.lerp(to, a);
                drawDot(lerped);
                a += alphaStep;
            }

            drawDot(to);
            dirty = true;
        }

        @Override
        public void dispose() {
            t.dispose();
            pixmap.dispose();
        }
    }
}
