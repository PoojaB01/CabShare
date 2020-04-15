package com.example.not_decided;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FormAct extends AppCompatActivity {
    private Button b1;
    private EditText name1,flight,date,time,ex;
    private DatabaseReference d1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        d1= FirebaseDatabase.getInstance().getReference();
        name1=(EditText)findViewById(R.id.e1);
        flight=(EditText)findViewById(R.id.e2);
        date=(EditText)findViewById(R.id.e3);
        time=(EditText)findViewById(R.id.e4);
        ex=(EditText)findViewById(R.id.eid);
        b1=(Button)findViewById(R.id.button4);
        onclick();
    }
    void onclick(){
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name2=name1.getText().toString().trim();
                String flight1=flight.getText().toString().trim();
                String date1=date.getText().toString().trim();
                String time1=time.getText().toString().trim();
                String ex1=ex.getText().toString().trim();
                if(TextUtils.isEmpty(name2) ||TextUtils.isEmpty(flight1) || TextUtils.isEmpty(date1) || TextUtils.isEmpty(time1) || TextUtils.isEmpty(ex1))
                {
                    Toast.makeText(getApplicationContext(), "Field empty", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String x=d1.push().getKey();
                    FormDetails1 form1= new FormDetails1(x,name2,ex1,date1,time1,flight1);
                    d1.child(x).setValue(form1);
                    Toast.makeText(getApplicationContext(), "You would be informed soon through your email", Toast.LENGTH_SHORT).show();
                    // startActivity(new Intent(formactivity.this, MainActivity.class));
                    //  finish();
                }
            }
        });
    };

}
