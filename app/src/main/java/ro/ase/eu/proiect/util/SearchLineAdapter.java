package ro.ase.eu.proiect.util;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.networking.BusItem;

public class SearchLineAdapter extends ArrayAdapter {
    private int res;
    private List<BusItem> objects;
    private LayoutInflater inflater;

    public SearchLineAdapter(@NonNull Context ctx, @LayoutRes int resource, @NonNull List<BusItem> objects, LayoutInflater inflater){
        super(ctx,resource,objects);
        this.res=resource;
        this.objects=objects;
        this.inflater=inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(this.res, parent, false);
        TextView tvBus = (TextView) row.findViewById(R.id.lv_search_line_tv_line);
        TextView tvStart = (TextView) row.findViewById(R.id.lv_search_line_tv_startPoint);
        TextView tvEnd = (TextView) row.findViewById(R.id.lv_search_line_tv_endPoint);

        BusItem bus = objects.get(position);

        tvBus.setText(bus != null && bus.getBusNumber() != null ? bus.getBusNumber() : "");
        tvStart.setText(bus != null && bus.getStartingPoint() != null ? bus.getStartingPoint().getStationName() : "");
        tvEnd.setText(bus != null && bus.getEndingPoint() != null ? bus.getEndingPoint().getStationName() : "");
        return row;
    }

}
