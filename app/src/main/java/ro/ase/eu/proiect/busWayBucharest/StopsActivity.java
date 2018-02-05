package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.database.DatabaseRepository;
import ro.ase.eu.proiect.networking.HttpConnection;
import ro.ase.eu.proiect.networking.HttpResponse;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.RouteStop;
import ro.ase.eu.proiect.util.Stop;
import ro.ase.eu.proiect.util.StopAdapeter;

public class StopsActivity extends AppCompatActivity {
    ListView lvStops;
    FloatingActionButton fab;

    Line line;
    StopAdapeter adapter;
    DatabaseRepository repository;
    List<Stop> stops = new ArrayList<>();
    int positionUpdated;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stops);

        lvStops = (ListView) findViewById(R.id.stop_lv);
        fab = (FloatingActionButton) findViewById(R.id.stop_add_fab);

        repository = new DatabaseRepository(getApplicationContext());
        intent = getIntent();
        if(intent.hasExtra(Constants.STOP_LINE_SEND_KEY)){
            line = intent.getParcelableExtra(Constants.STOP_LINE_SEND_KEY);
            if(line.getStops() != null)
                stops.addAll(line.getStops());
            adapter=new StopAdapeter(getApplicationContext(),R.layout.lv_stop_item,stops,getLayoutInflater());
            lvStops.setAdapter(adapter);
        }
        else{
            repository.open();
            stops.addAll(repository.getAllStops());
            adapter=new StopAdapeter(getApplicationContext(),R.layout.lv_stop_item,stops,getLayoutInflater());
            lvStops.setAdapter(adapter);
            repository.close();
        }

        if(stops == null){
            Toast.makeText(getApplicationContext(),R.string.empty_message,Toast.LENGTH_SHORT).show();
        }

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),AddStopActivity.class);
                startActivityForResult(intent, Constants.ADD_STOP_REQUEST_CODE);
            }
        });
        lvStops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),AddStopActivity.class);
                intent.putExtra(Constants.ADD_STOP_EDIT_KEY,stops.get(i));
                positionUpdated = i;
                startActivityForResult(intent,Constants.ADD_STOP_EDIT_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == Constants.ADD_STOP_REQUEST_CODE || requestCode == Constants.ADD_STOP_EDIT_REQUEST_CODE) &&
                resultCode==RESULT_OK && data!=null) {
            Stop result = data.getParcelableExtra(Constants.ADD_STOP_NUMBER);
            if (result != null) {
                repository.open();
                // daca dorim adaugarea
                if (requestCode == Constants.ADD_STOP_REQUEST_CODE) {
                    Long id = repository.insertStop(result);
                    if (id != -1) {
                        result.setId(id);
                        repository.insertRouteStop(new RouteStop(line.getId(),id));
                    }
                    stops.add(result);
                }
                else{
                    // la update resetam controalele vizuale
                    stops.get(positionUpdated).setName(result.getName());
                    stops.get(positionUpdated).setLatitude(result.getLatitude());
                    Log.d("latStops",result.getLatitude().toString());

                    stops.get(positionUpdated).setLongitude(result.getLongitude());

                    repository.updateStop(stops.get(positionUpdated));
                }
                repository.close();
                StopAdapeter currentAdapter=(StopAdapeter) lvStops.getAdapter();
                currentAdapter.notifyDataSetChanged();
            }

        }
        // daca dorim stergerea
        if(resultCode == Constants.RESULT_DELETE_STOP){
            repository.open();
            repository.removeStop(stops.get(positionUpdated));
            repository.removeRouteStop(new RouteStop(line.getId(),stops.get(positionUpdated).getId()));
            repository.close();
            stops.remove(positionUpdated);
            StopAdapeter currentAdapter=(StopAdapeter) lvStops.getAdapter();
            currentAdapter.notifyDataSetChanged();
        }
        setResult(RESULT_OK,intent);
    }
}
