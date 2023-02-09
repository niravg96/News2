package com.example.news;

import com.example.news.NewsHeadLines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeadLines> list, String message);
    void onError(String message);
}
