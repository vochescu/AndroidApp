package ro.ase.eu.proiect.busWayBucharest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.ArrayList;

import ro.ase.eu.proiect.R;
import ro.ase.eu.proiect.networking.GetImageTask;
import ro.ase.eu.proiect.util.Constants;

public class FeedbackActivity extends AbstractActivity {

    private SharedPreferences preferences;

    SeekBar sbRating;
    TextView tvSeekBarRating;
    EditText etName;
    EditText etPhone;
    EditText etEmail;
    EditText etMessage;
    DatePicker dpDate;
    CheckBox cbAccTerms;
    ImageView img;
    EditText et_URL;
    Button btn_gallery;
    Button btn_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initComponents();

        preferences = getSharedPreferences(Constants.FEEDBACK_SHARED_PREFERENCES_NAME,MODE_PRIVATE);
        // get phone from preferences
        String phone = preferences.getString(Constants.FEEDBACK_PHONE_VIEW_NAME, "");
        if(!phone.isEmpty()){
            etPhone.setText(phone);
        }

        tvSeekBarRating.setText(sbRating.getProgress() + "/" + sbRating.getMax());
        sbRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int p = 0; //progres
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                p = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                tvSeekBarRating.setError(null);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // afisam valoarea in textView
                tvSeekBarRating.setText(p + "/" + seekBar.getMax());
            }
        });

        cbAccTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    cbAccTerms.setError(null);
                }
                else{
                    cbAccTerms.setError(getString(R.string.feedback_terms_cond));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuItem item=menu.findItem(R.id.app_menu_feedback);
        item.setVisible(false);
        return true;
    }
    public void initComponents(){
        sbRating = (SeekBar)findViewById(R.id.feedback_seekBar);
        tvSeekBarRating = (TextView)findViewById(R.id.feedback_tv_seekBar);
        etName = (EditText)findViewById(R.id.feedback_editText_name);
        etPhone = (EditText)findViewById(R.id.feedback_editText_phone);
        etEmail = (EditText)findViewById(R.id.feedback_editText_email);
        etMessage = (EditText)findViewById(R.id.feedback_editText_text);
        dpDate = (DatePicker)findViewById(R.id.feedback_datePicker);
        cbAccTerms = (CheckBox)findViewById(R.id.feedback_checkBox_termsConditions);
        long now = System.currentTimeMillis();
        dpDate.setMaxDate(now);

        img = (ImageView) findViewById(R.id.feedback_image);
        et_URL = (EditText) findViewById(R.id.feedback_imageFromURL_et);
        btn_gallery =(Button) findViewById(R.id.feedback_imageFromGallery_btn);
        btn_URL  =(Button) findViewById(R.id.feedback_imageFromURL_btn);
    }

    private boolean validate(){
        boolean ok = true;
        if(etName.getText() == null || etName.getText().length()<3 || etName.getText().length()>30){
            etName.setError(getString(R.string.feedback_name_error));
            ok = false;
        }
        if(etPhone.getText() == null || etPhone.getText().length()!=10){
            etPhone.setError(getString(R.string.feedback_phone_error));
            ok = false;
        }
        if(etEmail.getText() == null || etEmail.getText().length()<6 || !android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText()).matches()){
            etEmail.setError(getString(R.string.feedback_email_error));
            ok = false;
        }
        if(!cbAccTerms.isChecked()){
            cbAccTerms.setError(getString(R.string.feedback_terms_cond));
            ok = false;
        }
        if(etMessage.getText() == null || etMessage.getText().length()<15)
        {
            etMessage.setError(getString(R.string.feedback_message_error));
            ok = false;
        }
        if(sbRating.getProgress() == 0){
            tvSeekBarRating.setError(getString(R.string.feedback_seekbar_error));
            ok  = false;
        }
        return ok;
    }

    public void goToSaveFeedbackReport(View view){
        if (validate()){
            String file = Constants.CSV_FEEDBACK_FILE_NAME;
            String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            File f = new File(dir + File.separator + file );
            String text =dpDate.getDayOfMonth() + Constants.CSV_SEPARATOR +
                    dpDate.getMonth() + Constants.CSV_SEPARATOR +
                    dpDate.getYear()+ Constants.CSV_SEPARATOR +
                    etName.getText().toString() + Constants.CSV_SEPARATOR +
                    etEmail.getText().toString() + Constants.CSV_SEPARATOR +
                    etPhone.getText().toString()+ Constants.CSV_SEPARATOR +
                    etMessage.getText().toString();

            FileOutputStream outputStream;
            try {
                outputStream = openFileOutput(file, Context.MODE_WORLD_READABLE);
                outputStream.write(text.getBytes());
                outputStream.close();
                Toast.makeText(getApplicationContext(),R.string.csv_exported_message,Toast.LENGTH_SHORT).show();

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {Constants.FEEDBACK_EMAIL_RECIPIENT});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");

                Uri uri = Uri.fromFile(f);
                emailIntent.putExtra(Intent.EXTRA_STREAM,uri);
                startActivity(Intent.createChooser(emailIntent, getString(R.string.feedback_email_provider)));

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),R.string.csv_exported_error,Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(Constants.FEEDBACK_PHONE_VIEW_NAME,etPhone.getText().toString());
            editor.commit();



            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.FEEDBACK_GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //preluare imagine
                Bitmap image = extras.getParcelable("data");
                img.setImageBitmap(image);
            }
        }
    }

    public void goToImageFromGallery(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constants.FEEDBACK_GALLERY_REQUEST_CODE);
    }

    public void goToImageFromURL(View view) {
        if (et_URL.getText() != null && !et_URL.getText().toString().trim().isEmpty()) {

            GetImageTask task = new GetImageTask(img);
            task.execute(et_URL.getText().toString());
            et_URL.setError(null);
        } else {
            et_URL.setError(getString(R.string.feedback_imageFromURL_error));
        }

    }
}
