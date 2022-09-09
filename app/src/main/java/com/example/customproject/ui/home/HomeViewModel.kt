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
    var total: MutableLiveData<Int> = MutableLiveData(0)
    var incometransactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()
    var spendingtransactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()



    fun getTotal():LiveData<Int>{
        total.value=0
        var list :MutableList<String> = mutableListOf()
        transactionController.getAll(TransactionType.Income).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { it ->
                transactionController.getSpecific(TransactionType.Income,it).addOnSuccessListener{
                    it.documents.forEach {
                        it.reference.addSnapshotListener{value,error->
                            if (value != null)
                            {
                                if (value.data?.get("value") != null)
                                {
                                    total.value = total.value?.plus(value.data?.get("value").toString().toInt())
                                }
                            }
                        }
                    }
                }
            }
        }.addOnFailureListener {
            Log.d("404",it.message.toString())
        }

        transactionController.getAll(TransactionType.Spending).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { it ->
                transactionController.getSpecific(TransactionType.Spending,it).addOnSuccessListener{
                    it.documents.forEach {
                        it.reference.addSnapshotListener{value,error->
                            if (value != null)
                            {
                                if (value.data?.get("value") != null) {
                                    total.value = total.value?.minus(
                                        value.data?.get("value").toString().toInt()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }.addOnFailureListener {
            Log.d("404",it.message.toString())
        }
        return total
    }
    fun getallIncomeData():LiveData<List<Transaction>>{
        var list:MutableList<String> = mutableListOf()
        var translist: MutableList<Transaction> = mutableListOf()
        transactionController.getAll(TransactionType.Income).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { item ->
                transactionController.getSpecific(TransactionType.Income,item).addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.addSnapshotListener{ value,error ->
                            if (value != null)
                            {
                                if(value.data?.get("value") != null) {
                                    var newtransaction = transactionController.Create(
                                        TransactionType.Income,
                                        value.data?.get("value") as Long,
                                        value.data?.get("desc") as String
                                    )
                                    translist.add(newtransaction)
                                    incometransactionlist.value = translist
                                }
                            }
                        }
                    }
                }
            }
        }
        return incometransactionlist
    }
    fun getallSpendingData():LiveData<List<Transaction>>{
        var list:MutableList<String> = mutableListOf()
        var translist: MutableList<Transaction> = mutableListOf()
        transactionController.getAll(TransactionType.Spending).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { item ->
                transactionController.getSpecific(TransactionType.Spending,item).addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.addSnapshotListener{ value,error ->
                            if (value != null)
                            {
                                var newtransaction = transactionController.Create(TransactionType.Spending,value.data?.get("value") as Long,value.data?.get("desc") as String )
                                translist.add(newtransaction)
                                spendingtransactionlist.value = translist

                            }
                        }
                    }
                }
            }
        }
        return spendingtransactionlist
    }

}

