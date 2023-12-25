package io.common

import android.content.Context
import androidx.work.Operation
import androidx.work.WorkManager
import androidx.work.WorkRequest

object RelicWorkerSystem {

    fun enqueueWorker(
        context: Context,
        request: WorkRequest
    ): Operation {
        return getSystemWorkerManager(context).enqueue(request)
    }

    private fun getSystemWorkerManager(context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }

}