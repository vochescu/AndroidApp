package ro.ase.eu.proiect.networking;

public class BusItem {
    private String busNumber;
    private Station startingPoint;
    private Station endingPoint;

    public BusItem(){

    }

    public BusItem(String busNumber, Station startingPoint, Station endingPoint) {
        this.busNumber = busNumber;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public Station getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(Station startingPoint) {
        this.startingPoint = startingPoint;
    }

    public Station getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(Station endingPoint) {
        this.endingPoint = endingPoint;
    }

    @Override
    public String toString() {
        return "BusItem{" +
                "busNumber='" + busNumber + '\'' +
                ", startingPoint=" + startingPoint.toString() +
                ", endingPoint=" + endingPoint.toString() +
                '}';
    }
}
