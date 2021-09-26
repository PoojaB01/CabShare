package com.example.not_decided;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.os.Bundle;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, inputName, inputPhone, inputDepartment, confirmPassword;
    private Button SUbutton, LIbutton;
    private FirebaseAuth auth;
    private static final String[] hostels = {"Dhansiri", "Siang", "Lohit", "Kameng"};
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;


    private Spinner hostel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();

        hostel = (Spinner) findViewById(R.id.hostel);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, hostels);
        hostel.setAdapter(adapter);


        SUbutton = (Button) findViewById(R.id.signup_submit);
        LIbutton = (Button) findViewById(R.id.login_submit);
        inputEmail = (EditText) findViewById(R.id.email_input);
        inputName = (EditText) findViewById(R.id.name_input);
        inputPhone = (EditText) findViewById(R.id.phone_input);
        inputPassword = (EditText) findViewById(R.id.password_input);
        inputDepartment = (EditText) findViewById(R.id.input_department);
        confirmPassword = (EditText) findViewById(R.id.confirm_password);

        firebaseAuth = FirebaseAuth.getInstance();


        SUbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                String c_password = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(c_password)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String name = inputName.getText().toString().trim();
                            String phone = inputPhone.getText().toString().trim();
                            String department = inputDepartment.getText().toString().trim();
                            String hostel_name = hostel.getSelectedItem().toString();
                            String email1 = inputEmail.getText().toString().trim().replace('.', '-');

                            User_information userinfo = new User_information(name, phone, hostel_name, department, email);


                            databaseReference = FirebaseDatabase.getInstance().getReference();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            firebaseStorage = FirebaseStorage.getInstance();
                            storageReference = firebaseStorage.getReference();
                            databaseReference = databaseReference.child("Users");
                            databaseReference.child(user.getUid()).setValue(userinfo);
                            databaseReference = FirebaseDatabase.getInstance().getReference().child("UserID");
                            databaseReference.child(email1).setValue(user.getUid());

                            startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

        LIbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
