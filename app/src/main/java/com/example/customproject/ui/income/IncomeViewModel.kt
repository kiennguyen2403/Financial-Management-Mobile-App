package com.example.customproject.ui.income

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

class IncomeViewModel : ViewModel() {
   val transactionController= TransactionController()
   val tagController = TagController()
   val taglist: MutableLiveData<List<String>> = MutableLiveData()
   var transactionlist: MutableLiveData<List<Transaction>> = MutableLiveData()

   fun getallTag():LiveData<List<String>>
   {
      var list :MutableList<String> = mutableListOf()
         tagController.getAll(TransactionType.Income).addOnSuccessListener { result ->
            result.documents.forEach {it ->
               list.add(it.data?.get("desc") as String)
               taglist.value=list
            }
      }

   return taglist
   }
   /*
   fun getallData():LiveData<List<Transaction>>{
      var list:MutableList<Transaction> = mutableListOf()

   }
   */

}