package com.example.news;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class News_Details extends AppCompatActivity
{

    NewsHeadLines headlines;
    TextView txt_title,txt_author,txt_time,txt_detail,txt_content;
    ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        txt_title=findViewById(R.id.text_detail_title);
        txt_author=findViewById(R.id.text_detail_author);
        txt_time=findViewById(R.id.text_detail_time);
        txt_detail=findViewById(R.id.text_detail_detail);
        txt_content=findViewById(R.id.text_detail_detail_content);
        img_view=findViewById(R.id.img_detail_news);

        headlines = (NewsHeadLines) getIntent().getSerializableExtra("data");

        txt_title.setText(headlines.getTitle());
        txt_author.setText(headlines.getAuthor());
        txt_time.setText(headlines.getPublishedAt());
        txt_detail.setText(headlines.getDescription());
        txt_content.setText(headlines.getContent());

        Picasso.get().load(headlines.getUrlToImage()).into(img_view);
    }
    public  void onBackPressed()
    {
        Intent i =new Intent(News_Details.this,News_Home.class);
        startActivity(i);
        finish();
    }
}