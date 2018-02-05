package ro.ase.eu.proiect.busWayBucharest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ro.ase.eu.proiect.database.DatabaseRepository;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.PieChartView;

public class GraphicActivity extends AppCompatActivity {

    DatabaseRepository repository;
    List<Line> lines = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new DatabaseRepository(getApplicationContext());
        repository.open();
        lines.addAll(repository.getAllLines());
        repository.close();
        PieChartView view = new PieChartView(getApplicationContext(), lines);
        setContentView(view);
    }
}
