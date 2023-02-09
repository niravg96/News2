package com.example.news;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Registration extends AppCompatActivity {

    DatabaseHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    EditText name,email, pass, phone, college;
    Button submit;
    TextView link_message_of_login,dob;

    int STORAGE_PERMISSION_CODE = 1;

    private DatePicker datepicker;
    private Calendar calander;
    private int year,month,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Registration");
        setContentView(R.layout.activity_registration);

        sqLiteHelper = new DatabaseHelper(this);

        name = (EditText) findViewById(R.id.name);
        dob = (TextView) findViewById(R.id.dateofbirth);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        college = (EditText) findViewById(R.id.college);

        calander =Calendar.getInstance();
        year = calander.get(Calendar.YEAR);
        month = calander.get(Calendar.MONTH);
        day = calander.get(Calendar.DAY_OF_MONTH);
       // showDate(year,month+1,day);


        link_message_of_login = (TextView) findViewById(R.id.link_message_of_login);
        link_message_of_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
                finish();
            }
        });
        submit = (Button) findViewById(R.id.register);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (name.getText().toString().trim().length() > 0 && dob.getText().toString().trim().length() > 0 && email.getText().toString().trim().length() > 0 && pass.getText().toString().trim().length() > 0 && phone.getText().toString().trim().length() > 0 && college.getText().toString().trim().length() > 0) {
                        try {
                            boolean isInserted = sqLiteHelper.insertData(name.getText().toString(), dob.getText().toString(), email.getText().toString(), pass.getText().toString(), phone.getText().toString(), college.getText().toString());

                            if (isInserted == true) {
                                Intent i = new Intent(Registration.this, Login.class);
                                startActivity(i);
                                name.setText("");
                                dob.setText("Enter a Date of Birth");
                                email.setText("");
                                pass.setText("");
                                phone.setText("");
                                college.setText("");
                                Toast.makeText(getApplicationContext(), "Registration Successfully Done , Do Login Now", Toast.LENGTH_LONG).show();
                                finish();
                            } else
                            {
                                Toast.makeText(getApplicationContext(), "Not Registered", Toast.LENGTH_LONG).show();
                              //  Toast.makeText(getApplicationContext(), "Android OS Latest version is not supported, Android OS 8 is supported.", Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), "Details Not Inserted : " + ex, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter All Details", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error : " + e, Toast.LENGTH_LONG).show();
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              //  showDate(year,month+1,day);
                showDialog(999);
              //  Toast.makeText(getApplicationContext(),"ca",Toast.LENGTH_LONG).show();
            }
        });
    }
    protected Dialog onCreateDialog(int id){
        if(id == 999)
        {
            return new DatePickerDialog(this,myDateListener,year,month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

            showDate(year,month+1,dayOfMonth);

        }
    };
    private void showDate(int year,int month,int day)
    {
        dob.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
    }
}