package ro.ase.eu.proiect.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Stop implements Parcelable{
    Long id;
    String name;
    String latitude;
    String longitude;

    public Stop(){}

    public Stop(String name,  String latitude, String longitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public Stop(Long id, String name,  String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.id == null ? -1 : this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.latitude);
        parcel.writeString(this.longitude);
    }

    public Stop(Parcel in){
        this.id=in.readLong();
        this.name=in.readString();
        this.latitude=in.readString();
        this.longitude=in.readString();
    }
    public static Parcelable.Creator<Stop> CREATOR= new Creator<Stop>() {
        @Override
        public Stop createFromParcel(Parcel parcel) {
            return new Stop(parcel);
        }

        @Override
        public Stop[] newArray(int i) {
            return new Stop[i];
        }
    };


}
