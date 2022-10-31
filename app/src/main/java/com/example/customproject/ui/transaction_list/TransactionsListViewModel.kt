package com.example.customproject.ui.transaction_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType

class TransactionsListViewModel : ViewModel() {
    private val transactionController= TransactionController()
    var transactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()

   fun getallTransaction(transactionType: TransactionType,labelName:String): LiveData<List<Transaction>> {
       val translist: MutableList<Transaction> = mutableListOf()
        transactionController.getSpecific(transactionType,labelName).addOnSuccessListener { it ->
            it.documents.forEach {
                it.reference.addSnapshotListener{ value, _ ->
                    if(value != null)
                    {
                        if(value.data?.get("value") != null) {
                        val newtransaction = transactionController.Create(
                            value.data?.get("value") as Long,
                            value.data?.get("desc") as String,
                            value.id
                        )
                        translist.add(newtransaction)

                    }
                }
                    transactionlist.value = translist
                }
             }
        }
        return transactionlist
    }

}