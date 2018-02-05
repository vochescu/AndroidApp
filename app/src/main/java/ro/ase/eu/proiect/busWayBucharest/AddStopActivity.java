package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.Line;
import ro.ase.eu.proiect.util.Stop;

public class AddStopActivity extends AppCompatActivity {

    private EditText et_stopName;
    private EditText et_lat;
    private EditText et_long;
    private Button btn_add;
    private Button btn_delete;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stop);

        et_stopName=(EditText)findViewById(R.id.addStop_activity_et_stopName);
        et_lat=(EditText)findViewById(R.id.add_stop_et_latitude);
        et_long=(EditText)findViewById(R.id.add_stop_et_longitude);
        btn_add=(Button)findViewById(R.id.add_stop_add_btn);
        btn_delete=(Button)findViewById(R.id.add_stop_delete_btn);

        intent=getIntent();

        if(intent.hasExtra(Constants.ADD_STOP_EDIT_KEY)){
            Stop stop = intent.getParcelableExtra(Constants.ADD_STOP_EDIT_KEY);
            if(stop != null){
                setTextViews(stop);
                btn_delete.setVisibility(View.VISIBLE);
            }
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Stop stop = createStopFromComponents();
                    if(stop!=null){
                        intent.putExtra(Constants.ADD_STOP_NUMBER,stop);
                    }
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Constants.RESULT_DELETE_STOP);
                finish();
            }
        });
    }

    private void setTextViews(Stop stop){
        et_stopName.setText(stop.getName() != null ? stop.getName()  : "");
        et_lat.setText(stop.getLatitude() != null ? stop.getLatitude() : "");
        et_long.setText(stop.getLongitude()!= null ? stop.getLongitude() : "");
    }

    private boolean validate(){
        boolean ok = true;
        if(et_stopName.getText()==null || et_stopName.getText().toString().trim().isEmpty()){
            ok = false;
            et_stopName.setError(getString(R.string.stop_nameError));
        }
        if(et_lat.getText()==null || et_lat.getText().toString().trim().isEmpty()){
            ok = false;
            et_lat.setError(getString(R.string.stop_latError));
        }
        if(et_lat.getText()==null || et_lat.getText().toString().trim().isEmpty()){
            ok = false;
            et_lat.setError(getString(R.string.stop_longError));
        }
        return ok;
    }

    private Stop createStopFromComponents(){
        String name =et_stopName.getText().toString();
        String lat=et_lat.getText().toString();
        String lng=et_long.getText().toString();
        return new Stop(name,lat,lng);
    }
}
