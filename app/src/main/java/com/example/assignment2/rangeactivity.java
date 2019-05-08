package com.example.assignment2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class rangeactivity extends AppCompatActivity {
    MyDB db;
    EditText editText1;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rangeactivity);
        db = new MyDB(this, MyDB.DBname, null, 1);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

    }

    public void display(View view) {
        if(!(editText1.getText().toString().equals(""))&&!(editText2.getText().toString().equals(""))) {
            int a = Integer.parseInt(editText1.getText().toString());
            int b = Integer.parseInt(editText2.getText().toString());
            int size = db.countRows();
            if (a <= 0 || b > size || a > b) {
                Toast.makeText(this, "Range is not valid", Toast.LENGTH_LONG).show();
            }
            else{
                Intent intent = new Intent(this, viewrange.class);
                Bundle extras = new Bundle();
                extras.putInt("min",a);
                extras.putInt("max",b);
                intent.putExtras(extras);
                startActivity(intent);
                finish();
            }
        }
        else{
            Toast.makeText(this, "Range is not valid", Toast.LENGTH_LONG).show();
        }
    }
}
