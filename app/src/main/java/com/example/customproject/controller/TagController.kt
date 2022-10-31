package com.example.customproject.controller

import android.util.Log
import com.example.customproject.model.Tag
import com.example.customproject.model.Transaction
import com.example.customproject.model.TransactionType
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TagController {
    private val dbinstance= Firebase.firestore
    fun Create(_name: String, _color: String, _type: TransactionType,id:String): Tag
    {
        val tag = Tag(_name, _color, _type,id)
        return tag
    }

    fun Add(tag: Tag){
        val immutableMap = hashMapOf(
            "color" to tag.color,
            "desc" to tag.name,
            "type" to tag.type.toString()
        )
        dbinstance.collection("Tag").add(immutableMap)
            .addOnSuccessListener { result ->
                Log.d("200", "DocumentSnapshot added with ID: ${result}")
            }
            .addOnFailureListener { e ->
                Log.w("404", "Error adding document", e)
            }

    }

    /*
    fun Update(tag:Tag, data:HashMap<String,Any>)
    {
        dbinstance.collection("Tag")
            .document()
            .update(data)
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("404", "Error updating document", e) }
    }

    fun Delete(transaction: Transaction, transactionType: TransactionType, tag: String)
    {
        dbinstance.collection("Tag")
            .document()
            .delete()
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("404", "Error deleting document", e) }
    }
    */

    fun getAll(transactionType: TransactionType): Task<QuerySnapshot> {
        val snapshot = dbinstance.collection("Tag").whereEqualTo("type",transactionType.toString()).get()
        return snapshot
    }
}