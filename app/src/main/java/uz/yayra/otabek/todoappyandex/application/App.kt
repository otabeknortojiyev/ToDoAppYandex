package uz.yayra.otabek.todoappyandex.application

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import uz.yayra.otabek.presenter.utils.DataSyncWorker
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        schedulePeriodicDataSync()
    }

    private fun schedulePeriodicDataSync() {
        val syncRequest = PeriodicWorkRequestBuilder<DataSyncWorker>(8, TimeUnit.HOURS)
            .setInitialDelay(8, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DataSyncWork",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }
}