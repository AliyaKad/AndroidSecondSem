package ru.itis.AndroidSecondSem

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.itis.AndroidSecondSem.presentation.MainActivity

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val CHANNEL_ID = "important_notifications"
    private val NOTIFICATION_ID = 100

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val category = remoteMessage.data["category"] ?: return

        when (category) {
            "important" -> showImportantNotification(remoteMessage)
            "silent" -> saveSilentData(remoteMessage)
            "feature_open" -> openFeatureIfAppInForeground(remoteMessage)
        }
    }

    private fun showImportantNotification(remoteMessage: RemoteMessage) {
        val title = remoteMessage.data["title"] ?: getString(R.string.notification_important_title)
        val message = remoteMessage.data["message"] ?: ""

        val channelId = CHANNEL_ID

        val deepLink = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.mainFragment)
            .createPendingIntent()

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setContentIntent(deepLink)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, getString(R.string.channel_important_notifications), NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        manager.notify(NOTIFICATION_ID, notification)
    }

    private fun saveSilentData(remoteMessage: RemoteMessage) {
        val prefs: SharedPreferences = getSharedPreferences("fcm_data", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        remoteMessage.data.forEach { (key, value) ->
            editor.putString(key, value)
        }
        editor.apply()
    }

    private fun openFeatureIfAppInForeground(remoteMessage: RemoteMessage) {
        if (isAppInForeground()) {
            val currencyCode = remoteMessage.data["currency_code"] ?: "USD"

            if (!isUserLoggedIn()) {
                showToast(getString(R.string.feature_open_denied))
            } else {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("navigate_to_detail", true)
                    putExtra("currency_code", currencyCode)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
                startActivity(intent)
            }
        }
    }

    private fun isUserLoggedIn(): Boolean {
        val prefs = getSharedPreferences("auth_data", Context.MODE_PRIVATE)
        return prefs.getBoolean("is_logged_in", false)
    }

    companion object {
        var isForeground = false
    }

    private fun isAppInForeground(): Boolean {
        return isForeground
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}