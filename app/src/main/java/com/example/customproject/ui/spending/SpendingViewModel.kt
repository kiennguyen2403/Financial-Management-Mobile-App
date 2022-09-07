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
    var taglist:MutableLiveData<List<String>> = MutableLiveData()
    var transactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()


    fun getallTag():LiveData<List<String>>
    {
        var list :MutableList<String> = mutableListOf()
        tagController.getAll(TransactionType.Spending).addOnSuccessListener { result ->
            result.documents.forEach {it ->
                list.add(it.data?.get("desc") as String)
                taglist.value=list
            }
        }

        return taglist
    }


    fun getallTransaction():LiveData<List<Transaction>>
    {
        var list:MutableList<String> = mutableListOf()
        var translist: MutableList<Transaction> = mutableListOf()
        transactionController.getAll(TransactionType.Spending).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { it ->
                transactionController.getSpecific(TransactionType.Spending,it).addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.addSnapshotListener{ value,error ->
                            if (value != null)
                            {
                                var newtransaction = transactionController.Create(TransactionType.Spending,value.data?.get("value") as Int,value.data?.get("desc") as String )
                                translist.add(newtransaction)
                                transactionlist.value = translist
                            }
                        }
                    }
                }
            }
        }
        return transactionlist
    }

}