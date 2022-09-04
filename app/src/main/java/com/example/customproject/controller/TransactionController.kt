package com.example.customproject.controller

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TransactionController {
    val dbinstance= Firebase.firestore
    fun Create(_type: TransactionType, _value:Number, _desc:String, _tag: Tag): Transaction
    {
        val transaction = Transaction(_type,_value, _desc, _tag)
        return transaction;
    }

    fun Add(transaction: Transaction){
        val immutableMap: Map<String, String> = mapOf(
            "type" to transaction.type.toString(),
            "value" to transaction.value.toString(),
            "desc" to transaction.desc,
            "tag" to transaction.tag.toString()

        )
        dbinstance.collection(transaction.type.toString()).add(immutableMap)
            .addOnSuccessListener { result ->
                Log.d("200", "DocumentSnapshot added with ID: ${result}")
            }
            .addOnFailureListener { e ->
                Log.w("404", "Error adding document", e)
            }

    }


    fun Update(transaction: Transaction, data:Map<String,String>)
    {
        dbinstance.collection(transaction.type.toString())
            .document()
            .update(data)
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("404", "Error updating document", e) }
    }

    fun Delete(transaction: Transaction)
    {
        dbinstance.collection(transaction.type.toString())
            .document()
            .delete()
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("404", "Error deleting document", e) }
    }


    fun Get(transactionType: TransactionType, path:String): DocumentReference {


       var documentreference =  dbinstance.collection(transactionType.toString())
            .document(path)

        return documentreference
    }

    fun getAll(transactionType: TransactionType):Task<QuerySnapshot>{
        var snapshot = dbinstance.collection(transactionType.toString()).get()
        return snapshot
    }
}

