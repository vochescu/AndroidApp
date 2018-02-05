package ro.ase.eu.proiect.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Route implements Parcelable{
    private Long routeId;
    private List<RouteStop> routeStops;

    public Route(){}

    public Route(Long routeId) {
        this.routeId = routeId;
    }

    public Route(Long routeId, List<RouteStop> routeStops) {
        this.routeId = routeId;
        this.routeStops = routeStops;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId=" + routeId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.routeId == null ? -1 : this.routeId);
        dest.writeTypedList(routeStops);
    }

    public Route(Parcel in){
        this.routeId = in.readLong();
        routeStops = new ArrayList<RouteStop>();
        in.readTypedList(routeStops, RouteStop.CREATOR);
    }
    public static Parcelable.Creator<Route> CREATOR= new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel source) {
            return new Route(source);
        }

        @Override
        public Route[] newArray(int i) {
            return new Route[i];
        }
    };
}
