package com.example.customproject.ui.transaction_list
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.customproject.R
import com.example.customproject.model.Transaction
import java.util.*

class CustomAdapter(mList: List<Transaction>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views

    var Translist:List<Transaction> = mList
    @SuppressLint("NotifyDataSetChanged")
    fun filterList(text:String){
        val newlist:MutableList<Transaction>  = mutableListOf()
        Translist.forEach {
            if (it.desc.lowercase(Locale.getDefault()).contains(text.lowercase()))
            {
                newlist.add(it)
            }
        }
        Translist= newlist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design_transaction, parent, false)
        view.setOnClickListener {

        }
        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val transaction = Translist[position]

        // sets the image to the imageview from our itemHolder class
        // sets the text to the textview from our itemHolder class
        holder.desc.text = transaction.desc
        holder.value.text = transaction.value.toString()+"$"
        holder.date.text = transaction.date.toDate().toString()
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return Translist.size
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val desc: TextView = itemView.findViewById(R.id.desc)
        val value: TextView = itemView.findViewById(R.id.value)
        val date: TextView = itemView.findViewById(R.id.date)
    }
}