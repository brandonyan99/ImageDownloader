package com.example.assignment2;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class viewactivity extends AppCompatActivity {
    MyDB db;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Bitmap> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewactivity);
        db = new MyDB(this,MyDB.DBname, null, 1);
        Cursor c = db.getAllRows();
        while(c.moveToNext()){
            byte[] b = c.getBlob(c.getColumnIndex("PICTURE"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0, b.length);
            titles.add(c.getString(c.getColumnIndex("TITLE")));
            images.add(bitmap);

        }
        RecyclerView recyclerView = findViewById(R.id.imageRecyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titles, images, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
