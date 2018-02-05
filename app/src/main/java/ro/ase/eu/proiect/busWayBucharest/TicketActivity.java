package ro.ase.eu.proiect.busWayBucharest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.util.Constants;

public class TicketActivity extends AppCompatActivity implements Constants {

    private RadioGroup rgTicketType;
    private CheckBox cbTicketTerms;
    private Button btnBuyTicket;
    private EditText et_busNo;
    private RadioButton rbE780;
    private RadioButton rbE783;
    private RadioButton rbOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        //Add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponents();

        cbTicketTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    cbTicketTerms.setError(null);
                }
                else{
                    cbTicketTerms.setError(getString(R.string.feedback_terms_cond));
                }
            }
        });

        rbE780.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_busNo.setText(Constants.LINE_E780);
                et_busNo.setEnabled(false);
            }
        });
        rbE783.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_busNo.setText(Constants.LINE_E783);
                et_busNo.setEnabled(false);
            }
        });
        rbOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_busNo.setText("");
                et_busNo.setEnabled(true);
            }
        });
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
    private void initComponents(){
        rgTicketType=(RadioGroup)findViewById(R.id.ticket_type_group);
        cbTicketTerms=(CheckBox)findViewById(R.id.ticket_checkBox);
        btnBuyTicket=(Button)findViewById(R.id.button_tickets);
        et_busNo=(EditText)findViewById(R.id.ticket_busNumber_editText);
        rbE780=(RadioButton) findViewById(R.id.ticket_E780_radioButton);
        rbE783=(RadioButton)findViewById(R.id.ticket_E783_radioButton);
        rbOther=(RadioButton)findViewById(R.id.ticket_allDay_radioButton);
    }
    private boolean validate(){
        boolean ok =true;
        if(!cbTicketTerms.isChecked()){
            ok =false;
            cbTicketTerms.setError(getString(R.string.ticket_buy_error));
        }
        if(et_busNo.getText().toString().isEmpty()){
            ok =false;
            et_busNo.setError(getString(R.string.add_line_numberError));
        }
        if(rgTicketType.getCheckedRadioButtonId() == -1){
            ok =false;
            Toast.makeText(TicketActivity.this,R.string.ticket_buy_error,Toast.LENGTH_SHORT).show();
        }
        return ok;
    }

    public void goToBuyTicket(View view){
        if(validate()) {
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("smsto", SPECIAL_PHONE_NUMBER, null));
            String body;
            if(rgTicketType.getCheckedRadioButtonId() == R.id.ticket_E780_radioButton){
                body = LINE_E780;
            }else {
                if(rgTicketType.getCheckedRadioButtonId() == R.id.ticket_E783_radioButton){
                    body = LINE_E783;
                } else {
                    body = et_busNo.getText().toString();
                }
            }
            sendIntent.putExtra("sms_body", body);
            startActivity(sendIntent);
        }
    }
}
