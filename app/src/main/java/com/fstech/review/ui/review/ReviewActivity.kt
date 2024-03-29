package com.fstech.review.ui.review

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.fstech.review.R
import com.fstech.review.databinding.ActivityReviewBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ReviewActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            val reviewRef = Firebase.database.reference.child("reviews")
                .child(intent.getStringExtra("userId").toString()).push()
            reviewRef.child("pros").setValue(binding.includedContent.prosEdt.text.trim().toString())
            reviewRef.child("cons").setValue(binding.includedContent.consEdt.text.trim().toString())

            binding.includedContent.prosEdt.text.clear()
            binding.includedContent.consEdt.text.clear()
            Snackbar.make(view, getString(R.string.submited_review), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }


}