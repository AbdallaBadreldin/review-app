package com.fstech.review.ui.home


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.fstech.review.R
import com.fstech.review.model.User
import com.fstech.review.ui.review.ReviewActivity
import java.util.ArrayList


class PersonAdapter(private var mList: List<User>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    fun update(usersList: ArrayList<User>) {
        mList=usersList
        this.notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card:CardView = itemView.findViewById(R.id.personCardView)
        val firstName: TextView = itemView.findViewById(R.id.firstname)
        val lastName: TextView = itemView.findViewById(R.id.lastname)
        val age: TextView = itemView.findViewById(R.id.age)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.firstName.text = mList[position].firstName
        holder.lastName.text = mList[position].secondName
        holder.age.text = mList[position].age.toString()
        holder.card.setOnClickListener{
            val intent = Intent(holder.card.context, ReviewActivity::class.java)
            intent.putExtra("userId",mList[position].userId.toString())
            holder.card.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}