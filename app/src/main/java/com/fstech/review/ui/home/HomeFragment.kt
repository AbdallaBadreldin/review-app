package com.fstech.review.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


import com.fstech.review.databinding.FragmentHomeBinding
import com.fstech.review.model.User
import com.fstech.review.model.UserResponse
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference

    //    private var response: ArrayList<UserResponse> = ArrayList()
    private var usersList: ArrayList<UserResponse> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        database = Firebase.database.reference


        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val database = Firebase.database
        val myRef = database.getReference("users")

//        val myUserId = uid
//        val myTopPostsQuery = databaseReference.child("user-posts").child(myUserId)
//            .orderByChild("starCount")


        val myMostViewedPostsQuery = myRef.orderByChild("metrics/views").limitToLast(20)
        myMostViewedPostsQuery.addChildEventListener(object : ChildEventListener {
            // TODO: implement the ChildEventListener methods as documented above
            // ...
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val firstName = snapshot.child("firstName").value
                val position = snapshot.child("position").value
                val profileImage = snapshot.child("profileImage").value
                val age = snapshot.child("age").value
                val email = snapshot.child("email").value
                val secondName = snapshot.child("secondName").value
                Log.v("TAG", firstName.toString())
                Log.v("TAG", position.toString())
                Log.v("TAG", profileImage.toString())
                Log.v("TAG", age.toString())
                Log.v("TAG", email.toString())
                Log.v("TAG", secondName.toString())



            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                //TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                //TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                //TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}