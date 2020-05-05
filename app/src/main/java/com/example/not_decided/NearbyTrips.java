package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.not_decided.R.layout.list_layout;

public class NearbyTrips extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{
private EditText source,destination;
private Button search;
private RecyclerView result;
private Button select_time,select_date;
private TextView time,date;
private DatabaseReference mTripDatabase;
private String s1,s2,s3,s4,s5,s6,s7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_trips);
        source=(EditText)findViewById(R.id.source1);
        destination=(EditText)findViewById(R.id.destination1);
        time=(TextView)findViewById(R.id.time1);
        date=(TextView)findViewById(R.id.date1);
        select_time=(Button) findViewById(R.id.button_time);
        select_date=(Button)findViewById(R.id.button_date);
        search=(Button) findViewById(R.id.search);
        result=(RecyclerView)findViewById(R.id.new_trips);
        result.setHasFixedSize(true);
        result.setLayoutManager(new LinearLayoutManager(this));
        mTripDatabase= FirebaseDatabase.getInstance().getReference("Trips");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchSource=source.getText().toString();
                String searchDestination=destination.getText().toString();
                firebaseTripSearch(searchSource,searchDestination);
            }
        });
        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }
    private void firebaseTripSearch(String searchSource,String searchDestination){
        Toast.makeText(NearbyTrips.this,"Started",Toast.LENGTH_LONG).show();
        Query firebaseSearchQuery=mTripDatabase.orderByChild("source").startAt(searchSource).endAt(searchSource+"\uf8ff");
        //Query firebaseSearchQuery1=firebaseSearchQuery.orderByChild("destination").startAt(searchDestination).endAt(searchDestination+"\uf8ff");
        FirebaseRecyclerAdapter<Trip_information, TripsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Trip_information, TripsViewHolder>(

                Trip_information.class,
                list_layout,
                TripsViewHolder.class,
                firebaseSearchQuery

        ) {
            @Override
            protected void populateViewHolder(TripsViewHolder tripsViewHolder, Trip_information trip_information, int i) {
                    tripsViewHolder.setDetails(getApplicationContext(),trip_information.getName(),trip_information.getPhone(),trip_information.getEmail(),trip_information.getSource()+" -> "+trip_information.getDestination(),trip_information.getTime() +" "+trip_information.getDate());
            }
        };
        result.setAdapter(firebaseRecyclerAdapter);
    }

    public  static class TripsViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public TripsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView= itemView;
        }

        public void setDetails(Context ctx, String name1, String phone1, String email1,String path1,String date1) {
            TextView name2=(TextView)mView.findViewById(R.id.name);
            TextView phone2=(TextView)mView.findViewById(R.id.phone);
            TextView email2=(TextView)mView.findViewById(R.id.email);
            TextView path2=(TextView)mView.findViewById(R.id.path);
            TextView time2=(TextView)mView.findViewById(R.id.date);
            name2.setText(name1);
            phone2.setText(phone1);
            email2.setText(email1);
            path2.setText(path1);
            time2.setText(date1);
        }
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        time.setText("Hour: " + hourOfDay + " Minute: " + minute);
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

        s4=String.valueOf(year);
        s5=String.valueOf(month);
        s6=String.valueOf(dayOfMonth);
        s7=s6+"/"+s5+"/"+s4;
        date.setText(currentDateString);
    }

}
