package com.gmail.maystruks08.domain.executor

import io.reactivex.Scheduler

abstract class ThreadExecutor {

    abstract val mainExecutor: Scheduler

    abstract val postExecutor: Scheduler

    abstract val supportExecutor: Scheduler

}