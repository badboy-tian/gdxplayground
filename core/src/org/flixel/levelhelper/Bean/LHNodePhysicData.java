package org.flixel.levelhelper.Bean;

/**
 * Created by tian on 2016/10/18.
 */

public class LHNodePhysicData {
    private boolean allowSleep;
    private int angularDamping;
    private int angularVelocity;
    private boolean bullet;
    private boolean fixedRotation;
    private int gravityScale;
    private int linearDamping;
    private String linearVelocity;
    private int shape;
    private int type;

    public boolean isAllowSleep() {
        return allowSleep;
    }

    public void setAllowSleep(boolean allowSleep) {
        this.allowSleep = allowSleep;
    }

    public int getAngularDamping() {
        return angularDamping;
    }

    public void setAngularDamping(int angularDamping) {
        this.angularDamping = angularDamping;
    }

    public int getAngularVelocity() {
        return angularVelocity;
    }

    public void setAngularVelocity(int angularVelocity) {
        this.angularVelocity = angularVelocity;
    }

    public boolean isBullet() {
        return bullet;
    }

    public void setBullet(boolean bullet) {
        this.bullet = bullet;
    }

    public boolean isFixedRotation() {
        return fixedRotation;
    }

    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    public int getGravityScale() {
        return gravityScale;
    }

    public void setGravityScale(int gravityScale) {
        this.gravityScale = gravityScale;
    }

    public int getLinearDamping() {
        return linearDamping;
    }

    public void setLinearDamping(int linearDamping) {
        this.linearDamping = linearDamping;
    }

    public String getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(String linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public int getShape() {
        return shape;
    }

    public void setShape(int shape) {
        this.shape = shape;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LHNodePhysicData{" +
                "allowSleep=" + allowSleep +
                ", angularDamping=" + angularDamping +
                ", angularVelocity=" + angularVelocity +
                ", bullet=" + bullet +
                ", fixedRotation=" + fixedRotation +
                ", gravityScale=" + gravityScale +
                ", linearDamping=" + linearDamping +
                ", linearVelocity='" + linearVelocity + '\'' +
                ", shape=" + shape +
                ", type=" + type +
                '}';
    }
}
