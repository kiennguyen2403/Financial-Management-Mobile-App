package com.example.customproject.ui.transactionlist
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.model.Transaction
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlin.math.log

class CustomAdapter(private val mList: List<Transaction>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design_transaction, parent, false)
       view.setOnClickListener{

       }
        val viewHolder= ViewHolder(view)
        return viewHolder
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val transaction = mList[position]

        // sets the image to the imageview from our itemHolder class
        // sets the text to the textview from our itemHolder class
        holder.desc.text = transaction.desc
        holder.value.text = transaction.value.toString()+"$"
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val desc: TextView = itemView.findViewById(R.id.desc)
        val value: TextView = itemView.findViewById(R.id.value)
    }
}