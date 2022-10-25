package com.example.customproject.ui.notifications

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.NotificationController
import com.example.customproject.model.Notification

class NotificationsViewModel : ViewModel() {
    private val notificationController = NotificationController()
    private var notificationlist:MutableLiveData<List<Notification>> = MutableLiveData()

    fun getallNotification(loading:ProgressBar):LiveData<List<Notification>>
    {
        val list: MutableList<Notification> = mutableListOf()
        notificationController.Get().addOnSuccessListener { it ->
            loading.visibility = View.GONE
            it.documents.forEach {
                    val notification = notificationController.Create(it.data?.get("desc").toString())
                    list.add(notification)

            }
            notificationlist.value = list
        }
        return notificationlist
    }
}