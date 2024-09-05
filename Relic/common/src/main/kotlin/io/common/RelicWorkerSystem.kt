package io.common

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.Operation
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import androidx.work.WorkRequest

object RelicWorkerSystem {

    fun enqueueNormalWorker(
        context: Context,
        request: WorkRequest
    ): Operation {
        return getSystemWorkerManager(context).enqueue(request)
    }

    fun enqueueUniqueOneTimeWorker(
        context: Context,
        uniqueWorkerName: String,
        request: OneTimeWorkRequest
    ): Operation {
        return getSystemWorkerManager(context)
            .enqueueUniqueWork(
                /* uniqueWorkName = */ uniqueWorkerName,
                /* existingWorkPolicy = */ ExistingWorkPolicy.KEEP,
                /* work = */ request
            )
    }

    fun enqueueUniquePeriodWorker(
        context: Context,
        uniqueWorkerName: String,
        request: PeriodicWorkRequest
    ): Operation {
        return getSystemWorkerManager(context)
            .enqueueUniquePeriodicWork(
                /* uniqueWorkName = */ uniqueWorkerName,
                /* existingPeriodicWorkPolicy = */ ExistingPeriodicWorkPolicy.KEEP,
                /* periodicWork = */ request
            )
    }

    private fun getSystemWorkerManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }
}