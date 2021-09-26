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

        TextView dev_alay = (TextView) findViewById(R.id.text_alay);
        TextView dev_pooja = (TextView) findViewById(R.id.text_pooja);

        dev_alay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent br = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/alay-shah007/"));
                startActivity(br);
            }
        });
        dev_pooja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent br = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/poojabhagat/"));
                startActivity(br);
            }
        });
    }
}
