package com.example.lipin.javaee_servlet;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


//讓全愈能共享Volley,目的不用在每個activity都實作出RequestQueue queue;
public class MainApp extends Application {
    public static RequestQueue queue;

    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(this);
    }
}
