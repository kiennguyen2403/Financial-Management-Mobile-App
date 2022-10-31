package com.example.customproject.ui.transaction

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.TagController
import com.example.customproject.model.Tag
import com.example.customproject.model.TransactionType

class TransactionViewModel : ViewModel() {
   private val tagController = TagController()
   private val taglist: MutableLiveData<List<Tag>> = MutableLiveData()

   fun getallTag(loading:ProgressBar,type:Int):LiveData<List<Tag>>
   {
      if (type==1)
      {
         val list :MutableList<Tag> = mutableListOf()
            tagController.getAll(TransactionType.Income).addOnSuccessListener { result ->
               loading.visibility = View.GONE
               result.documents.forEach {
                  val newtag = tagController.Create(it.data?.get("desc") as String,it.data?.get("color") as String,TransactionType.Income, it.id)
                  list.add(newtag)
               }
               taglist.value=list
         }
      }
      else{
         val list :MutableList<Tag> = mutableListOf()
         tagController.getAll(TransactionType.Spending).addOnSuccessListener { result ->
            loading.visibility = View.GONE
            result.documents.forEach {
               val newtag = tagController.Create(it.data?.get("desc") as String,it.data?.get("color") as String,TransactionType.Income,it.id)
               list.add(newtag)
            }
            taglist.value=list
         }
      }

   return taglist
   }
}