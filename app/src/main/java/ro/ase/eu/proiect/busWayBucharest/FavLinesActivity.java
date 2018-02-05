package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.database.DatabaseRepository;
import ro.ase.eu.proiect.util.ChangeFavLineAdapter;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.FavoriteLine;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.LineAdapter;
import ro.ase.eu.proiect.util.SearchLineAdapter;

public class FavLinesActivity extends AppCompatActivity {

    ListView lvFavLines;
    TextView tvEmptyList;
    FloatingActionButton fab;
    LineAdapter adapter;
    List<Line> lines=new ArrayList<>();
    List<FavoriteLine> favLines=new ArrayList<>();
    DatabaseRepository repository;
    int positionUpdated =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_lines);

        lvFavLines=(ListView) findViewById(R.id.fav_lines_lv);
        tvEmptyList=(TextView) findViewById(R.id.fav_lines_tv_emptyList);
        fab = (FloatingActionButton)findViewById(R.id.fav_lines_fab_report);

        repository = new DatabaseRepository(getApplicationContext());
        repository.open();
        favLines.addAll(repository.getAllFavLines());

        if(favLines.size() == 0){
           tvEmptyList.setVisibility(View.VISIBLE);
           fab.setVisibility(View.GONE);
        }
        else{
            tvEmptyList.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
            for(int i=0;i<favLines.size();i++){
                if(repository.getOneLine(favLines.get(i).getLineId()) != null){
                    lines.add(repository.getOneLine(favLines.get(i).getLineId()));
                }
            }
        }
        repository.close();

        adapter=new LineAdapter(getApplicationContext(),R.layout.lv_line_item,lines,getLayoutInflater());
        lvFavLines.setAdapter(adapter);

        lvFavLines.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), SearchLineActivity.class);
                intent.putExtra(Constants.EDIT_FAVORITE_LINE_KEY, favLines.get(i));
                positionUpdated = i;
                startActivityForResult(intent, Constants.EDIT_FAVORITE_LINE_REQUEST_CODE);
            }
        });

        //Add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.EDIT_FAVORITE_LINE_REQUEST_CODE && resultCode==RESULT_OK && data!=null) {
            Line result = data.getParcelableExtra(Constants.EDIT_FAVORITE_LINE_KEY);
            if (result != null) {
                repository.open();
                repository.updateFavLine(favLines.get(positionUpdated),new FavoriteLine(result.getId()));
                favLines.clear();
                favLines.addAll(repository.getAllFavLines());
                repository.close();

                lines.remove(positionUpdated);
                lines.add(positionUpdated,result);

                LineAdapter currentAdapter=(LineAdapter) lvFavLines.getAdapter();
                currentAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        return true;
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

    public void goToReportFavLines(View v){
        Intent intent = new Intent(this,ReportActivity.class);
        intent.putExtra(Constants.RAPORT_FAV_LINES_CALLER_KEY,true);
        startActivity(intent);
    }
}
