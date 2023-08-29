package com.example.dsr_practice.domain.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.dsr_practice.domain.repository.TriggersRepository
import com.example.dsr_practice.domain.repository.WeatherRepository
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val triggersRepository: TriggersRepository,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = TriggersWorker(
        weatherRepository = weatherRepository,
        triggersRepository = triggersRepository,
        context = appContext,
        workerParams = workerParameters,
    )
}