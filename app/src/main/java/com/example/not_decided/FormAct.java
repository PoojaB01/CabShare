package com.example.not_decided;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormAct extends AppCompatActivity {
    private Button b1;
    private EditText name1,flight,date,time,ex;
    private DatabaseReference d1;
    private DatabaseReference d2,d3;
    private String text;
    private Spinner myspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        d1= FirebaseDatabase.getInstance().getReference("Flight Trips");
        d2= FirebaseDatabase.getInstance().getReference("Railway Trips");
        d3= FirebaseDatabase.getInstance().getReference("Other Trips");
        flight=(EditText)findViewById(R.id.e2);
        date=(EditText)findViewById(R.id.e3);
        time=(EditText)findViewById(R.id.e4);
        myspinner= (Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<String> myadapter =new ArrayAdapter<String>(FormAct.this,android.R.layout.simple_expandable_list_item_1,getResources().getStringArray(R.array.place));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);

        b1=(Button)findViewById(R.id.button4);
        onclick();
    }
    void onclick(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String flight1=flight.getText().toString().trim();
                String date1=date.getText().toString().trim();
                String time1=time.getText().toString().trim();
                String text = myspinner.getSelectedItem().toString();
                if(TextUtils.isEmpty(flight1) || TextUtils.isEmpty(date1) || TextUtils.isEmpty(time1) )
                {
                    Toast.makeText(getApplicationContext(), "Please Fill up all the Details", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(text.equals("Airport")) {
                        String x = d1.push().getKey();
                        FormDetails1 form1 = new FormDetails1(x, date1, time1, flight1);
                        d1.child(x).setValue(form1);
                        Toast.makeText(getApplicationContext(), "You would be informed soon with the help of email", Toast.LENGTH_SHORT).show();
                    }
                    else if(text.equals("Railwaystation")){
                        String x = d2.push().getKey();
                        FormDetails2 form1 = new FormDetails2(x, date1, time1, flight1);
                        d2.child(x).setValue(form1);
                        Toast.makeText(getApplicationContext(), "You would be informed soon with the help of email", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String x = d3.push().getKey();
                        FormDetails4 form1 = new FormDetails4(x, date1, time1, flight1);
                        d3.child(x).setValue(form1);
                        Toast.makeText(getApplicationContext(), "You would be informed soon with the help of email", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    };

}
