package com.example.customproject.ui.transactionlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import java.util.*

class TransactionsListViewModel : ViewModel() {
    val transactionController= TransactionController()
    var transactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()

   fun getallTransaction(transactionType: TransactionType,labelName:String): LiveData<List<Transaction>> {

       var translist: MutableList<Transaction> = mutableListOf()
        transactionController.getSpecific(transactionType,labelName.replace("\\s".toRegex(), "")).addOnSuccessListener { it ->
            it.documents.forEach {
                Log.d("200", it.toString())
                it.reference.addSnapshotListener{ value,error ->
                    if(value != null)
                    {   if(value.data?.get("value") != null) {
                        var newtransaction = transactionController.Create(
                            transactionType,
                            value.data?.get("value") as Long,
                            value.data?.get("desc") as String
                        )
                        translist.add(newtransaction)
                        transactionlist.value = translist
                    }
                }
            }
        }
        }
        return transactionlist
    }

}