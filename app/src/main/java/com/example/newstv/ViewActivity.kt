package com.example.newstv


import android.os.Bundle

import com.bumptech.glide.load.model.Model
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


import android.view.LayoutInflater
import android.view.View
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle

import android.view.MenuItem

import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

import androidx.drawerlayout.widget.DrawerLayout

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import android.view.Menu
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class ViewActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var recyclerView: RecyclerView? = null
    private var databaseReference: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Blog")
        databaseReference!!.keepSynced(true)


        recyclerView = findViewById(R.id.news_recycler_view)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onStart() {
        super.onStart()
        val options = FirebaseRecyclerOptions.Builder<Blog>()
                .setQuery(databaseReference!!, Blog::class.java)
                .build()
        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<Blog, ViewHolder>(options) {
            override fun onBindViewHolder(viewHolder: ViewHolder, i: Int, blog: Blog) {
                viewHolder.descView.text = blog.description
                viewHolder.titleView.text = blog.title
                Picasso.get().load(blog.image).into(viewHolder.imageView)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rows, parent, false)
                return ViewHolder(view)

            }
        }

        recyclerView!!.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    inner class ViewHolder internal constructor( view: View) : RecyclerView.ViewHolder(view) {
        internal var descView: TextView
        internal var titleView: TextView
        internal var imageView: ImageButton

        init {
            descView = view.findViewById(R.id.post_description)
            titleView = view.findViewById(R.id.post_title)
            imageView = view.findViewById(R.id.view_image)

        }


        /*  void setDesc(String DESCRIPTION) {
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
        */
    }
}
