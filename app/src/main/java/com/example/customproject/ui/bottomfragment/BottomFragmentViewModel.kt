package com.example.customproject.ui.bottomfragment

import android.util.Log
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.NotificationController
import com.example.customproject.controller.TagController
import com.example.customproject.controller.TransactionController
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.firebase.firestore.QueryDocumentSnapshot

class BottomFragmentViewModel:ViewModel() {
    private lateinit var transactionController: TransactionController
   fun Delete(transactionType: TransactionType, tag:String,ID:String){
       transactionController = TransactionController()
       transactionController.Delete(transactionType,tag,ID)
   }

    fun Update(transactionType: TransactionType, tag: String, data:HashMap<String,Any>,ID:String){
        transactionController = TransactionController()
        transactionController.Update(transactionType,tag,data,ID)
    }
    fun createNotification(notification:String){
        var notificationController = NotificationController()
        notificationController.Add(notification)
    }

}