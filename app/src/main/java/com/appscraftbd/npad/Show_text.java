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

public class Show_text extends AppCompatActivity {

    EditText editTitle,editBody;
    TextView submit;

    public static String s_title ="";
    public static String s_body ="";
    public static String id = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_text);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        editTitle = findViewById(R.id.editTitle);
        editBody = findViewById(R.id.editBody);

        editTitle.setHighlightColor(Color.parseColor("#2DB7BFCD"));
        editBody.setHighlightColor(Color.parseColor("#2DB7BFCD"));

        submit = findViewById(R.id.submit);

        editTitle.setText(""+s_title);
        editBody.setText(""+s_body);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTitle.getText().toString();
                String body = editBody.getText().toString();

                SQLite sqLite = new SQLite(Show_text.this);
                sqLite.dataDelete(id);
                sqLite.getInsertData(title,body);

                startActivity(new Intent(Show_text.this,MainActivity.class));

            }
        });




    }
}