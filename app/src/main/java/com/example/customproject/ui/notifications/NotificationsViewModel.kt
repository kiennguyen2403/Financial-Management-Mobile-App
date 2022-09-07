package com.example.customproject.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.customproject.controller.NotificationController
import com.example.customproject.model.Notification

class NotificationsViewModel : ViewModel() {
    private val notificationController = NotificationController()
    public var notificationlist:MutableLiveData<List<Notification>> = MutableLiveData()

    fun getallNotification():LiveData<List<Notification>>
    {
        var list: MutableList<Notification> = mutableListOf()
        notificationController.Get().addOnSuccessListener {
            it.documents.forEach {
                    val notification = notificationController.Create(it.data?.get("desc").toString())
                    list.add(notification)
                    notificationlist.value = list
            }
        }
        return notificationlist
    }
}