package com.example.dsr_practice.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dsr_practice.R
import com.example.dsr_practice.domain.UserPrefHelper
import com.example.dsr_practice.domain.repository.TriggersRepository
import com.example.dsr_practice.domain.repository.WeatherRepository
import com.example.dsr_practice.ui.destinations.DetailsScreenDestination
import com.example.dsr_practice.ui.main_activity.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import kotlin.math.floor

@HiltWorker
class TriggersWorker @AssistedInject constructor(
    private val weatherRepository: WeatherRepository,
    private val triggersRepository: TriggersRepository,
    private val userPrefHelper: UserPrefHelper,
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    init {
        createNotificationChannel()
    }

    @SuppressLint("MissingPermission")
    private suspend fun notification(placeId: Int, title: String, text: String) {
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        userPrefHelper.setWeatherId(placeId)
        userPrefHelper.setStartRoute(DetailsScreenDestination.route)
        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(applicationContext)) {
            this.areNotificationsEnabled()
            notify(NOTIFY_ID, builder.build())
        }
    }

    override suspend fun doWork(): Result {
        try {
            val triggers = triggersRepository.fetchTriggers().first()
            triggers.forEach { trigger ->
                weatherRepository.forecastById(trigger.locationId).first().let { response ->
                    val checkTemp = floor(response.currentTemp) == trigger.temp
                    val checkWindSpeed =
                        floor(response.daily.first().windSpeed) == trigger.windSpeed
                    val checkHumidity = floor(response.daily.first().humidity) == trigger.humidity
                    val checkPressure = floor(response.daily.first().pressure) == trigger.pressure
                    if (checkTemp || checkWindSpeed || checkHumidity || checkPressure) notification(
                        placeId = trigger.locationId,
                        title = trigger.name ?: "",
                        text = applicationContext.getString(R.string.click_here_for_details)
                    )
                }

            }
        } catch (t: Throwable) {
            t.printStackTrace()
            Result.failure()
        }
        return Result.success()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager = getSystemService(
                applicationContext, NotificationManager::class.java
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_NAME = "channel"
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIFY_ID = 1234
        const val WORK_NAME = "triggers_work"
    }
}
