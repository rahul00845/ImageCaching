package com.android.greedygames.ui.di

import com.android.greedygames.data.remote.NetworkService
import com.android.greedygames.data.repositories.ImageRepository
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class AppModule {

    @Provides
    fun getCompositeDisposable() = CompositeDisposable()

    @Provides
    fun getNewsRepository(networkService: NetworkService) = ImageRepository (networkService)

}