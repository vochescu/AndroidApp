package ro.ase.eu.proiect.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Console;
import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.networking.BusItem;


public class StopAdapeter extends ArrayAdapter{

    private int resource;
    private List<Stop> objects;
    private LayoutInflater inflater;

    public StopAdapeter(@NonNull Context context, int resource,  @NonNull List<Stop> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.objects = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(this.resource,parent,false);
        TextView tvName = (TextView) row.findViewById(R.id.lv_stop_tv_name);
        TextView tvLat = (TextView) row.findViewById(R.id.lv_stop_tv_latitude);
        TextView tvLong = (TextView) row.findViewById(R.id.lv_stop_tv_longitude);

        Stop stop = objects.get(position);

        tvName.setText(stop.getName() != null ? stop.getName() : "");
        tvLat.setText(stop.getLatitude() != null ? stop.getLatitude() : "");
        tvLong.setText(stop.getLongitude() != null ? stop.getLongitude() : "");

        return  row;
    }
}
