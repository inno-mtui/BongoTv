package com.example.newstv

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class RecylerActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyler)
        databaseReference = FirebaseDatabase.getInstance().reference.child("Blog")
        databaseReference!!.keepSynced(true)

        recyclerView = findViewById(R.id.news_recycler_view)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    /*  @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog,ViewHolder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Blog, ViewHolder>
                (Blog.class,R.layout.rows,ViewHolder.class,databaseReference) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Blog blog) {

            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
        void setDesc(String DESCRIPTION) {
            TextView descView = mView.findViewById(R.id.post_desc);
            descView.setText(DESCRIPTION);

        }

        void setTitle(String Title) {
            TextView titleView = mView.findViewById(R.id.post_title);
            titleView.setText(Title);

        }

        void setImage(String IMAGE) {
            ImageView imageView = mView.findViewById(R.id.view_image);
            Picasso.get().load(IMAGE).into(imageView);

        }
    }*/
}


