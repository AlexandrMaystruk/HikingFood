package com.gmail.maystruks08.hikingfood.core.di.application

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AndroidModule (val context: Context) {

    @Provides
    @Singleton
    fun context (): Context = context

}