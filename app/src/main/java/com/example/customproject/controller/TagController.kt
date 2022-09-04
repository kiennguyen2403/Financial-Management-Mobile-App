package com.example.customproject.controller

import android.util.Log
import com.example.customproject.model.Tag
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class TagController {
    val dbinstance= Firebase.firestore
    fun Create(_name:String):Tag{
        val tag = Tag(_name)
        return tag
    }
    fun Add(tag: Tag)
    {
        val immutableMap: Map<String,String> = mapOf(
            "name" to tag.name
        )

        dbinstance.collection("Tag").add(immutableMap).addOnSuccessListener { result ->
            Log.d("200", "DocumentSnapshot added with ID: ${result}")
        }
            .addOnFailureListener { e ->
                Log.w("404", "Error adding document", e)
            }
    }

    fun Get(path:String): DocumentReference
    {
        var documentreference =  dbinstance.collection("Tag").document(path)
        return documentreference
    }

    fun getAll(): Task<QuerySnapshot>
    {
        var snapshot = dbinstance.collection("Tag").get()
        return snapshot
    }

    fun Delete()
    {
        dbinstance.collection("Tag")
            .document()
            .delete()
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("404", "Error deleting document", e) }
    }

    fun Update(data:Map<String, String>)
    {
        dbinstance.collection("Tag")
            .document()
            .update(data)
            .addOnSuccessListener { Log.d("200", "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w("404", "Error updating document", e) }
    }

}