package org.flixel.levelhelper.Bean;

import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/10/18.
 */

public class LHRootData {
    private int aspect;
    private String backgroundColor;
    private LHChildData[] children;
    private Vector2 designResolution;
    private LHDeviceData[] devices;
    private Vector2 globalGravityDirection;
    private int globalGravityForce;
    private String nodeType;
    private int sceneFormat;
    private String[] tags;
    private boolean useGlobalGravity;
    private String uuid;

    public int getAspect() {
        return aspect;
    }

    public void setAspect(int aspect) {
        this.aspect = aspect;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public LHChildData[] getChildren() {
        return children;
    }

    public void setChildren(LHChildData[] children) {
        this.children = children;
    }


    public LHDeviceData[] getDevices() {
        return devices;
    }

    public void setDevices(LHDeviceData[] devices) {
        this.devices = devices;
    }

    public Vector2 getGlobalGravityDirection() {
        return globalGravityDirection;
    }

    public void setGlobalGravityDirection(Vector2 globalGravityDirection) {
        this.globalGravityDirection = globalGravityDirection;
    }

    public int getGlobalGravityForce() {
        return globalGravityForce;
    }

    public void setGlobalGravityForce(int globalGravityForce) {
        this.globalGravityForce = globalGravityForce;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public int getSceneFormat() {
        return sceneFormat;
    }

    public void setSceneFormat(int sceneFormat) {
        this.sceneFormat = sceneFormat;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isUseGlobalGravity() {
        return useGlobalGravity;
    }

    public void setUseGlobalGravity(boolean useGlobalGravity) {
        this.useGlobalGravity = useGlobalGravity;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setDesignResolution(Vector2 designResolution) {
        this.designResolution = designResolution;
    }

    public Vector2 getDesignResolution() {
        return designResolution;
    }

    @Override
    public String toString() {
        return "LHRootData{" +
                "aspect=" + aspect +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", children=" + Arrays.toString(children) +
                ", designResolution='" + designResolution + '\'' +
                ", devices=" + Arrays.toString(devices) +
                ", globalGravityDirection=" + globalGravityDirection +
                ", globalGravityForce=" + globalGravityForce +
                ", nodeType='" + nodeType + '\'' +
                ", sceneFormat=" + sceneFormat +
                ", tags=" + Arrays.toString(tags) +
                ", useGlobalGravity=" + useGlobalGravity +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
