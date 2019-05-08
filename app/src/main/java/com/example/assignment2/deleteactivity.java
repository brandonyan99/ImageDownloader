package com.example.assignment2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class deleteactivity extends AppCompatActivity {
    MyDB db;
    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteactivity);
        db = new MyDB(this, MyDB.DBname, null, 1);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

    }

    public void ok(View view) {
        int a;
        if(!(editText1.getText().toString().equals(""))){ // if id was entered
            a = Integer.parseInt(editText1.getText().toString());
            int delete = db.deleteByID(a);
            if(delete>0){
                Toast.makeText(this, "Photo deleted", Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                Toast.makeText(this, "ID not in database", Toast.LENGTH_LONG).show();
            }
        }

        if(!(editText2.getText().toString().equals(""))){
            int delete = db.deleteByTitle(editText2.getText().toString());
            if(delete>0){
                Toast.makeText(this, "Photo deleted", Toast.LENGTH_LONG).show();
                finish();
            }
            else{
                Toast.makeText(this, "Title not in database", Toast.LENGTH_LONG).show();
            }
        }

        if(editText1.getText().toString().equals("") && editText2.getText().toString().equals("")){
            Toast.makeText(this, "Enter an ID or title", Toast.LENGTH_LONG).show();
        }


    }
}
