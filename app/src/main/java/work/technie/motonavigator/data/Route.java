/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator.data;

/**
 * Created by anupam on 21/7/16.
 */
public class Route {

    private String duration;
    private String distance;

    public Route(String distance, String duration) {
        this.distance = distance;
        this.duration = duration;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
