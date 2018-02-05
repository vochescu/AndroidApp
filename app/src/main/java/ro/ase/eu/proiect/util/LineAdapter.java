package ro.ase.eu.proiect.util;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.database.DatabaseRepository;

public class LineAdapter extends ArrayAdapter {
    private int res;
    private List<Line> objects;
    private List<FavoriteLine> favLines;
    private LayoutInflater inflater;
    DatabaseRepository repository;
    public LineAdapter(@NonNull Context ctx, @LayoutRes int resource, @NonNull List objects,LayoutInflater inflater){
        super(ctx,resource,objects);
        this.res=resource;
        this.objects=objects;
        this.inflater=inflater;
        repository = new DatabaseRepository(ctx);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
        View row = inflater.inflate(this.res, parent, false);
        TextView tvBus = (TextView) row.findViewById(R.id.lv_line_item_tv_lineNo);
        TextView tvStart = (TextView) row.findViewById(R.id.lv_line_item_tv_startPoint);
        TextView tvEnd = (TextView) row.findViewById(R.id.lv_line_item_tv_endPoint);
        Line line = objects.get(position);
        tvBus.setText(line != null && line.getNumber() != null ? line.getNumber() : "");
        tvStart.setText(line != null && line.getStartingPoint() != null ? line.getStartingPoint() : "");
        tvEnd.setText(line != null && line.getEndingPoint() != null ? line.getEndingPoint() : "");

        final CheckBox cb = (CheckBox) row.findViewById(R.id.lv_line_item_checkBox);
        cb.setTag(position);

        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(cb.isChecked()){
                   int positionToAdd= (int) view.getTag();
                   FavoriteLine lineToAdd = new FavoriteLine(objects.get(positionToAdd).getId());
                   repository.open();
                   repository.insertFavLine(lineToAdd);
                   repository.close();
               }
               else{
                   int positionToRemove= (int) view.getTag();
                   FavoriteLine lineToRemove = new FavoriteLine(objects.get(positionToRemove).getId());

                   repository.open();
                   repository.removeFavLine(lineToRemove);
                   repository.close();
               }
            }
        });
        favLines= new ArrayList<>();
        repository.open();
        favLines.addAll(repository.getAllFavLines());
        repository.close();
        if (favLines !=null)
        for (int i=0; i<favLines.size(); i++) {
            if(line != null && favLines.get(i) != null && favLines.get(i).getLineId().toString().equals(line.getId().toString())){
                cb.setChecked(true);
            }
        }


        return row;
    }
}
