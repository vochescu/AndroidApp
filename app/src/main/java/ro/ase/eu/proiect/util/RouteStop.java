package ro.ase.eu.proiect.util;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class RouteStop implements Parcelable{
    Long routeId;
    Long stopId;
    public RouteStop(){}

    public RouteStop(Long routeId, Long stopId) {
        this.routeId = routeId;
        this.stopId = stopId;
    }

    public static final Creator<RouteStop> CREATOR = new Creator<RouteStop>() {
        @Override
        public RouteStop createFromParcel(Parcel in) {
            return new RouteStop(in);
        }

        @Override
        public RouteStop[] newArray(int size) {
            return new RouteStop[size];
        }
    };

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getStopId() {
        return stopId;
    }

    public void setStopId(Long stopId) {
        this.stopId = stopId;
    }

    @Override
    public String toString() {
        return "RouteStop{" +
                "routeId=" + routeId +
                ", stopId=" + stopId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeLong(this.routeId == null ? -1 : this.routeId);
        dest.writeLong(this.stopId == null ? -1 : this.stopId);
    }

    public RouteStop(Parcel in){
        this.routeId = in.readLong();
        this.stopId = in.readLong();
    }
}
