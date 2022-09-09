package com.example.customproject.ui.transactionlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType

class TransactionsListViewModel : ViewModel() {
    val transactionController= TransactionController()
    var transactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()

    fun getallTransaction(transactionType: TransactionType): LiveData<List<Transaction>> {
        var list:MutableList<String> = mutableListOf()
        var translist: MutableList<Transaction> = mutableListOf()
        transactionController.getAll(transactionType).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { item ->
                transactionController.getSpecific(transactionType,item).addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.addSnapshotListener{ value,error ->
                            if (value != null)
                            {
                                if(value.data?.get("value") != null) {
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
            }
        }
        return transactionlist
    }

}