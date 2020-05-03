package com.example.not_decided;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.not_decided.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchAct extends AppCompatActivity {
private RecyclerView x;
private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//        ref= FirebaseDatabase.getInstance().getReference().child("Users");
//        ref.keepSynced(true);
//        x=(RecyclerView)findViewById(R.id.myrecycleview);
//        x.setHasFixedSize(true);
//        x.setLayoutManager(new LinearLayoutManager(this));

    }
//    @Override
////    protected void onStart()
////    {
////        super.onStart();
////        FirebaseRecyclerAdapter<SearchAct,BlogViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<SearchAct, BlogViewHolder>
////                (SearchAct.class,R.layout.activity_cardview,BlogViewHolder.class,ref) {
////            @Override
////            protected void populateViewHolder(BlogViewHolder blogViewHolder, SearchAct blog, int i) {
////
////            }
////        };
////
////    }
////    public static class BlogViewHolder extends RecyclerView.ViewHolder{
////        //View mView;
////    }

}
