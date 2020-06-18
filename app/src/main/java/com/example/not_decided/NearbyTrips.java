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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import static com.example.not_decided.R.layout.list_layout;

public class NearbyTrips extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener,Message_reply.Message_replyListener{
private EditText source,destination;
private Button search;
private RecyclerView result;
private Button select_time,select_date;
private TextView time,date;
private boolean flag=false,flag1=false;
    private RecyclerView.Adapter mAdapter;
private DatabaseReference mTripDatabase,mTripDatabase1;
    ArrayList<Trip_information> triplist = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
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
        mTripDatabase1= FirebaseDatabase.getInstance().getReference("Trips");
        mTripDatabase1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot: dataSnapshot.getChildren()) {
                    Trip_information trip = tripSnapshot.getValue(Trip_information.class);
                    triplist.add(trip);
                }
                makeView(triplist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        result.setLayoutManager(new LinearLayoutManager(this));
        mTripDatabase= FirebaseDatabase.getInstance().getReference("Trips");
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchSource=source.getText().toString();
                String searchDestination=destination.getText().toString();
                firebaseTripSearch(searchSource,searchDestination);
                triplist.clear();
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
    private void firebaseTripSearch(final String searchSource, final String searchDestination){
        Toast.makeText(NearbyTrips.this,"Started",Toast.LENGTH_LONG).show();
        mTripDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tripSnapshot: dataSnapshot.getChildren()) {
                    String input1 = tripSnapshot.getValue(Trip_information.class).getSource().toLowerCase();
                    //   boolean isFound1 = input1.indexOf(searchSource.toLowerCase()) !=-1? true: false;
                    boolean isFound1 = isSubSequence(searchSource, input1, searchSource.length(), input1.length());
                    String input2 = tripSnapshot.getValue(Trip_information.class).getDestination().toLowerCase();
                    // boolean isFound2 = input2.indexOf(searchDestination.toLowerCase()) !=-1? true: false;
                    boolean isFound2 = isSubSequence(searchDestination, input2, searchDestination.length(), input2.length());
                    String input3 = tripSnapshot.getValue(Trip_information.class).getDate();

                    String input4 = tripSnapshot.getValue(Trip_information.class).getTime();
                    boolean isFound4 = flag;
                    if (isFound4 && flag1) {
                        String x = input4.substring(0, 2);
                        int y = Integer.parseInt(x);
                        String z1 = s3.substring(0, 2);
                        int z = Integer.parseInt(z1);
                        boolean isFound3 = input3.equals(s7);
                        if (isFound1 && isFound2 && isFound3 && (y == z || y == z - 1 || y == z + 1 || y == z + 2 || y == z - 2)) {
                            Trip_information trip = tripSnapshot.getValue(Trip_information.class);
                            triplist.add(trip);
                        }
                    } else if (!isFound4 && flag1) {
                        boolean isFound3 = input3.equals(s7);
                        if (isFound1 && isFound2 && isFound3) {
                            Trip_information trip = tripSnapshot.getValue(Trip_information.class);
                            triplist.add(trip);
                        }
                    } else if (isFound4 && !flag1) {
                        String x = input4.substring(0, 2);
                        int y = Integer.parseInt(x);
                        String z1 = s3.substring(0, 2);
                        int z = Integer.parseInt(z1);
                        if (isFound1 && isFound2 && (y == z || y == z - 1 || y == z + 1 || y == z + 2 || y == z - 2)) {
                            Trip_information trip = tripSnapshot.getValue(Trip_information.class);
                            triplist.add(trip);
                        }
                    } else {
                        if (isFound1 && isFound2) {
                            Trip_information trip = tripSnapshot.getValue(Trip_information.class);
                            triplist.add(trip);
                        }
                    }
                }
                makeView(triplist);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void makeView(final ArrayList<Trip_information> triplist) {
        result = findViewById(R.id.new_trips);
        result.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        TripAdapter.BtnClickListener listener = new TripAdapter.BtnClickListener() {
            @Override
            public void onBtnClick(int position) {
                final Trip_information trip = triplist.get(position);

                DatabaseReference ref;
                final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                ref = FirebaseDatabase.getInstance().getReference().child("Users").child(UID);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String name = dataSnapshot.child("name").getValue().toString();
                        Message_reply dialog;
                        dialog = new Message_reply(trip.getName(), new Message(UID, trip.getId(), "", "", name, trip.getName()));
                        dialog.show(getSupportFragmentManager(), "message");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        Button clear1=(Button)findViewById(R.id.clear_search);
        clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                source.setText("");
                destination.setText("");
                time.setText("Enter Time");
                date.setText("Enter Date");
                s3="";
                s7="";
                flag=false;
                flag1=false;
                firebaseTripSearch("","");
                triplist.clear();
            }
        });
        mAdapter = new TripAdapter(triplist, listener);
        result.setLayoutManager(mLayoutManager);
        result.setAdapter(mAdapter);
    }

    @Override
    public void applyTextsReply(Message message) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Chats");
        String key = ref.push().getKey();
        ref.child(key).setValue(message);
        Toast.makeText(NearbyTrips.this, "Message Sent",
                Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        time.setText("Hour: " + hourOfDay + " Minute: " + minute);
        s1=String.valueOf(hourOfDay);
        s2=String.valueOf(minute);
        if(s1.length()==1)
        {
            s1="0"+s1;
        }
        flag=true;
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
        flag1=true;
        int x=Integer.parseInt(s5);
        x=x+1;
        s5=String.valueOf(x);
        s7=s6+"/"+s5+"/"+s4;
        date.setText(currentDateString);
    }
    static boolean isSubSequence(String str1, String str2, int m, int n)
    {
        str1=str1.toLowerCase();
        str2=str2.toLowerCase();
        if (m == 0)
            return true;
        if (n == 0)
            return false;

        if (str1.charAt(m-1) == str2.charAt(n-1))
            return isSubSequence(str1, str2, m-1, n-1);

        return isSubSequence(str1, str2, m, n-1);
    }


}
