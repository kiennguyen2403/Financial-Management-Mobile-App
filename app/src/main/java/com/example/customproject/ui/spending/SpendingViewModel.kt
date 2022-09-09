package com.example.customproject.ui.spending

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TagController
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
class SpendingViewModel : ViewModel() {
    val transactionController= TransactionController()
    val transaction: MutableLiveData<Transaction> = MutableLiveData()
    val tagController = TagController()
    var taglist:MutableLiveData<List<Tag>> = MutableLiveData()



    fun getallTag():LiveData<List<Tag>>
    {
        var list :MutableList<Tag> = mutableListOf()
        tagController.getAll(TransactionType.Spending).addOnSuccessListener { result ->
            result.documents.forEach {it ->
                val newtag = tagController.Create(it.data?.get("desc") as String,it.data?.get("color") as String,TransactionType.Spending)
                list.add(newtag)
                taglist.value=list
            }
        }

        return taglist
    }
}