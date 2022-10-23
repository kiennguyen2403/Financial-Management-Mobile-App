package com.example.customproject.controller

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AccountController {
    private val dbinstance = Firebase.firestore

    fun get(): Task<QuerySnapshot> {
        val result =  dbinstance.collection("Account").get()
        return result
    }
}