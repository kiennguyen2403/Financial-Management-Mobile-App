package com.example.customproject.controller

import android.util.Log
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TagController {
    val dbinstance= Firebase.firestore
    fun Create(_type: TransactionType, _value:Number, _desc:String): Transaction
    {
        val transaction = Transaction(_value, _desc, Timestamp.now())
        return transaction;
    }

    fun Add(transaction: Transaction, transactionType: TransactionType, tag:String){
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


    fun Update(transaction: Transaction, transactionType: TransactionType, tag: String, data:HashMap<String,Any>)
    {
        dbinstance.collection("Transaction").document(transactionType.toString()).collection(tag)
            .document()
            .update(data)
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("404", "Error updating document", e) }
    }

    fun Delete(transaction: Transaction, transactionType: TransactionType, tag: String)
    {
        dbinstance.collection("Tag").document(transactionType.toString()).collection(tag)
            .document()
            .delete()
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("404", "Error deleting document", e) }
    }

    fun getAll(transactionType: TransactionType): Task<QuerySnapshot> {
        val snapshot = dbinstance.collection("Tag").whereEqualTo("type",transactionType.toString()).get()
        return snapshot
    }
}