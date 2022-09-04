package com.example.customproject.ui.notifications
import android.app.NotificationChannel
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class NotificationManager: FirebaseMessagingService() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title: String? = message.notification?.title
        var body:String? =  message.notification?.body
        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"
    }

}