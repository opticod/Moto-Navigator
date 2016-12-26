/*
 * Copyright (c) 2016 Anupam Das.
 */

package work.technie.motonavigator.data;
/*
 * Copyright (C) 2017 Anupam Das
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anupam on 21/7/16.
 */
public class Steps implements Parcelable {
    public final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel parcel) {
            return new Steps(parcel);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };

    private String route_id;
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

    public Steps(String route_id, String bearing_after, String bearing_before, String location_lat, String location_long, String type, String instruction, String mode, String duration, String name, String distance) {
        this.route_id = route_id;
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

    private Steps(Parcel in) {
        this.route_id = in.readString();
        this.bearing_after = in.readString();
        this.bearing_before = in.readString();
        this.location_lat = in.readString();
        this.location_long = in.readString();
        this.type = in.readString();
        this.instruction = in.readString();
        this.mode = in.readString();
        this.duration = in.readString();
        this.name = in.readString();
        this.distance = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(route_id);
        dest.writeString(bearing_after);
        dest.writeString(bearing_before);
        dest.writeString(location_lat);
        dest.writeString(type);
        dest.writeString(instruction);
        dest.writeString(mode);
        dest.writeString(duration);
        dest.writeString(name);
        dest.writeString(distance);
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
