package com.android.greedygames.ui.di

import com.android.greedygames.GreedyGamesApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, BuildersModule::class, ViewModelFactoryModule::class, AppModule::class, NetworkModule::class])
interface NovoComponent : AndroidInjector<GreedyGamesApplication>