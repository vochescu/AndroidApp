package ro.ase.eu.proiect.networking;

import java.util.Arrays;
import java.util.List;

public class Program {
    private List<String>  timetableMF; // timetable Monday-Friday
    private List<String>  timetableSS; // timetable Saturday-Sunday
    private String periodValidity;
    public Program(){

    }

    public Program(List<String>  timetableMF, List<String>  timetableSS, String periodValidity) {
        this.timetableMF = timetableMF;
        this.timetableSS = timetableSS;
        this.periodValidity = periodValidity;
    }

    public List<String> getTimetableMF() {
        return timetableMF;
    }

    public void setTimetableMF(List<String> timetableMF) {
        this.timetableMF = timetableMF;
    }

    public List<String> getTimetableSS() {
        return timetableSS;
    }

    public void setTimetableSS(List<String> timetableSS) {
        this.timetableSS = timetableSS;
    }

    public String getPeriodValidity() {
        return periodValidity;
    }

    public void setPeriodValidity(String periodValidity) {
        this.periodValidity = periodValidity;
    }

    @Override
    public String toString() {
        return "Program{" +
                "timetableMF=" + timetableMF +
                ", timetableSS=" + timetableSS +
                ", periodValidity='" + periodValidity + '\'' +
                '}';
    }
}
