package ro.ase.eu.proiect.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.database.DatabaseRepository;

public class SecondReportAdapter extends ArrayAdapter{
    private int resource;
    private List<FavoriteLine> objects;
    private LayoutInflater inflater;
    DatabaseRepository repository;
    public SecondReportAdapter(@NonNull Context context, int resource, @NonNull List<FavoriteLine> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.resource = resource;
        this.objects = objects;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(this.resource, parent, false);
        TextView tvNumber = (TextView) row.findViewById(R.id.lv_report_second_tv_number);
        TextView tvStart = (TextView) row.findViewById(R.id.lv_report_second_tv_start);
        TextView tvEnd = (TextView) row.findViewById(R.id.lv_report_second_tv_end);

        repository = new DatabaseRepository(getContext());
        repository.open();
        Line line = repository.getOneLine(objects.get(position).getLineId());
        repository.close();

        tvNumber.setText(line.getNumber() != null ? line.getNumber() : "");
        tvStart.setText(line.getStartingPoint() != null ? line.getStartingPoint() + " - " : "");
        tvEnd.setText(line.getEndingPoint() != null ? line.getEndingPoint() : "");

        return row;
    }
}

