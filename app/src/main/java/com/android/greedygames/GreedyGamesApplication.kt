package com.android.greedygames

import android.app.Activity
import android.app.Application
import com.android.greedygames.ui.di.DaggerNovoComponent
import com.android.greedygames.ui.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class GreedyGamesApplication: Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerNovoComponent.builder().networkModule(NetworkModule()).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

}