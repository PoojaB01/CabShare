package com.example.not_decided;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.google.android.libraries.places.api.Places;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FormAct extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    private Button b1;
    private EditText name1,flight,date,time,ex;
    private DatabaseReference d1;
    private DatabaseReference d2,d3;
    private String text,s1,s2,s3,s4,s5,s6,s7;
    private Button b2,b3,b4;
    private Spinner myspinner;
    final int AUTOCOMPLETE_REQUEST_CODE = 1;
    PlacesClient placesClient;

    TextView source;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Places.initialize(getApplicationContext(), "AIzaSyB7KKGTJJbTyGx4O5vgJgrvPvkr3z-brg0");
        PlacesClient placesClient = Places.createClient(this);



        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_form);
        d1= FirebaseDatabase.getInstance().getReference("Flight Trips");
        d2= FirebaseDatabase.getInstance().getReference("Railway Trips");
        d3= FirebaseDatabase.getInstance().getReference("Other Trips");
        flight=(EditText)findViewById(R.id.e2);
        //date=(EditText)findViewById(R.id.e3);
        //time=(EditText)findViewById(R.id.e4);
        myspinner= (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> myadapter =new ArrayAdapter<String>(FormAct.this,android.R.layout.simple_expandable_list_item_1,getResources().getStringArray(R.array.place));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);

        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button3) ;
        b3=(Button)findViewById(R.id.button5);
        b4=(Button)findViewById(R.id.button11);
        Button select_source = (Button) findViewById(R.id.button_source);
        source = (TextView) findViewById(R.id.source);
        // Set the fields to specify which types of place data to return.
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);

        // Start the autocomplete intent.
        final Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        select_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

        onclick();
    }


    void onclick(){

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormAct.this, HomeActivity.class));
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String flight1=flight.getText().toString().trim();
                String date1=s7;
                String time1=s3;
                String text = myspinner.getSelectedItem().toString();
                if(TextUtils.isEmpty(flight1) || TextUtils.isEmpty(date1) || TextUtils.isEmpty(time1) )
                {
                    Toast.makeText(getApplicationContext(), "Please Fill up all the Details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(text.equals("Airport")) {
                        String x = d1.push().getKey();
                        FormDetails1 form1 = new FormDetails1(x, time1, date1, flight1);
                        d1.child(x).setValue(form1);
                        Toast.makeText(getApplicationContext(), "You would be informed soon with the help of email", Toast.LENGTH_SHORT).show();
                    }
                    else if(text.equals("Railwaystation")){
                        String x = d2.push().getKey();
                        FormDetails2 form1 = new FormDetails2(x, time1, date1, flight1);
                        d2.child(x).setValue(form1);
                        Toast.makeText(getApplicationContext(), "You would be informed soon with the help of email", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String x = d3.push().getKey();
                        FormDetails4 form1 = new FormDetails4(x, time1, date1, flight1);
                        d3.child(x).setValue(form1);
                        Toast.makeText(getApplicationContext(), "You would be informed soon with the help of email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

    };
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);
         s1=String.valueOf(hourOfDay);
         s2=String.valueOf(minute);
         s3=s1+":"+s2;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView = (TextView) findViewById(R.id.textView5);
        s4=String.valueOf(year);
        s5=String.valueOf(month);
        s6=String.valueOf(dayOfMonth);
        s7=s6+"/"+s5+"/"+s4;
        textView.setText(currentDateString);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                source.setText("Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                source.setText(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }



}
