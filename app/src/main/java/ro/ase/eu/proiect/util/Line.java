package ro.ase.eu.proiect.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Line implements Parcelable{
    private String globalId;
    public  Long id;
    public String number;
    public String startingPoint;
    public String endingPoint;
    private List<Stop> stops;
    public Line() {
        this.stops = new ArrayList<>();
    }

    public Line(String number, String startingPoint, String endingPoint) {
        this.number = number;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.stops = new ArrayList<>();
    }
    public Line(Long id,String number, String startingPoint, String endingPoint) {
        this.id = id;
        this.number = number;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.stops = new ArrayList<>();
    }
    public Line(String number, String startingPoint, String endingPoint,List<Stop> stops) {

        this.number = number;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.stops = new ArrayList<>();
        this.stops = stops;
    }

    public Line(Long id, String number, String startingPoint, String endingPoint,List<Stop> stops) {
        this.id = id;
        this.number = number;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.stops = new ArrayList<>();
        this.stops = stops;
    }
    public Line(String globalId, Long id, String number, String startingPoint, String endingPoint,List<Stop> stops) {
        this.globalId = globalId;
        this.id = id;
        this.number = number;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.stops = new ArrayList<>();
        this.stops = stops;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Stop> getRouteId() {
        return stops;
    }

    public void setRouteId(List<Stop> stops) {
        this.stops = stops;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId){
        this.globalId = globalId;
    }

    @Override
    public String toString() {
        return "Line "+ this.number + "{ "+ this.startingPoint+ ", "+ this.endingPoint+"}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id == null ? -1 : this.id);
        dest.writeString(this.number);
        dest.writeString(this.startingPoint);
        dest.writeString(this.endingPoint);
        dest.writeTypedList(stops);
    }

    public List<Stop> getStops() {
        return stops;
    }

    public void setStops(List<Stop> stops) {
        this.stops = stops;
    }

    public Line(Parcel in) {
        this.id = in.readLong();
        this.number = in.readString();
        this.startingPoint = in.readString();
        this.endingPoint = in.readString();
        this.stops = new ArrayList<>();
        in.readTypedList(stops, Stop.CREATOR);
    }
    public static Parcelable.Creator<Line> CREATOR= new Creator<Line>() {
        @Override
        public Line createFromParcel(Parcel source) {
            return new Line(source);
        }

        @Override
        public Line[] newArray(int i) {
            return new Line[i];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (number != null ? !number.equals(line.number) : line.number != null) return false;
        if (startingPoint != null ? !startingPoint.equals(line.startingPoint) : line.startingPoint !=null) return false;
        if (endingPoint != null ? !endingPoint.equals(line.endingPoint) : line.endingPoint != null) return false;
        if(stops != null ? stops.size() == line.stops.size() : line.stops != null)return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = number != null ?  number.hashCode() : 0;
        result = startingPoint != null ? 31 * result + startingPoint.hashCode(): 0;
        result = endingPoint != null ? 31 * result + endingPoint.hashCode() : 0;
        result = stops != null ? 31 * result + stops.hashCode() : 0;
        return result;
    }

}
