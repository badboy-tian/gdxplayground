package org.flixel.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by tian on 2016/9/28.
 */

public class MeshTools {
    private HashSet<JVertexData> vertexSet = null;
    private FloatArray vertextArr;

    private Texture texture;
    private Array<Vector2> points;
    private float mThickness;

    public MeshTools() {
        vertextArr = new FloatArray();
        vertexSet = new LinkedHashSet<JVertexData>();
        points = new Array<Vector2>();
    }

    public FloatArray convert(Array<Vector2> points, Texture texture, float mThickness) {
        vertextArr.clear();
        vertexSet.clear();

        this.points.addAll(points);
        this.texture = texture;
        this.mThickness = mThickness;

        convertMeshVertex();
        return vertextArr;
    }

    private void convertMeshVertex() {
        if (points.size > 1) {

            for (int i = 0; i < points.size; i++) {
                int a = ((i - 1) < 0) ? 0 : (i - 1);
                int b = i;
                int c = ((i + 1) >= points.size) ? points.size - 1 : (i + 1);
                int d = ((i + 2) >= points.size) ? points.size - 1 : (i + 2);

                caculate(points.get(a), points.get(b), points.get(c), points.get(d));
            }

            List<JVertexData> array = new ArrayList<JVertexData>();
            array.addAll(vertexSet);

            for (int i = 0; i < array.size() / 2 - 1; i++) {
                //下1
                JVertexData data0 = array.get(i * 2);
                if (i == 0)
                    insertData(data0);

                //上1
                JVertexData data2 = array.get(i * 2 + 1);
                if (i == 0)
                    insertData(data2);

                //下2
                JVertexData data1 = array.get(i * 2 + 2);
                data1.u = caculateU(data0.u, data0.pos, data1.pos);
                insertData(data1);

                //上2
                JVertexData data3 = array.get(i * 2 + 3);
                data3.u = data1.u;
                insertData(data3);
            }
        }
    }

    private float caculateU(float preU, Vector2 pre, Vector2 curr) {
        float dx = (curr.x - pre.x);
        float dy = (curr.y - pre.y);
        float d = (float) Math.sqrt(dx * dx + dy * dy);

        return preU + (d / texture.getWidth());
    }

    private void insertData(JVertexData data) {
        vertextArr.add(data.pos.x);
        vertextArr.add(data.pos.y);

        vertextArr.add(data.u);
        vertextArr.add(data.v);
    }

    private void caculate(Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3) {
        if (p1.equals(p2)) return;

        Vector2 line = p2.cpy().sub(p1).nor();

        Vector2 normal = new Vector2(-line.y, line.x).nor();

        Vector2 tangent1 = (p0 == p1) ? line : ((p1.cpy().sub(p0).nor().add(line)).nor());
        Vector2 tangent2 = (p2 == p3) ? line : ((p3.cpy().sub(p2).nor().add(line)).nor());

        Vector2 miter1 = new Vector2(-tangent1.y, tangent1.x);
        Vector2 miter2 = new Vector2(-tangent2.y, tangent2.x);


        float length1 = mThickness / normal.cpy().dot(miter1);
        float length2 = mThickness / normal.cpy().dot(miter2);

        Vector2 v0 = p1.cpy().sub(miter1.cpy().scl(length1));
        Vector2 v1 = p2.cpy().sub(miter2.cpy().scl(length2));

        Vector2 v2 = p1.cpy().add(miter1.cpy().scl(length1));
        Vector2 v3 = p2.cpy().add(miter2.cpy().scl(length2));


        //下1
        vertexSet.add(new JVertexData((v0), 0f, 1f));
        //上1
        vertexSet.add(new JVertexData((v2), 0f, 0f));
        //下2
        vertexSet.add(new JVertexData((v1), 0f, 1f));
        //上2
        vertexSet.add(new JVertexData((v3), 0f, 0f));
    }

    private static Vector2 save4(Vector2 v) {
        v.x = ((Math.round(v.x * 10000f)) / 10000f);
        v.y = ((Math.round(v.y * 10000f)) / 10000f);

        return v;
    }

    private class JVertexData {
        Vector2 pos = Vector2.Zero;

        float u = 0f;
        float v = 0f;

        private JVertexData(Vector2 p, float u, float v) {
            this.pos = new Vector2(p);
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof JVertexData)) return false;

            JVertexData that = (JVertexData) o;

            if (Float.compare(that.u, u) != 0) return false;
            if (Float.compare(that.v, v) != 0) return false;
            return pos.equals(that.pos);

        }

        @Override
        public int hashCode() {
            int result = pos.hashCode();

            result = 31 * result + (u != +0.0f ? Float.floatToIntBits(u) : 0);
            result = 31 * result + (v != +0.0f ? Float.floatToIntBits(v) : 0);
            return result;
        }
    }
}
