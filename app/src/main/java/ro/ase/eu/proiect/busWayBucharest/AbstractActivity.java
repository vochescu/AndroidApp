package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ro.ase.eu.proiect.R;

public class AbstractActivity extends AppCompatActivity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.app_menu,menu);
        return true;
    }
    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent=null;
        switch (item.getItemId()){
            case R.id.app_menu_about:
                intent=new Intent(getApplicationContext(),AboutActivity.class);
                break;
            case R.id.app_menu_feedback:
                intent=new Intent(getApplicationContext(),FeedbackActivity.class);
                break;
            default: intent=new Intent(getApplicationContext(),MainActivity.class);
        }
        startActivity(intent);
        Toast.makeText(getApplicationContext(), R.string.opened_item, Toast.LENGTH_SHORT).show();
        return true;
    }
    public void goToFeedback(View view){
        Intent intent = new Intent(this,FeedbackActivity.class);
        startActivity(intent);
    }
}