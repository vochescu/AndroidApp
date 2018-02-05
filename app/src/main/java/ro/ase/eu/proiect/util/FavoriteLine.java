package ro.ase.eu.proiect.util;

import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteLine implements Parcelable {
    Long lineId;

    public FavoriteLine(){}

    public FavoriteLine(Long lineId) {
        this.lineId = lineId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    @Override
    public String toString() {
        return "FavoriteLine{" +
                "lineId=" + lineId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.lineId == null ? -1 : this.lineId);
    }
    public FavoriteLine(Parcel in){
        this.lineId = in.readLong();
    }

    public static final Creator<FavoriteLine> CREATOR = new Creator<FavoriteLine>() {
        @Override
        public FavoriteLine createFromParcel(Parcel in) {
            return new FavoriteLine(in);
        }

        @Override
        public FavoriteLine[] newArray(int size) {
            return new FavoriteLine[size];
        }
    };

}
