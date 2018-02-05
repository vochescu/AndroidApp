package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.util.Constants;
import ro.ase.eu.proiect.util.Line;

public class AddLineActivity extends AppCompatActivity {
    private EditText et_busNumber;
    private EditText et_startPoint;
    private EditText et_endPoint;
    private Button btn_add;
    private Button btn_delete;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_line);
        initComponents();
        intent=getIntent();

        if(intent.hasExtra(Constants.ADD_LINE_EDIT_KEY)){
            Line line = intent.getParcelableExtra(Constants.ADD_LINE_EDIT_KEY);
            if(line != null){
                setTextViews(line);
                btn_delete.setVisibility(View.VISIBLE);
            }
        }

        if(intent.hasExtra(Constants.EDIT_FAVORITE_LINE_KEY)){
            String s = intent.getStringExtra(Constants.EDIT_FAVORITE_LINE_KEY);
            if(s != null){
                et_busNumber.setText(s);
                et_startPoint.setVisibility(View.GONE);
                et_endPoint.setVisibility(View.GONE);
            }
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    Line line = createLineFromComponents();
                    if(line!=null){
                        intent.putExtra(Constants.ADD_LINE_NUMBER,line);
                    }
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Constants.RESULT_DELETE_LINE);
                Toast.makeText(getApplicationContext(),R.string.add_line_deleted_line_message,Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void initComponents(){
        et_busNumber=(EditText)findViewById(R.id.add_line_tv_busNumber);
        et_startPoint=(EditText)findViewById(R.id.add_line_tv_startPoint);
        et_endPoint=(EditText)findViewById(R.id.add_line_tv_endPoint);
        btn_add=(Button)findViewById(R.id.add_line_add_btn);
        btn_delete=(Button)findViewById(R.id.add_line_delete_btn);
    }
    private void setTextViews(Line line){
        et_busNumber.setText(line.getNumber() != null ? line.getNumber()  : "");
        et_startPoint.setText(line.getStartingPoint() != null ? line.getStartingPoint() : "");
        et_endPoint.setText(line.getEndingPoint()!= null ? line.getEndingPoint() : "");
    }

    private boolean validate(){
        boolean ok = true;
        if(et_busNumber.getText()==null || et_busNumber.getText().toString().trim().isEmpty()){
            ok = false;
            et_busNumber.setError(getString(R.string.add_line_numberError));
        }
        if(et_startPoint.getText()==null || et_startPoint.getText().toString().trim().isEmpty()){
            ok = false;
            et_startPoint.setError(getString(R.string.add_line_startingPointError));
        }
        if(et_endPoint.getText()==null || et_endPoint.getText().toString().trim().isEmpty()){
            ok = false;
            et_endPoint.setError(getString(R.string.add_line_endingPointError));
        }
        return ok;
    }

    private Line createLineFromComponents(){
        String number =et_busNumber.getText().toString();
        String start=et_startPoint.getText().toString();
        String end=et_endPoint.getText().toString();
        return new Line(number,start,end,null);
    }
}
