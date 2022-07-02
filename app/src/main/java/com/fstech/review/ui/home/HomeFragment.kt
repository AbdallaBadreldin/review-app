package com.fstech.review.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fstech.review.databinding.FragmentHomeBinding
import com.fstech.review.model.User
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

    private var usersList: ArrayList<User> = ArrayList()
    private lateinit var adapter: PersonAdapter
    private lateinit var recycler: RecyclerView
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


        adapter = PersonAdapter(mList = usersList)
        recycler = binding.usersList

        adapter.also {
            recycler.layoutManager = LinearLayoutManager(requireContext())
            recycler.adapter = it }


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
                val userId= snapshot.key
                val firstName = snapshot.child("firstName").value
                val position = snapshot.child("position").value
                val profileImage = snapshot.child("profileImage").value
                val age = snapshot.child("age").value
                val email = snapshot.child("email").value
                val secondName = snapshot.child("secondName").value

                val user = User(
                    userId =userId ,
                    firstName = firstName as String,
                    position = position as String,
                    profileImage = profileImage as String,
                    age = age as Long,
                    email = email as String,
                    secondName = secondName as String
                )
                usersList.add(user)
                adapter.update(usersList)
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