package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.util.Constants;

public class MainActivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        MenuItem item =menu.findItem(R.id.app_menu_main);
        item.setVisible(false);
        return true;
    }

    public void goToBusActivity(View view){
        Intent intent = new Intent(this,BusActivity.class);
        startActivity(intent);
    }
    public void goToFavLinesActivity(View view){
        Intent intent = new Intent(this,FavLinesActivity.class);
        startActivity(intent);
    }
    public void goToMapActivity(View view){
        Intent intent = new Intent(this,MapActivity.class);
        startActivity(intent);
    }
    public void goToTicketsActivity(View view){
        Intent intent = new Intent(this,TicketActivity.class);
        startActivity(intent);
    }
    public void goToReportActivity(View view){
        Intent intent = new Intent(this,ReportActivity.class);
        intent.putExtra(Constants.RAPORT_MAIN_CALLER_KEY,true);
        startActivity(intent);
    }
    public void goToGraphicActivity(View view){
        Intent intent = new Intent(this,GraphicActivity.class);
        startActivity(intent);
    }
}
