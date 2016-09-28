package org.flixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.FloatArray
import com.badlogic.gdx.utils.ShortArray
import org.flixel.bean.TVertexData
import java.util.*

/**
 * Created by tian on 2016/9/28.
 */

class MeshLine : BaseScreen() {

    private var mRadius = 0.0f

    private var mThickness = 0.0f

    private var mPoints: Array<Vector2>? = null;

    private var bIsDragging: Boolean = false

    private var bDrawOutlines: Boolean = false

    private var bDrawConstruction: Boolean = false

    private var mWindowSize: Vector2 = Vector2.Zero

    var shapeRender: ShapeRenderer? = null

    var shader: ShaderProgram? = null
    var mesh: Mesh? = null

    var vertextArr: FloatArray? = null
    var texture: Texture? = null

    var vertexSet: HashSet<TVertexData>? = null

    override fun initData() {
        mRadius = 5.0f;

        mPoints = Array<Vector2>()

        bIsDragging = false;


        bDrawOutlines = true;
        bDrawConstruction = true;

        mWindowSize = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        shapeRender = ShapeRenderer()

        shader = ShaderProgram(Gdx.files.internal("surface_vertex.gles"), Gdx.files.internal("surface_fragment.gles"))

        vertextArr = FloatArray();

        texture = Texture("line.png")
        texture!!.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        texture!!.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        mThickness = texture!!.height.toFloat() / 2
        vertexSet = LinkedHashSet()
    }

    override fun initUI() {

    }

    override fun update(dt: Float) {

        for (vec in mPoints!!) {
            shapeRender!!.color = Color.RED
            shapeRender!!.begin(ShapeRenderer.ShapeType.Filled)
            shapeRender!!.circle(vec.x, vec.y, mRadius)
            shapeRender!!.end()
        }

        for (i in 0..mPoints!!.size - 1) {
            var a: Int = if ((i - 1) < 0) 0 else (i - 1)
            var b: Int = i
            var c: Int = if ((i + 1) >= mPoints!!.size) mPoints!!.size - 1 else (i + 1)
            var d: Int = if ((i + 2) >= mPoints!!.size) mPoints!!.size - 1 else (i + 2)

            drawSegment(mPoints!![a], mPoints!![b], mPoints!![c], mPoints!![d])
        }

        if (mesh != null) {
            Gdx.gl.glDepthMask(false);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);


            texture!!.bind()

            shader!!.begin()
            shader!!.setUniformMatrix("u_worldView", stage.camera.combined);
            mesh!!.render(shader!!, GL20.GL_TRIANGLE_STRIP)
            shader!!.end()

            Gdx.gl.glDepthMask(true);
        }
    }

    var tmp = Vector2();
    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        tmp.setZero()
        tmp.set(screenX.toFloat(), Gdx.graphics.height - screenY.toFloat())
        mPoints!!.add(tmp.cpy());

        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (mPoints!!.size > 1) {
            Gdx.app.log("xx", "point size:" + mPoints!!.size)
            if (mesh != null) {
                mesh!!.dispose()
            }

            for (i in 0..mPoints!!.size - 1) {
                var a: Int = if ((i - 1) < 0) 0 else (i - 1)
                var b: Int = i
                var c: Int = if ((i + 1) >= mPoints!!.size) mPoints!!.size - 1 else (i + 1)
                var d: Int = if ((i + 2) >= mPoints!!.size) mPoints!!.size - 1 else (i + 2)

                caculate(mPoints!![a], mPoints!![b], mPoints!![c], mPoints!![d])
            }


            Gdx.app.log("", "" + vertexSet!!.size)
            var array: ArrayList<TVertexData> = ArrayList(vertexSet);

            for (i in 0..array.size / 2 - 1 - 1) {

                //下1
                var data0 = array[i * 2 + 0];

                if (i == 0)
                    insertData(data0, vertextArr!!)
                //上1
                var data2 = array[i * 2 + 1];
                if (i == 0)
                    insertData(data2, vertextArr!!)

                //下2
                var data1 = array[i * 2 + 2];
                data1.u = caculateU(data0.u, data0.pos, data1.pos)
                insertData(data1, vertextArr!!)

                //上2
                var data3 = array[i * 2 + 3];
                data3.u = data1.u;
                insertData(data3, vertextArr!!)
            }

            mesh = Mesh(
                    true,
                    vertexSet!!.size,
                    vertexSet!!.size,
                    VertexAttribute(VertexAttributes.Usage.Position, 2, "a_position"),
                    VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0")
            )


            mesh!!.setVertices(vertextArr!!.toArray())

            var indexs: ShortArray = ShortArray()
            for (i in 0..vertexSet!!.size - 1) {
                indexs.add(i.toShort())
            }

            Gdx.app.log("", "" + indexs.size)

            mesh!!.setIndices(indexs.toArray())


            vertexSet!!.clear()
            vertextArr!!.clear()
        }

        return super.touchUp(screenX, screenY, pointer, button)
    }

    fun insertData(data: TVertexData, array: FloatArray) {
        Gdx.app.log("", data.toString())
        array.add(data.pos.x)
        array.add(data.pos.y)

        array.add(data.u)
        array.add(data.v)
    }


    fun caculate(p0: Vector2, p1: Vector2, p2: Vector2, p3: Vector2) {
        if (p1.equals(p2)) return;

        var line = p2.cpy().sub(p1).nor()

        var normal = Vector2(-line.y, line.x).nor()

        var tangent1 = if (p0 == p1) line else ((p1.cpy().sub(p0).nor().add(line)).nor())
        var tangent2 = if (p2 == p3) line else ((p3.cpy().sub(p2).nor().add(line)).nor())

        var miter1 = Vector2(-tangent1.y, tangent1.x)
        var miter2 = Vector2(-tangent2.y, tangent2.x)


        var length1: Float = mThickness / normal.dot(miter1);
        var length2: Float = mThickness / normal.dot(miter2);

        var v0: Vector2 = p1.cpy().sub(miter1.cpy().scl(length1));
        var v1 = p2.cpy().sub(miter2.cpy().scl(length2));

        var v2 = p1.cpy().add(miter1.cpy().scl(length1));
        var v3 = p2.cpy().add(miter2.cpy().scl(length2));


        //下1
        vertexSet!!.add(TVertexData(v0.save4(), 0f, 1f))
        //上1
        vertexSet!!.add(TVertexData(v2.save4(), 0f, 0f))
        //下2
        vertexSet!!.add(TVertexData(v1.save4(), 0f, 1f))
        //上2
        vertexSet!!.add(TVertexData(v3.save4(), 0f, 0f))
    }

    fun caculateU(preU: Float, pre: Vector2, curr: Vector2): Float {
        var dx = (curr.x - pre.x).toDouble();
        var dy = (curr.y - pre.y).toDouble();
        var d = Math.sqrt(dx * dx + dy * dy);
        var u = preU + (d / texture!!.getWidth());

        return u.toFloat()
    }

    fun drawSegment(p0: Vector2, p1: Vector2, p2: Vector2, p3: Vector2) {
        if (p1.equals(p2)) return;

        var line = p2.cpy().sub(p1).nor()

        var normal = Vector2(-line.y, line.x).nor()

        var tangent1 = if (p0 == p1) line else ((p1.cpy().sub(p0).nor().add(line)).nor())
        var tangent2 = if (p2 == p3) line else ((p3.cpy().sub(p2).nor().add(line)).nor())

        var miter1 = Vector2(-tangent1.y, tangent1.x)
        var miter2 = Vector2(-tangent2.y, tangent2.x)


        var length1 = mThickness / normal.dot(miter1);
        var length2 = mThickness / normal.dot(miter2);

        shapeRender!!.begin(ShapeRenderer.ShapeType.Line)

        if (bDrawConstruction) {
            Gdx.gl.glLineWidth(1.0f)
            shapeRender!!.color.set(Color.BLACK)
            shapeRender!!.line(p1, p2)


            // draw normals in stippled red
            shapeRender!!.color.set(Color.RED)
            Gdx.gl.glEnable(GL20.GL_LINE_STRIP);
            drawDashedLine(Color.RED, shapeRender!!, Vector2(5f, 5f), p1.cpy().sub(normal.cpy().scl(mThickness)), p1.cpy().add(normal.cpy().scl(mThickness)), 0.5f)
            drawDashedLine(Color.RED, shapeRender!!, Vector2(5f, 5f), p2.cpy().sub(normal.cpy().scl(mThickness)), p2.cpy().add(normal.cpy().scl(mThickness)), 0.5f)
            Gdx.gl.glDisable(GL20.GL_LINE_STRIP)

            shapeRender!!.color.set(Color.GRAY)
            Gdx.gl.glEnable(GL20.GL_LINE_STRIP);
            shapeRender!!.line(p1.cpy().sub(normal.cpy().scl(mThickness)), p2.cpy().sub(normal.cpy().scl(mThickness)))
            shapeRender!!.line(p1.cpy().add(normal.cpy().scl(mThickness)), p2.cpy().add(normal.cpy().scl(mThickness)))
            Gdx.gl.glDisable(GL20.GL_LINE_STRIP)
        }

        if (bDrawOutlines) {
            Gdx.gl.glLineWidth(1.5f)
            shapeRender!!.color.set(Color.BLACK)
            var v0 = p1.cpy().sub(miter1.cpy().scl(length1));
            var v1 = p2.cpy().sub(miter2.cpy().scl(length2));

            var v2 = p1.cpy().add(miter1.cpy().scl(length1));
            var v3 = p2.cpy().add(miter2.cpy().scl(length2));

            shapeRender!!.line(v0, v1)
            shapeRender!!.line(v2, v3)

            Gdx.gl.glEnable(GL20.GL_LINE_STRIP);
            drawDashedLine(Color.BLACK, shapeRender!!, Vector2(5f, 5f), p1.cpy().sub(miter1.cpy().scl(length1)), p1.cpy().add(miter1.cpy().scl(length1)), 0.5f)
            drawDashedLine(Color.BLACK, shapeRender!!, Vector2(5f, 5f), p1.cpy().sub(miter1.cpy().scl(length1)), p2.cpy().add(miter2.cpy().scl(length2)), 0.5f)
            drawDashedLine(Color.BLACK, shapeRender!!, Vector2(5f, 5f), p2.cpy().sub(miter2.cpy().scl(length2)), p2.cpy().add(miter2.cpy().scl(length2)), 0.5f)
            Gdx.gl.glDisable(GL20.GL_LINE_STRIP)
        }
        shapeRender!!.end()
    }

    fun drawDashedLine(color: Color, renderer: ShapeRenderer, dashes: Vector2, start: Vector2, end: Vector2, width: Float) {
        if (dashes.x === 0.0f) {
            return
        }
        var dirX = end.x - start.x
        var dirY = end.y - start.y

        val length = Vector2.len(dirX, dirY)
        dirX /= length
        dirY /= length

        var curLen = 0f
        var curX = 0f
        var curY = 0f

        renderer.color = color

        while (curLen <= length) {
            curX = start.x + dirX * curLen
            curY = start.y + dirY * curLen
            renderer.rectLine(curX, curY, curX + dirX * dashes.x, curY + dirY * dashes.x, width)
            curLen += dashes.x + dashes.y

        }
    }
}

private fun Vector2.save4(): Vector2 {
    this.x = ((Math.round(this.x * 10000f)) / 10000f).toFloat();
    this.y = ((Math.round(this.y * 10000f)) / 10000f).toFloat();

    return this
}