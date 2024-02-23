package com.appscraftbd.npad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;

public class Recycle_view {

    Context context;
    RecyclerView recyclerView;

    LinearLayout not_found;
    HashMap<String,String> hashMap ;
    ArrayList<HashMap <String,String>> arrayList ;
    public Recycle_view(Context context, RecyclerView recyclerView, ArrayList arrayList , HashMap hashMap){

        this.recyclerView=recyclerView;
        this.arrayList=arrayList;
        this.hashMap = hashMap;
        this.context=context;
        MyAdapter myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));



    }
    public class MyAdapter extends RecyclerView.Adapter{


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false);

            return new Myviewholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Myviewholder myviewholder = (Myviewholder) holder;

            hashMap = arrayList.get(position);

            String sid = hashMap.get("id");
            String stitle = ""+hashMap.get("title");
            String sbody = ""+hashMap.get("body");
            String sdate = ""+hashMap.get("date");

            myviewholder.Title.setText(""+stitle);
            myviewholder.Body.setText(""+sbody);
            myviewholder.date.setText(""+sdate);

            myviewholder.gothere.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Show_text.id=""+sid;
                    Show_text.s_title = ""+stitle;
                    Show_text.s_body = ""+sbody;

                    Intent intent = new Intent(context, Show_text.class);
                    // extras
                    context.startActivity(intent);

                }
            });

            myviewholder.gothere.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    /////////////////////////////////////////
                    BottomSheetDialog bottomSheetDialog;

                    bottomSheetDialog = new BottomSheetDialog(context);
                    View view1 = LayoutInflater.from(context).inflate(R.layout.modefiy,
                            null);
                    bottomSheetDialog.setContentView(view1);

                    bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    bottomSheetDialog.setCancelable(false);
                    bottomSheetDialog.show();

                    TextView title = bottomSheetDialog.findViewById(R.id.title);
                    TextView body = bottomSheetDialog.findViewById(R.id.body);
                    TextView delete = bottomSheetDialog.findViewById(R.id.delete);
                    TextView cancel = bottomSheetDialog.findViewById(R.id.cancel);

                    EditText confirm_delete = bottomSheetDialog.findViewById(R.id.confirm_delete);
                    confirm_delete.setHighlightColor(Color.parseColor("#2DB7BFCD"));


                    title.setText(""+stitle);
                    body.setText(""+sbody);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            bottomSheetDialog.dismiss();
                        }
                    });

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(confirm_delete.length()>0){
                                String yes = confirm_delete.getText().toString();

                                if(yes.equals("yes")){
                                    SQLite sqLite = new SQLite(context);
                                    sqLite.dataDelete(sid);
                                    Cursor cursor = sqLite.getAllData();


                                    try{

                                        arrayList.remove(position);
                                        notifyItemRemoved(position);
                                        bottomSheetDialog.dismiss();

                                    }catch (Exception e){

                                        bottomSheetDialog.dismiss();


                                    }

                                    if (cursor.getCount()<1){
                                        recyclerView.setVisibility(View.GONE);
                                        not_found.setVisibility(View.VISIBLE);
                                    }else {
                                        not_found.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                    }


                                }else {
                                    confirm_delete.setError("No Match");
                                }


                            }else {
                                confirm_delete.setError("Type 'yes'");
                            }





                        }
                    });








                    ////////////////////////////////////////////////


//                    setBottomSheetDialog(id,title,body,position);


                    return false;
                }
            });




        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        public class Myviewholder extends RecyclerView.ViewHolder{

            TextView Title,Body,date;
            LinearLayout gothere;
            public Myviewholder(@NonNull View itemView) {
                super(itemView);

                Title = itemView.findViewById(R.id.title);
                Body = itemView.findViewById(R.id.body);
                date = itemView.findViewById(R.id.date);
                gothere = itemView.findViewById(R.id.goThere);



            }
        }
    }

    public void notFound(LinearLayout not_founr){
        this.not_found=not_founr;

    }


}
