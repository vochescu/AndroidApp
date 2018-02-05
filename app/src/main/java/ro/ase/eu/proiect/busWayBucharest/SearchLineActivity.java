package ro.ase.eu.proiect.busWayBucharest;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.database.DatabaseRepository;
import ro.ase.eu.proiect.networking.BusItem;
import ro.ase.eu.proiect.networking.HttpConnection;
import ro.ase.eu.proiect.networking.HttpResponse;
import ro.ase.eu.proiect.util.ChangeFavLineAdapter;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.FavoriteLine;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.LineAdapter;
import ro.ase.eu.proiect.util.SearchLineAdapter;
import ro.ase.eu.proiect.util.Stop;

import static ro.ase.eu.proiect.util.Constants.MAP_LINE_KEY1;
import static ro.ase.eu.proiect.util.Constants.MAP_LINE_KEY2;
import static ro.ase.eu.proiect.util.Constants.MAP_LINE_REQUEST_CODE;

public class SearchLineActivity extends AppCompatActivity {
    List<Line> lines = new ArrayList<>();
    List<BusItem> buses = new ArrayList<>();
    Button button;
    List<FavoriteLine> fav = new ArrayList<>();
    ListView lvSearchLine;
    private static String URL_JSON = "https://api.myjson.com/bins/w02od";
    DatabaseRepository repository;
    Intent intent;
    ChangeFavLineAdapter adapterCH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_line);

        lvSearchLine = (ListView) findViewById(R.id.search_line_lv);
        lvSearchLine.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        repository = new DatabaseRepository(getApplicationContext());
        intent = getIntent();

        if(intent.hasExtra(Constants.EDIT_FAVORITE_LINE_KEY)){
            repository.open();
            fav.addAll(repository.getAllFavLines());
            lines.addAll(repository.getAllLines());
            repository.close();
            for(int i=0;i<fav.size();i++){
                for(int j=0;j<lines.size();j++){
                    if(fav.get(i).getLineId() == lines.get(j).getId()){
                        lines.remove(j);
                    }
                }
            }
            adapterCH=new ChangeFavLineAdapter(getApplicationContext(),R.layout.lv_search_line_item,lines,getLayoutInflater());
            lvSearchLine.setAdapter(adapterCH);
            lvSearchLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    View lvv = lvSearchLine.getChildAt(i);
                    CheckBox cbRound = (CheckBox)lvv.findViewById(R.id.lv_search_line_radioButton);
                    cbRound.setVisibility(View.VISIBLE);
                    cbRound.setChecked(true);
                    intent.putExtra(Constants.EDIT_FAVORITE_LINE_KEY,lines.get(i));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }else {
            HttpConnection connection = new HttpConnection() {
                @Override
                protected void onPostExecute(final HttpResponse httpResponse) {
                    super.onPostExecute(httpResponse);
                    if (httpResponse != null) {
                        Toast.makeText(getApplicationContext(), httpResponse.toString(), Toast.LENGTH_SHORT).show();
                        final SearchLineAdapter adapter = new SearchLineAdapter(getApplicationContext(), R.layout.lv_search_line_item, httpResponse.getBuses(), getLayoutInflater());
                        lvSearchLine.setAdapter(adapter);
                        buses = httpResponse.getBuses();
                        lvSearchLine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                View lvv = lvSearchLine.getChildAt(i);
                                CheckBox cbRound = (CheckBox)lvv.findViewById(R.id.lv_search_line_radioButton);
                                cbRound.setVisibility(View.VISIBLE);
                                cbRound.setChecked(true);
                                Stop s1= new Stop(httpResponse.getBuses().get(i).getStartingPoint().getStationName(),
                                        httpResponse.getBuses().get(i).getStartingPoint().getLatitude(),
                                        httpResponse.getBuses().get(i).getStartingPoint().getLongitude());
                                Stop s2= new Stop(httpResponse.getBuses().get(i).getEndingPoint().getStationName(),
                                        httpResponse.getBuses().get(i).getEndingPoint().getLatitude(),
                                        httpResponse.getBuses().get(i).getEndingPoint().getLongitude());
                                intent.putExtra(MAP_LINE_KEY1,s1);
                                intent.putExtra(MAP_LINE_KEY2,s2);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.empty_message, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            connection.execute(URL_JSON);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_done_item) {
            this.finish();
        }
        return true;
    }
}