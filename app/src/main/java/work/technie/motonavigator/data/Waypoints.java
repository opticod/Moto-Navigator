/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anupam on 21/7/16.
 */
public class Waypoints implements Parcelable {
    public final Parcelable.Creator<Waypoints> CREATOR = new Parcelable.Creator<Waypoints>() {
        @Override
        public Waypoints createFromParcel(Parcel parcel) {
            return new Waypoints(parcel);
        }

        @Override
        public Waypoints[] newArray(int size) {
            return new Waypoints[size];
        }
    };

    private String start_name;
    private String start_lat;
    private String start_long;
    private String dest_name;
    private String dest_lat;
    private String dest_long;
    private String mode;
    private String route_id;
    private String route_duration;
    private String route_distance;

    public Waypoints(String start_name, String start_lat, String start_long, String dest_name, String dest_lat, String dest_long, String mode, String route_id, String route_duration, String route_distance) {
        this.start_name = start_name;
        this.start_lat = start_lat;
        this.start_long = start_long;
        this.dest_name = dest_name;
        this.dest_lat = dest_lat;
        this.dest_long = dest_long;
        this.mode = mode;
        this.route_id = route_id;
        this.route_duration = route_duration;
        this.route_distance = route_distance;
    }

    private Waypoints(Parcel in) {
        this.start_name = in.readString();
        this.start_lat = in.readString();
        this.start_long = in.readString();
        this.dest_name = in.readString();
        this.dest_lat = in.readString();
        this.dest_long = in.readString();
        this.mode = in.readString();
        this.route_id = in.readString();
        this.route_duration = in.readString();
        this.route_distance = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(start_name);
        dest.writeString(start_lat);
        dest.writeString(start_long);
        dest.writeString(dest_name);
        dest.writeString(dest_lat);
        dest.writeString(dest_long);
        dest.writeString(mode);
        dest.writeString(route_id);
        dest.writeString(route_duration);
        dest.writeString(route_distance);
    }
}
