package ro.ase.eu.proiect.networking;

public class Station {
    private String stationName;
    private String latitude;
    private String longitude;
    private Program program;

    public Station(){

    }

    public Station(String stationName, String latitude, String longitude, Program program) {
        this.stationName = stationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.program = program;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public String toString() {
        return "Station: " + stationName+ "; latitude: "+
                latitude + "; longitude: "+ longitude + "; " +program.toString();
    }
}
