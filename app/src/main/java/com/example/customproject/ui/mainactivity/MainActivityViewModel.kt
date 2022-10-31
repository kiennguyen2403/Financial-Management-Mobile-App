package com.example.customproject.ui.mainactivity

import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.NotificationController
import com.example.customproject.controller.TagController
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.TransactionType
import com.google.firebase.firestore.QueryDocumentSnapshot

class MainActivityViewModel:ViewModel() {
    private lateinit var transactionController: TransactionController
    private lateinit var notificationController: NotificationController
    private lateinit var tagController: TagController
    fun createLabel(name:String,color:String,transactionType: TransactionType){
        tagController = TagController()
        val result = tagController.Create(name,color,transactionType,"")
        tagController.Add(result)
    }

    fun createTransaction(type:TransactionType,value:Int,desc:String,taginput:String){
        transactionController = TransactionController()
        val result = transactionController.Create(value,desc,"")
        transactionController.Add(result, type,taginput)
    }
    fun createNotification(notification:String){
        notificationController = NotificationController()
        notificationController.Add(notification)
    }

    fun getLabel(taglists:ArrayList<String>,tagAdapter:ArrayAdapter<String>,type:String){
        tagController=TagController()
       if (type=="Income") {
           taglists.clear()
           tagController.getAll(TransactionType.Income).addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   for (document: QueryDocumentSnapshot in task.result) {
                       val tag = document.getString("desc")
                       if (tag != "") {
                           taglists.add(tag as String)
                       }
                   }
                   tagAdapter.notifyDataSetChanged()
               }
           }
       }
        else{
           taglists.clear()
           tagController.getAll(TransactionType.Spending).addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   for (document: QueryDocumentSnapshot in task.result) {
                       val tag = document.getString("desc")
                       if (tag != null) {
                           taglists.add(tag)
                       }
                   }
                   tagAdapter.notifyDataSetChanged()
               }
           }
        }
    }


}