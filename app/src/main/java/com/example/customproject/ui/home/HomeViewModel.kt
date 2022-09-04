package com.example.customproject.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import java.util.*

class HomeViewModel : ViewModel() {
    val transactionController=TransactionController()
    var totalSpending: MutableLiveData<Int> = MutableLiveData(0)
    var transactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()



    fun GetTotal():LiveData<Int>{
        totalSpending.value=0
        transactionController.getAll(TransactionType.Income).addOnSuccessListener { result ->
            result.documents.forEach { item ->
                item.reference.addSnapshotListener(
                    EventListener<DocumentSnapshot>{value,error->
                        if (value!= null)
                        {
                            totalSpending.value = totalSpending.value?.plus(value.data?.get("value").toString().toInt())
                        }
                    }
                )
            }
        }.addOnFailureListener {
            totalSpending.value=0
        }
        transactionController.getAll(TransactionType.Spending).addOnSuccessListener { result ->
            result.documents.forEach { item ->
                item.reference.addSnapshotListener(
                    EventListener<DocumentSnapshot>{value,error->
                        if (value!= null)
                        {
                            totalSpending.value = totalSpending.value?.minus(value.data?.get("value").toString().toInt())
                        }
                    }
                )
            }
        }.addOnFailureListener {
            totalSpending.value=0
        }
        return totalSpending
    }
    fun GetallData():LiveData<List<Transaction>>{
        var list:MutableList<Transaction> = mutableListOf()
        transactionController.getAll(TransactionType.Income).addOnSuccessListener { result ->
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

