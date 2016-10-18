package org.flixel.levelhelper.Bean;

/**
 * Created by tian on 2016/10/18.
 */

public class LHDeviceData {
    private int ratio;
    private String size;
    private String suffix;

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "LHDeviceData{" +
                "ratio=" + ratio +
                ", size='" + size + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}
