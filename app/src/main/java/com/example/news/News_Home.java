package com.example.news;

import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.news.NewsApiResponse;
import com.example.news.NewsHeadLines;

import java.util.List;

public class News_Home extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button retrybutton;
    private int count=0;

    private static final int PERMISSION_REQUEST_CODE2 = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_home);

        retrybutton =findViewById(R.id.RetryInternet);
        retrybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isconnected())
                {
                    count = 0;
                    Toast.makeText(getApplicationContext(),"Please Turn On Internet",Toast.LENGTH_LONG).show();
                }
                else if(count >= 1)
                {
                    Toast.makeText(getApplicationContext(),"Internet is Already on",Toast.LENGTH_LONG).show();
                }
                else if(isconnected())
                {
                    ++count;
                 //   dialog = new ProgressDialog(getApplicationContext());
                 //   dialog.setTitle("Fetching news articles...");
                 //   dialog.show();

                    RequestManager manager =new RequestManager(getApplicationContext());
                    manager.getNewsHeadlines(listener,"general",null);
                }
                else
                {
                    count = 0;
                    Toast.makeText(getApplicationContext(),"Please Turn on Your Internet",Toast.LENGTH_LONG).show();
                }
            }
        });
        try
        {
            if(isconnected())
            {
                ++count;
                dialog = new ProgressDialog(this);
                dialog.setTitle("Fetching news articles...");
                dialog.show();

                RequestManager manager =new RequestManager(this);
                manager.getNewsHeadlines(listener,"general",null);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"You are Offline. Can not read news",Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error : "+ex,Toast.LENGTH_LONG).show();
        }


    }
    private Boolean isconnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() !=null && cm.getActiveNetworkInfo().isConnected();
    }
    private final OnFetchDataListener<NewsApiResponse> listener =new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadLines> list, String message)
        {
            try
            {
                showNews(list);
                dialog.dismiss();
            }
            catch(Exception ex)
            {
                Toast.makeText(getApplicationContext(),"Error 2 : "+ex,Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadLines> list) {

        try
        {
            recyclerView = findViewById(R.id.recycler_main);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager((new GridLayoutManager(this,1)));
            adapter = new CustomAdapter(this,list,this);
            recyclerView.setAdapter(adapter);
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error 3 : "+ex,Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void OnNewsClicked(NewsHeadLines headLines) {
        try
        {
            startActivity(new Intent(News_Home.this,News_Details.class).putExtra("data",headLines));
            finish();
        }
        catch(Exception ex)
        {
            Toast.makeText(getApplicationContext(),"Error 4 : "+ex,Toast.LENGTH_LONG).show();
        }
    }
    public  void onBackPressed()
    {
        new AlertDialog.Builder(this).setTitle("Logout")
                .setMessage("Are you sure you want to Logout ?")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                        Intent i =new Intent(News_Home.this,Login.class);
                        startActivity(i);

                    }
                }).setNegativeButton("No",null).show();
    }
}