package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.database.DatabaseRepository;
import ro.ase.eu.proiect.firebase.FbController;
import ro.ase.eu.proiect.networking.BusItem;
import ro.ase.eu.proiect.networking.HttpConnection;
import ro.ase.eu.proiect.networking.HttpResponse;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.FavoriteLine;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.LineAdapter;
import ro.ase.eu.proiect.util.RouteStop;
import ro.ase.eu.proiect.util.SearchLineAdapter;
import ro.ase.eu.proiect.util.Stop;

public class BusActivity extends AppCompatActivity {
    ListView lvBuses;
    FloatingActionButton fabAddBus;
    Button exportBtn;
    CheckBox externDBcb;
    LineAdapter adapter;
    DatabaseRepository repository;
    List<Line> lines = new ArrayList<>();
    List<Line> externalLines = new ArrayList<>();
    Integer linesFromJSON;

    int positionUpdated;
    private static String URL_JSON = "https://api.myjson.com/bins/w02od";
    private SharedPreferences preferences;

    FbController firebaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        lvBuses = (ListView) findViewById(R.id.lv_bus);
        fabAddBus = (FloatingActionButton) findViewById(R.id.bus_floatingActionButton);
        exportBtn = (Button) findViewById(R.id.bus_export_btn);
        externDBcb = (CheckBox) findViewById(R.id.bus_externDB_cb);

        preferences = getSharedPreferences(Constants.BUS_ACTIVITY_SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        // constanta care ne idica daca datele din json au fost incarcate in baza de date
        String init = preferences.getString(Constants.BUS_ACTIVITY_INIT_DATABASE, "");

        repository = new DatabaseRepository(getApplicationContext());
        firebaseController = FbController.getInstance();
        if (init.isEmpty()) {
            HttpConnection connection = new HttpConnection() {
                @Override
                protected void onPostExecute(HttpResponse httpResponse) {
                    super.onPostExecute(httpResponse);
                    repository.open();
                    if (httpResponse != null) {
                        for (int i = 0; i < httpResponse.getBuses().size(); i++) {
                            Stop stopS = new Stop(httpResponse.getBuses().get(i).getStartingPoint().getStationName(),
                                    httpResponse.getBuses().get(i).getStartingPoint().getLatitude(),
                                    httpResponse.getBuses().get(i).getStartingPoint().getLongitude());
                            stopS.setId(repository.insertStop(stopS));
                            Stop stopE = new Stop(httpResponse.getBuses().get(i).getEndingPoint().getStationName(),
                                    httpResponse.getBuses().get(i).getEndingPoint().getLatitude(),
                                    httpResponse.getBuses().get(i).getEndingPoint().getLongitude());
                            stopE.setId(repository.insertStop(stopE));
                            List<Stop> stops = new ArrayList<>();
                            stops.add(stopS);
                            stops.add(stopE);
                            Line l = new Line(httpResponse.getBuses().get(i).getBusNumber(),
                                    httpResponse.getBuses().get(i).getStartingPoint().getStationName(),
                                    httpResponse.getBuses().get(i).getEndingPoint().getStationName(), stops);
                            Long id = repository.insertLine(l);
                            repository.insertRouteStop(new RouteStop(id, stopS.getId()));
                            repository.insertRouteStop(new RouteStop(id, stopE.getId()));
                        }
                    }

                    lines.addAll(repository.getAllLines());
                    adapter = new LineAdapter(getApplicationContext(), R.layout.lv_line_item, lines, getLayoutInflater());
                    lvBuses.setAdapter(adapter);
                    repository.close();
                }
            };
            connection.execute(URL_JSON);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.BUS_ACTIVITY_INIT_DATABASE, getString(R.string.json_added_into_database));
            editor.commit();
        } else {
            repository.open();
            lines.addAll(repository.getAllLines());
            adapter = new LineAdapter(getApplicationContext(), R.layout.lv_line_item, lines, getLayoutInflater());
            lvBuses.setAdapter(adapter);
            repository.close();
        }
        //Add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fabAddBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddLineActivity.class);
                startActivityForResult(intent, Constants.ADD_LINE_REQUEST_CODE);
            }
        });

        lvBuses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), AddLineActivity.class);
                if(!externDBcb.isChecked()){
                    intent.putExtra(Constants.ADD_LINE_EDIT_KEY, lines.get(i));
                    positionUpdated = i;
                }
                else{
                    intent.putExtra(Constants.ADD_LINE_EDIT_KEY, externalLines.get(i));
                    positionUpdated = i;
                }
                startActivityForResult(intent, Constants.ADD_LINE_EDIT_REQUEST_CODE);

            }
        });

        lvBuses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!externDBcb.isChecked()) {

                    Intent intent = new Intent(getApplicationContext(), StopsActivity.class);
                    intent.putExtra(Constants.STOP_LINE_SEND_KEY, lines.get(i));
                    startActivityForResult(intent, Constants.STOP_ACTIVITY_REQUEST_CODE);
                }
                return false;
            }
        });

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lines != null) {

                    boolean result = firebaseController.addAllLines(lines);
                    if (result) {
                        Toast.makeText(getApplicationContext(), getString(R.string.bus_lines_exported_message),
                                Toast.LENGTH_LONG).show();
                        repository.open();
                        for (Line line : lines) {
                            repository.updateLine(line);
                        }
                        repository.close();
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.export_error),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    externDBcb.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View view){
            if (externDBcb.isChecked()) {
                fabAddBus.setVisibility(View.GONE);
                exportBtn.setVisibility(View.GONE);
                firebaseController.findAllLines(uploadLinesFromDatabaseGlobal());
            } else {
                fabAddBus.setVisibility(View.VISIBLE);
                exportBtn.setVisibility(View.VISIBLE);
                repository.open();
                lines.clear();
                lines.addAll(repository.getAllLines());
                adapter = new LineAdapter(getApplicationContext(), R.layout.lv_line_item, lines, getLayoutInflater());
                lvBuses.setAdapter(adapter);
                repository.close();

            }
        }
    });
}
    private ValueEventListener removeLineEvent(final int position) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                externalLines.remove(position);
                LineAdapter currentAdapter= (LineAdapter) lvBuses.getAdapter();
                currentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }
    private ValueEventListener uploadLinesFromDatabaseGlobal() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (externDBcb.isChecked()) {
                    externalLines = new ArrayList<>();
                    if(!adapter.isEmpty())
                    adapter.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Line line = data.getValue(Line.class);
                        if (line != null) {
                            externalLines.add(line);
                            adapter = new LineAdapter(getApplicationContext(), R.layout.lv_line_item, externalLines, getLayoutInflater());
                            lvBuses.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("MainActivity", "Data is not available");
            }
        };
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == Constants.ADD_LINE_REQUEST_CODE || requestCode == Constants.ADD_LINE_EDIT_REQUEST_CODE) && resultCode==RESULT_OK && data!=null){
            Line result =data.getParcelableExtra(Constants.ADD_LINE_NUMBER);
            if(result!=null){
                repository.open();
                // daca dorim adaugarea
                if(requestCode == Constants.ADD_LINE_REQUEST_CODE){
                    Long id = repository.insertLine(result);
                    if(id != -1){
                        result.setId(id);
                    }
                    lines.add(result);
                }
                else{
                    // la update resetam controalele vizuale

                    if(externDBcb.isChecked()){
                        externalLines.get(positionUpdated).setNumber(result.getNumber());
                        externalLines.get(positionUpdated).setStartingPoint(result.getStartingPoint());
                        externalLines.get(positionUpdated).setEndingPoint(result.getEndingPoint());

                        firebaseController.addLine(externalLines.get(positionUpdated));
                    }
                    else{
                        lines.get(positionUpdated).setNumber(result.getNumber());
                        lines.get(positionUpdated).setStartingPoint(result.getStartingPoint());
                        lines.get(positionUpdated).setEndingPoint(result.getEndingPoint());
                        repository.updateLine(externalLines.get(positionUpdated));
                    }
                }
                repository.close();
                LineAdapter currentAdapter=(LineAdapter) lvBuses.getAdapter();
                currentAdapter.notifyDataSetChanged();
            }
        }
        // daca dorim stergerea
        if(resultCode == Constants.RESULT_DELETE_LINE){
            if(!externDBcb.isChecked()){
                repository.open();
                repository.removeLine(lines.get(positionUpdated));
                repository.removeFavLine(new FavoriteLine(lines.get(positionUpdated).getId()));
                repository.close();
                lines.remove(positionUpdated);
                LineAdapter currentAdapter=(LineAdapter) lvBuses.getAdapter();
                currentAdapter.notifyDataSetChanged();
            }else {
                Query query = firebaseController.removePlayer(externalLines.get(positionUpdated));
                firebaseController.findAllLines(uploadLinesFromDatabaseGlobal());

                if (query != null) {
                     query.removeEventListener(removeLineEvent(positionUpdated));
                }
            }
        }
        if(requestCode == Constants.STOP_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            repository.open();
            lines.clear();
            lines.addAll(repository.getAllLines());
            adapter=new LineAdapter(getApplicationContext(),R.layout.lv_line_item,lines,getLayoutInflater());
            lvBuses.setAdapter(adapter);
            repository.close();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
