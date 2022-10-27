package com.example.customproject.controller

import android.util.Log
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.collections.HashMap

class TransactionController {
    private val dbinstance= Firebase.firestore
    fun Create(_value:Number, _desc:String,ID:String?): Transaction
    {
        val transaction = Transaction(_value, _desc, Timestamp.now(),ID)
        return transaction
    }

    fun Add(transaction: Transaction,transactionType: TransactionType,tag:String){
        val immutableMap = hashMapOf(
            "value" to transaction.value,
            "desc" to transaction.desc,
            "date" to Timestamp.now()
        )
        dbinstance.collection("Transaction").document(transactionType.toString()).collection(tag).add(immutableMap)
            .addOnSuccessListener { result ->
                Log.d("200", "DocumentSnapshot added with ID: ${result}")
            }
            .addOnFailureListener { e ->
                Log.w("404", "Error adding document", e)
            }

    }

    fun Update(transactionType: TransactionType, tag: String, data:HashMap<String,Any>,transactionID:String)
    {
        dbinstance.collection("Transaction").document(transactionType.toString()).collection(tag)
            .document(transactionID)
            .update("desc", data["desc"].toString(),"value",data["value"].toString().toInt() )
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("404", "Error updating document", e) }
    }

    fun Delete(transactionType: TransactionType, tag: String,transactionID:String)
    {
        Log.d("200",transactionID)
        Log.d("200",transactionType.toString())
        Log.d("200",tag)
        dbinstance.collection("Transaction").document(transactionType.toString()).collection(tag)
            .document(transactionID)
            .delete()
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("404", "Error deleting document", e) }
    }



    fun getSpecific(transactionType: TransactionType, tag: String): Task<QuerySnapshot> {


        return dbinstance.collection("Transaction").document(transactionType.toString()).collection(tag).get()
    }

    fun getAll(transactionType: TransactionType): Task<DocumentSnapshot> {
        return dbinstance.collection("Transaction").document(transactionType.toString()).get()
    }

}

