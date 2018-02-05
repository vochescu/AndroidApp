package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.util.Constants;

public class AboutActivity extends AbstractActivity {
    private SharedPreferences preferences;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ratingBar = (RatingBar) findViewById(R.id.about_ratingBar);

        preferences = getSharedPreferences(Constants.ABOUT_SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        // get rating from preferences
        float rating = preferences.getFloat(Constants.ABOUT_RATING_BAR_NAME, -1);
        if(rating >= 0){
            ratingBar.setRating(rating);
        }
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getApplicationContext(), "New rating: " + v, Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putFloat(Constants.ABOUT_RATING_BAR_NAME,v);
                editor.commit();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item=menu.findItem(R.id.app_menu_about);
        item.setVisible(false);
        return true;
    }

    public void goToFeedback(View view){
        Intent intent = new Intent(this,FeedbackActivity.class);
        startActivity(intent);
    }
}
