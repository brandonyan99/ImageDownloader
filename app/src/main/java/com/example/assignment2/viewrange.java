package com.example.assignment2;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class viewrange extends AppCompatActivity {

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<Bitmap> images = new ArrayList<>();
    MyDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewrange);
        db = new MyDB(this, MyDB.DBname, null, 1);

        Bundle extras = getIntent().getExtras();
        int a = extras.getInt("min");
        int b = extras.getInt("max");

        Cursor c = db.getRowsRange(a,b);
        while(c.moveToNext()){
            byte[] bytes = c.getBlob(c.getColumnIndex("PICTURE"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0, bytes.length);
            titles.add(c.getString(c.getColumnIndex("TITLE")));
            images.add(bitmap);
        }
        RecyclerView recyclerView = findViewById(R.id.imageRecyclerView2);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(titles, images, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
