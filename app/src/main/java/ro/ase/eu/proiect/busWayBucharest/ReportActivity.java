package ro.ase.eu.proiect.busWayBucharest;

import android.content.Context;
import android.media.Image;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.database.DatabaseRepository;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.FavoriteLine;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.ReportAdapter;
import ro.ase.eu.proiect.util.SecondReportAdapter;
import ro.ase.eu.proiect.util.Stop;

public class ReportActivity extends AppCompatActivity {
    ListView lvLines;
    List<Line> lines = new ArrayList<>();
    List<FavoriteLine> favLines = new ArrayList<>();
    DatabaseRepository repository;
    ReportAdapter adapter;
    SecondReportAdapter secondAdapter;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        repository = new DatabaseRepository(getApplicationContext());
        lvLines = (ListView) findViewById(R.id.report_lv_lines);
        fab = (FloatingActionButton)findViewById(R.id.report_fab_save);

        if(getIntent().hasExtra(Constants.RAPORT_MAIN_CALLER_KEY)){

            repository.open();
            lines.addAll(repository.getAllLines());
            adapter=new ReportAdapter(getApplicationContext(),R.layout.lv_lines_report_item,lines,getLayoutInflater());
            lvLines.setAdapter(adapter);
            repository.close();

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String file = Constants.CSV_LINES_FILE_NAME;
                    String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    // Nu am reusit sa testez pe un android telefon cu Android sau in emulator (am AMD, nu Intel, si nu pot folosi emulatorul
                    // In alternativa mea, Nox, nu regasesc fisierele in Documents :( ci in data>data>ro.ase.eu.project>files
                    File f = new File(dir + File.separator + file );
                    String text = "";
                    for(Line l:lines){
                        text = text + l.getNumber() + Constants.CSV_SEPARATOR;
                        for(Stop s:l.getStops()){
                            text = text + s.getName()+ Constants.CSV_SEPARATOR +
                                    s.getLatitude() + Constants.CSV_SEPARATOR +
                                    s.getLongitude()+ Constants.CSV_SEPARATOR
                            ;
                        }
                        text = text + Constants.CSV_NEW_LINE;
                    }
                    FileOutputStream outputStream;

                    try {
                        outputStream = openFileOutput(file, Context.MODE_WORLD_READABLE);
                        outputStream.write(text.getBytes());
                        outputStream.close();
                        Toast.makeText(getApplicationContext(),R.string.csv_exported_message,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),R.string.csv_exported_error,Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }

        else if (getIntent().hasExtra(Constants.RAPORT_FAV_LINES_CALLER_KEY)){
            repository.open();
            favLines.addAll(repository.getAllFavLines());
            lines.addAll(repository.getAllFavLinesJoin());
            repository.close();

            secondAdapter=new SecondReportAdapter(getApplicationContext(),R.layout.lv_report_second_layout,favLines,getLayoutInflater());
            lvLines.setAdapter(secondAdapter);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String file = Constants.CSV_FAV_LINES_FILE_NAME;
                    String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                    File f = new File(dir + File.separator + file );
                    String text = "";
                    for(Line l:lines){
                        text = text + l.getNumber() + Constants.CSV_SEPARATOR +
                                l.getStartingPoint() + Constants.CSV_SEPARATOR +
                                l.getEndingPoint() + Constants.CSV_SEPARATOR +
                                Constants.CSV_NEW_LINE;
                    }
                    FileOutputStream outputStream;

                    try {
                        outputStream = openFileOutput(file, Context.MODE_WORLD_READABLE);
                        outputStream.write(text.getBytes());
                        outputStream.close();
                        Toast.makeText(getApplicationContext(),R.string.csv_exported_message,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(),R.string.csv_exported_error,Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
