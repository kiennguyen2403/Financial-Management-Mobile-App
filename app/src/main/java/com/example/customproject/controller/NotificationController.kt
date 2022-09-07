package com.example.customproject.controller

import android.util.Log
import com.example.customproject.model.Notification
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NotificationController {
    val dbinstance = Firebase.firestore

    fun Create(_desc:String):Notification{
        val notification = Notification(_desc)
        return notification
    }

    fun Add(_desc: String)
    {
        val immutableMap = hashMapOf(
            "date" to Timestamp.now(),
            "desc" to _desc
        )

        dbinstance.collection("Notfication").add(immutableMap).addOnSuccessListener {
            Log.d("200","DocumentSnapshot added with ID:${it}")
        }.addOnFailureListener {
            Log.d("404","Error adding document",it)
        }

    }

    fun Get(): Task<QuerySnapshot>
    {
        var result = dbinstance.collection("Notification").get()
        return result
    }
}
