package ro.ase.eu.proiect.networking;

import java.util.List;

public class HttpResponse {
    private List<BusItem> buses;

    public HttpResponse(List<BusItem> buses){
        this.buses=buses;
    }

    public HttpResponse(){

    }

    public List<BusItem> getBuses() {
        return buses;
    }

    public void setBuses(List<BusItem> buses) {
        this.buses = buses;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "buses=" + buses +
                '}';
    }
}
