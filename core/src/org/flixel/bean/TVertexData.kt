package org.flixel.bean

import com.badlogic.gdx.math.Vector2

/**
 * Created by tian on 2016/9/28.
 */
open class TVertexData{
    var pos: Vector2 = Vector2.Zero

    var u: Float = 0f;
    var v: Float = 0f;

    constructor(p: Vector2, u: Float, v: Float){
        this.pos = Vector2(p)
        this.u = u;
        this.v = v;
    }


    override fun toString(): String {
        return "TVertexData(pos=$pos, u=$u, v=$v)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TVertexData) return false

        if (pos != other.pos) return false
        if (u != other.u) return false
        if (v != other.v) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pos.hashCode()
        result = 31 * result + u.hashCode()
        result = 31 * result + v.hashCode()

        return result
    }


}