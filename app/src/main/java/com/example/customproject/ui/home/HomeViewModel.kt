package com.example.customproject.ui.home

import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.TransactionType

class HomeViewModel : ViewModel() {
    private val transactionController=TransactionController()
    private var total: MutableLiveData<Int> = MutableLiveData(0)
    var totalincome:MutableLiveData<Int> = MutableLiveData(0)
    var totalspending:MutableLiveData<Int> = MutableLiveData(0)
    private var totalincometransactionlist: MutableLiveData<List<Map<String,Long>>> = MutableLiveData()
    private var totalspendingtransactionlist: MutableLiveData<List<Map<String,Long>>> = MutableLiveData()



    fun getTotal(loading:ProgressBar):LiveData<Int>{
        total.value=0
        totalincome.value=0
        totalspending.value=0
        var list :MutableList<String>
        transactionController.getAll(TransactionType.Income).addOnSuccessListener { it ->
            loading.visibility = View.GONE
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { it ->
                transactionController.getSpecific(TransactionType.Income,it).addOnSuccessListener{it->
                    it.documents.forEach { it->
                        it.reference.addSnapshotListener{ value, _ ->
                            if (value != null)
                            {
                                if (value.data?.get("value") != null)
                                {
                                    total.value = total.value?.plus(value.data?.get("value").toString().toInt())
                                    totalincome.value = totalincome.value?.plus(value.data?.get("value").toString().toInt())
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
                transactionController.getSpecific(TransactionType.Spending,it).addOnSuccessListener{it ->
                    it.documents.forEach {
                        it.reference.addSnapshotListener{ value, _ ->
                            if (value != null)
                            {
                                if (value.data?.get("value") != null) {
                                    totalspending.value = totalspending.value?.minus(value.data?.get("value").toString().toInt())
                                    total.value = total.value?.minus(value.data?.get("value").toString().toInt())
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
    fun getallIncomeData():LiveData<List<Map<String,Long>>>{
        var list:MutableList<String>
        val translist: MutableList<Map<String,Long>> = mutableListOf()
        transactionController.getAll(TransactionType.Income).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { item ->
                var total:Long = 0
                transactionController.getSpecific(TransactionType.Income,item).addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.addSnapshotListener{ value,error ->
                            if (value != null)
                            {
                                if(value.data?.get("value") != null) {
                                        total = total.plus(value.data?.get("value") as Long)
                                        val newlist = hashMapOf(
                                            item to total
                                        )
                                         translist.add(newlist)
                                        totalincometransactionlist.value=translist
                                }

                            }
                        }
                    }
                }
            }
        }
        return totalincometransactionlist
    }

    fun getallSpendingData():LiveData<List<Map<String,Long>>>{
        var list:MutableList<String>
        val translist: MutableList<Map<String,Long>> = mutableListOf()
        transactionController.getAll(TransactionType.Spending).addOnSuccessListener {
            list = it.data?.get("tag") as MutableList<String>
            list.forEach { item ->
                transactionController.getSpecific(TransactionType.Spending,item).addOnSuccessListener {
                    var total:Long = 0
                    it.documents.forEach {
                        it.reference.addSnapshotListener{ value, _ ->
                            if (value != null)
                            {
                                if(value.data?.get("value") != null) {
                                    total = total.plus(value.data?.get("value") as Long)
                                    val newlist = hashMapOf(
                                        item to total
                                    )
                                    translist.add(newlist)
                                    totalspendingtransactionlist.value=translist
                                }
                            }
                        }
                    }
                }
            }
        }
        return totalspendingtransactionlist
    }


}

