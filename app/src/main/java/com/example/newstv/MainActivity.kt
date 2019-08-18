package com.example.newstv

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem

import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

     private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_login -> startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            R.id.menu_post -> {
                firebaseAuth.signOut()
                finish()
                startActivity(Intent(this@MainActivity, PostActivity::class.java))
            }
            R.id.post_news -> {
                //firebaseAuth.signOut();
                finish()
                startActivity(Intent(this@MainActivity, ViewActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
