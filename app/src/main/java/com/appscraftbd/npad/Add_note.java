package com.appscraftbd.npad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add_note extends AppCompatActivity {

    EditText editTitle,editBody;
    TextView submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        editTitle = findViewById(R.id.editTitle);
        editBody = findViewById(R.id.editBody);

        editTitle.setHighlightColor(Color.parseColor("#2DB7BFCD"));
        editBody.setHighlightColor(Color.parseColor("#2DB7BFCD"));

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTitle.length()>0 || editBody.length()>0){
                    String title = editTitle.getText().toString();
                    String body = editBody.getText().toString();

                    SQLite sqLite = new SQLite(Add_note.this);
                    sqLite.getInsertData(title,body);

                    Toast.makeText(Add_note.this,"Save",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Add_note.this,MainActivity.class));
                }else {
                    startActivity(new Intent(Add_note.this,MainActivity.class));
                }




            }
        });






    }
}