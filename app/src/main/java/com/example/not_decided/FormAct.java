package com.example.not_decided;

import androidx.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class FormAct extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
    private Button b1;
    private EditText source,destination;
    private DatabaseReference d1;
    private DatabaseReference d2,d3;
    private String text,s1,s2,s3,s4,s5,s6,s7,email1,phone1,name1;
    private Button b2,b3,b4;
    FirebaseUser user;
    DatabaseReference ref;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        d1= FirebaseDatabase.getInstance().getReference("Trips");
        source=(EditText)findViewById(R.id.editText2);
        destination=(EditText)findViewById(R.id.editText1);
        //date=(EditText)findViewById(R.id.e3);
        //time=(EditText)findViewById(R.id.e4);

        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button3) ;
        b3=(Button)findViewById(R.id.button5);
        b4=(Button)findViewById(R.id.button11);
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

                final String source1=source.getText().toString().trim();
                final String destination1=destination.getText().toString().trim();
                String date1=s7;
                String time1=s3;
                if(TextUtils.isEmpty(source1) || TextUtils.isEmpty(date1) || TextUtils.isEmpty(time1) || TextUtils.isEmpty(destination1) )
                {
                    Toast.makeText(getApplicationContext(), "Please Fill up all the Details", Toast.LENGTH_SHORT).show();
                }
                else
                {

                        final String x = d1.push().getKey();

                    user= FirebaseAuth.getInstance().getCurrentUser();
                    uid=user.getUid();
                    ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             email1 = dataSnapshot.child("email").getValue().toString();
                             phone1 = dataSnapshot.child("phone").getValue().toString();
                             name1 = dataSnapshot.child("name").getValue().toString();
                             Trip_information form1 = new Trip_information(destination1, source1, s3,s7,name1,email1,phone1,uid);
                             d1.child(x).setValue(form1);
                             Toast.makeText(getApplicationContext(), "Trip added.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FormAct.this, HomeActivity.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

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
   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                source.setText("Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);
                source.setText(status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }*/



}
