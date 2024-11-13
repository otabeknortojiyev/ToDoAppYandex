package uz.yayra.otabek.presenter.utils

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class DataSyncWorker(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            //must call sync method, but i can't
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}