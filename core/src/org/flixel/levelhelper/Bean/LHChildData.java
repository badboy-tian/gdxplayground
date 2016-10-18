package org.flixel.levelhelper.Bean;

import java.util.Arrays;

/**
 * Created by tian on 2016/10/18.
 */

public class LHChildData {
    private int alpha;
    private String anchor;
    private String colorOverlay;
    private String generalPosition;
    private String imageFileName;
    private String name;
    private LHNodePhysicData nodePhysics;
    private String nodeType;
    private String parentUUID;
    private String relativeImagePath;
    private int rotation;
    private String scale;
    private String size;
    private String spriteName;
    private String spriteUUID;
    private String[] tags;
    private String uuid;
    private int zOrder;
    private LHChildData[] children;

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public String getColorOverlay() {
        return colorOverlay;
    }

    public void setColorOverlay(String colorOverlay) {
        this.colorOverlay = colorOverlay;
    }

    public String getGeneralPosition() {
        return generalPosition;
    }

    public void setGeneralPosition(String generalPosition) {
        this.generalPosition = generalPosition;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LHNodePhysicData getNodePhysics() {
        return nodePhysics;
    }

    public void setNodePhysics(LHNodePhysicData nodePhysics) {
        this.nodePhysics = nodePhysics;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getParentUUID() {
        return parentUUID;
    }

    public void setParentUUID(String parentUUID) {
        this.parentUUID = parentUUID;
    }

    public String getRelativeImagePath() {
        return relativeImagePath;
    }

    public void setRelativeImagePath(String relativeImagePath) {
        this.relativeImagePath = relativeImagePath;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }

    public String getSpriteUUID() {
        return spriteUUID;
    }

    public void setSpriteUUID(String spriteUUID) {
        this.spriteUUID = spriteUUID;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getzOrder() {
        return zOrder;
    }

    public void setzOrder(int zOrder) {
        this.zOrder = zOrder;
    }

    public LHChildData[] getChildren() {
        return children;
    }

    public void setChildren(LHChildData[] children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "LHChildData{" +
                "alpha=" + alpha +
                ", anchor='" + anchor + '\'' +
                ", colorOverlay='" + colorOverlay + '\'' +
                ", generalPosition='" + generalPosition + '\'' +
                ", imageFileName='" + imageFileName + '\'' +
                ", name='" + name + '\'' +
                ", nodePhysics=" + nodePhysics +
                ", nodeType='" + nodeType + '\'' +
                ", parentUUID='" + parentUUID + '\'' +
                ", relativeImagePath='" + relativeImagePath + '\'' +
                ", rotation=" + rotation +
                ", scale='" + scale + '\'' +
                ", size='" + size + '\'' +
                ", spriteName='" + spriteName + '\'' +
                ", spriteUUID='" + spriteUUID + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", uuid='" + uuid + '\'' +
                ", zOrder=" + zOrder +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}
