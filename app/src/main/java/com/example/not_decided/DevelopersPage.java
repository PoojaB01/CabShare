package com.example.not_decided;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DevelopersPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers_page);

        TextView alay2=(TextView)findViewById(R.id.text_alay);
        TextView pooja2=(TextView)findViewById(R.id.text_pooja);

        alay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent br=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/alay.shah.5815"));
                startActivity(br);
            }
        });
        pooja2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent br=new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Pooja.bhagat20"));
                startActivity(br);
            }
        });
    }
}
