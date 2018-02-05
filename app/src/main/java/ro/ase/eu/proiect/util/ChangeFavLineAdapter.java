package ro.ase.eu.proiect.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ro.ase.eu.proiect.R;

public class ChangeFavLineAdapter extends ArrayAdapter{
    private int resource;
    private List<Line> objects;
    private LayoutInflater inflater;

    public ChangeFavLineAdapter(@NonNull Context context, int resource, @NonNull List<Line> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource=resource;
        this.objects=objects;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowLine = inflater.inflate(this.resource,parent,false);
        TextView tvBus = (TextView) rowLine.findViewById(R.id.lv_search_line_tv_line);
        TextView tvStart = (TextView) rowLine.findViewById(R.id.lv_search_line_tv_startPoint);
        TextView tvEnd = (TextView) rowLine.findViewById(R.id.lv_search_line_tv_endPoint);

        Line line = objects.get(position);
        tvBus.setText(line != null && line.getNumber() != null ? line.getNumber() : "");
        tvStart.setText(line != null && line.getStartingPoint() != null ? line.getStartingPoint() : "");
        tvEnd.setText(line != null && line.getEndingPoint() != null ? line.getEndingPoint() : "");

        return rowLine;
    }
}
