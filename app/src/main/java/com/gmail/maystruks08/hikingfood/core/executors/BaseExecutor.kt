package com.gmail.maystruks08.hikingfood.core.executors

import android.os.HandlerThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.gmail.maystruks08.domain.executor.ThreadExecutor


class BaseExecutor : ThreadExecutor() {

    override val mainExecutor: Scheduler
        get() = Schedulers.io()

    override val postExecutor: Scheduler = AndroidSchedulers.mainThread()

    override val supportExecutor: Scheduler
        get() {
            val newThread = HandlerThread(UI_WORKING_THREAD_NAME).apply { start() }
            return AndroidSchedulers.from(newThread.looper)
        }

    companion object {

        private const val UI_WORKING_THREAD_NAME = "UI:WorkingThread"

    }

}