package com.example.assignment2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class downloadactivity extends AppCompatActivity {
    static MyDB db;
    static EditText editText1;
    static EditText editText2;
    static Bitmap bitmap;
    static ConnectivityManager connectivityManager;
    static NetworkInfo networkInfo;
    String s1;
    static String s2;
    static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloadactivity);
        db = new MyDB(this, MyDB.DBname, null, 1);
        editText1 = findViewById(R.id.urltext);
        editText2 = findViewById(R.id.titletext);

    }
    public void insert(View view){
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){

            s1 = editText1.getText().toString();
            s2 = editText2.getText().toString();
            url = s1;
            //url = "https://coarchitects.com/wp-content/uploads/2015/05/UCSC-Engineering-09.jpg";
            ImageDownloader imageDownloader = new ImageDownloader();
            imageDownloader.execute(url);
            Toast.makeText(downloadactivity.this, "Download successful", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(downloadactivity.this, "No internet connection", Toast.LENGTH_LONG).show();
        }

    }

    //public static void
    public static class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(500);
                con.setReadTimeout(500);
                con.setRequestMethod("GET");
                con.connect();

                InputStream is = con.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            db.insert(bitmap, editText2.getText().toString()); // edittext.gettext.tostring
            //MainActivity.imageView.setImageBitmap(bitmap);
        }
    }
}
