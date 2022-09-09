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
   val tagController = TagController()
   val taglist: MutableLiveData<List<Tag>> = MutableLiveData()

   fun getallTag():LiveData<List<Tag>>
   {
      var list :MutableList<Tag> = mutableListOf()
         tagController.getAll(TransactionType.Income).addOnSuccessListener { result ->
            result.documents.forEach {it ->
               val newtag = tagController.Create(it.data?.get("desc") as String,it.data?.get("color") as String,TransactionType.Income)
               list.add(newtag)
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