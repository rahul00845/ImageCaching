package com.android.greedygames.ui.di

import com.android.greedygames.ui.images.ImageListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindNewsActivity(): ImageListActivity

}