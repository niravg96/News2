package com.example.news;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity
{

    private DatabaseHelper database;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Login");
        setContentView(R.layout.activity_login);

         try
         {

             if(checkPermission())
             {
              //   Toast.makeText(getApplicationContext(),"Permission already granted",Toast.LENGTH_LONG).show();

             }
             else
             {
                 requestPermission();
             }
         }
         catch(Exception ex)
         {
             Toast.makeText(getApplicationContext(),"Error 1 : "+ex,Toast.LENGTH_LONG).show();
         }
        initobjects();
        final EditText checkemail = (EditText)findViewById(R.id.checkemail);
        final EditText checkpass = (EditText)findViewById(R.id.checkpassword);
        final TextView link_message = (TextView)findViewById(R.id.link_message);

        link_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent i =new Intent(Login.this,Registration.class);
                startActivity(i);
                finish();

            }
        });

        Button login =(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    if(checkemail.getText().toString().trim().length() > 0 && checkpass.getText().toString().trim().length() > 0)
                    {
                        if(database.checkdata(checkemail.getText().toString(),checkpass.getText().toString()))
                        {
                            Intent i =new Intent(Login.this,News_Home.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                            checkemail.setText("");checkpass.setText("");
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Email Id or Password is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Please Enter Both Credentials", Toast.LENGTH_LONG).show();
                    }
                }
                catch(Exception ex)
                {
                    Toast.makeText(getApplicationContext(), "Error : "+ex, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void initobjects()
    {
        database=new DatabaseHelper(this);
    }
    private boolean checkPermission()
    {
        try
        {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
          //  int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
            int result3 = ContextCompat.checkSelfPermission(getApplicationContext(),INTERNET);

            return result == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Error 2 : "+ex, Toast.LENGTH_LONG).show();
        }
       return true;
    }
    private void requestPermission()
    {
        try
        {
            ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
            ActivityCompat.requestPermissions(this,new String[]{INTERNET},PERMISSION_REQUEST_CODE);
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(), "Error 3 : "+ex, Toast.LENGTH_LONG).show();
        }
    }
    /*public void onRequestPermissionResult(int requestcode,String permissions[],int[] grantResults) {
        try
        {
            switch (requestcode) {
                case PERMISSION_REQUEST_CODE:
                    if (grantResults.length > 0) {
                        boolean write_storage_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                        boolean read_storage_accepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                        boolean internet_accepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                        if (write_storage_accepted && read_storage_accepted && internet_accepted) {
                            Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) {
                                    showMessageOKCancel("You need to allow access to both the permission", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                //requestPermission(new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE,INTERNET},
                                                //        PERMISSION_REQUEST_CODE);
                                            }
                                        }
                                    });
                                    return;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        catch(Exception EX)
        {
            Toast.makeText(getApplicationContext(), "Error 4 : "+EX, Toast.LENGTH_LONG).show();
        }

    }*/
       /* private void showMessageOKCancel(String message,DialogInterface.OnClickListener oklistener)
        {
            new AlertDialog.Builder(Login.this)
                    .setMessage(message)
                    .setPositiveButton("OK",oklistener)
                    .setNegativeButton("Cancel",null)
                    .create()
                    .show();
        }*/
    }

