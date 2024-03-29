package com.example.customproject.ui.notifications
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.model.Notification
import java.sql.Timestamp

class CustomAdapter(private val mList: List<Notification>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    var onItemClick: (()->Unit)? = null
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design_notification, parent, false)
        view.setOnClickListener{

        }
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val notification= mList[position]

        // sets the image to the imageview from our itemHolder class
        // sets the text to the textview from our itemHolder class
        holder.desc.text = notification.desc
        holder.date.text = notification.date.toDate().toString()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val desc: TextView = itemView.findViewById(R.id.desc)
        val date: TextView = itemView.findViewById(R.id.date)
    }
}