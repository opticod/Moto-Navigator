/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator.data;

/**
 * Created by anupam on 21/7/16.
 */
public class Steps {

    private String bearing_before;
    private String bearing_after;
    private String location_lat;
    private String location_long;
    private String type;
    private String instruction;
    private String mode;
    private String duration;
    private String name;
    private String distance;

    public Steps(String bearing_after, String bearing_before, String location_lat, String location_long, String type, String instruction, String mode, String duration, String name, String distance) {
        this.bearing_after = bearing_after;
        this.bearing_before = bearing_before;
        this.location_lat = location_lat;
        this.location_long = location_long;
        this.type = type;
        this.instruction = instruction;
        this.mode = mode;
        this.duration = duration;
        this.name = name;
        this.distance = distance;
    }

    public String getBearing_before() {
        return bearing_before;
    }

    public void setBearing_before(String bearing_before) {
        this.bearing_before = bearing_before;
    }

    public String getBearing_after() {
        return bearing_after;
    }

    public void setBearing_after(String bearing_after) {
        this.bearing_after = bearing_after;
    }

    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_long() {
        return location_long;
    }

    public void setLocation_long(String location_long) {
        this.location_long = location_long;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
