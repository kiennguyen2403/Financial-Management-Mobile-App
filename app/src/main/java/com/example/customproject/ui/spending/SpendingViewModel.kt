package com.example.customproject.ui.spending

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
class SpendingViewModel : ViewModel() {
    val transactionController= TransactionController()
    val transaction: MutableLiveData<String> = MutableLiveData()
    var transactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()

    fun GetallData():LiveData<List<Transaction>>{
        var list:MutableList<Transaction> = mutableListOf()
        transactionController.getAll(TransactionType.Spending).addOnSuccessListener { result ->
            result.documents.forEach { item->
                item.reference.addSnapshotListener(
                    EventListener<DocumentSnapshot> { value, error ->
                        if (value != null) {
                            val tag = Tag("Eating")
                            val newtransaction = transactionController.Create(TransactionType.Income,
                                value.data?.get("value").toString().toInt(),
                                value.data?.get("desc").toString(),
                                tag
                            )
                            list.add(newtransaction)
                            transactionlist.value=list
                        }
                    }
                )
            }
        }
        return transactionlist
    }

}