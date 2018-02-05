package ro.ase.eu.proiect.util;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.R;

public class ReportAdapter extends ArrayAdapter{
    private int resource;
    private List<Line> objects;
    private LayoutInflater inflater;

    public ReportAdapter(@NonNull Context context, int resource, @NonNull List<Line> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.objects = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(this.resource, parent, false);
        TextView tvName = (TextView) row.findViewById(R.id.lv_lines_report_item_tv_name);
        GridView lvStops = (GridView) row.findViewById(R.id.lv_lines_report_item_lv_stops);

        Line line = objects.get(position);
        List<Stop> stops = new ArrayList<>();
        StopAdapeter adapter;
        tvName.setText(line.getNumber() != null ? line.getNumber() : "");

        adapter=new StopAdapeter(getContext(),R.layout.lv_stop_item,stops,inflater);
        lvStops.setAdapter(adapter);
        if(line.getStops() != null)
            stops.addAll(line.getStops());
        adapter.notifyDataSetChanged();
        return row;
    }

}
