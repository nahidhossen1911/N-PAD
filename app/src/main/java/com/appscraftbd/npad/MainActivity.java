package com.appscraftbd.npad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RecyclerView recyclerView;
    LinearLayout add_item,not_found,more;

    MaterialToolbar materialToolbar;

    HashMap <String,String> hashMap ;
    ArrayList <HashMap <String,String>>arrayList = new ArrayList();

    private DrawerLayout drawer;
    ImageView addnote;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navgation);
        materialToolbar = findViewById(R.id.meterialToolbar);

        addnote = findViewById(R.id.addnote);
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Add_note.class));

            }
        });






        more = findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawer.open();
            }
        });


        navigationView.setCheckedItem(R.id.home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()==R.id.home){
                    Toast.makeText(MainActivity.this,"home",Toast.LENGTH_SHORT).show();
                }else if (menuItem.getItemId()==R.id.applock){
                    Toast.makeText(MainActivity.this,"apploack",Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });




        getRecycleView();


        add_item = findViewById(R.id.writenote);
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,Add_note.class));


            }
        });

    }

    /////////////////////////////////////
    /////////////////////////////////////////
    public void getRecycleView(){

        recyclerView = findViewById(R.id.recycleview);
        not_found = findViewById(R.id.notfound);



        SQLite sqLite = new SQLite(MainActivity.this);
        Cursor cursor = sqLite.getAllData();

        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String body = cursor.getString(2);
            String date = cursor.getString(3);

            hashMap = new HashMap<>();
            hashMap.put("id",""+id);
            hashMap.put("title",title);
            hashMap.put("body",body);
            hashMap.put("date",date);
            arrayList.add(hashMap);

        }

        Recycle_view recycle_view = new Recycle_view(MainActivity.this,recyclerView,arrayList,hashMap);
        recycle_view.notFound(not_found);

        if (cursor.getCount()<1){

            recyclerView.setVisibility(View.GONE);
            not_found.setVisibility(View.VISIBLE);

        }else {

            not_found.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    public LinearLayout notFound(){

        return not_found;
    }




}