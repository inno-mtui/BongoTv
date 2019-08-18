package com.example.newstv

import android.app.Activity
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class PostActivity : AppCompatActivity() {
    private var mSubmitBtn: Button? = null
    private var mSelectImage: ImageButton? = null
    private var mPostTitle: EditText? = null
    private var mPostDesc: EditText? = null
    private var mImageUri: Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabase: DatabaseReference? = null
   // private var mprogressbar: ProgressDialog? = null
    private var mCurrentUser: FirebaseUser? = null
    private var mDatabaseUSer: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val mAuth = FirebaseAuth.getInstance()
        mCurrentUser = mAuth.currentUser
        mDatabaseUSer = FirebaseDatabase.getInstance().reference.child("Users").child(mCurrentUser!!.uid)

        bindViews()
        clickEvents()

    }

    private fun bindViews() {
       // mprogressbar = ProgressDialog(this)
        mDatabase = FirebaseDatabase.getInstance().reference.child("Blog")
        mStorageRef = FirebaseStorage.getInstance().reference
        mPostTitle = findViewById(R.id.editText1)
        mPostDesc = findViewById(R.id.editText2)
        mSubmitBtn = findViewById(R.id.btn)
        mSelectImage = findViewById(R.id.imageButton2)
    }

    private fun clickEvents() {
        mSelectImage!!.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"

            startActivityForResult(galleryIntent, GALLERY_REQUEST)
        }

        mSubmitBtn!!.setOnClickListener { startPosting() }

    }

    private fun startPosting() {

        //mprogressbar!!.setMessage("Posting...")

        val title_val = mPostTitle!!.text.toString().trim { it <= ' ' }
        val desc_val = mPostDesc!!.text.toString().trim { it <= ' ' }

        if (!TextUtils.isEmpty(title_val) && !TextUtils.isEmpty(desc_val) && mImageUri != null) {
            //can post
            // mImageUri= Uri.fromFile(new File(mImageUri.getLastPathSegment()));
            //mprogressbar!!.show()
            val filepath = mStorageRef!!.child("Blog_Images").child(mImageUri!!.lastPathSegment!!)
            filepath.putFile(mImageUri!!).addOnSuccessListener { taskSnapshot ->
                val downloadUrl = taskSnapshot.uploadSessionUri
                val newPost = mDatabase!!.push()//cret uniquid
                mDatabaseUSer!!.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        newPost.child("Title").setValue(title_val)
                        newPost.child("DESCRIPTION").setValue(desc_val)
                        assert(downloadUrl != null)
                        newPost.child("IMAGE").setValue(downloadUrl!!.toString())
                        newPost.child("uid").setValue(mCurrentUser!!.uid)
                        newPost.child("username").setValue(dataSnapshot.child("name").value).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this@PostActivity, MainActivity::class.java))

                            }
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {

                    }
                })

              //  mprogressbar!!.dismiss()
                Toast.makeText(this@PostActivity, "Posted Successfully!!!!!",
                        Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@PostActivity, "Unabel to post Please TRY AGAIN!!!!",
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            mImageUri = data!!.data
            mSelectImage!!.setImageURI(mImageUri)
        }
    }

    companion object {
        private const val GALLERY_REQUEST = 999
    }
}
