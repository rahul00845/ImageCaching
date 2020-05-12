package com.android.greedygames.ui.di.viewmodels

import androidx.lifecycle.ViewModel
import com.android.greedygames.ui.di.ViewModelKey
import com.android.greedygames.ui.images.ImageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ImageViewModel::class)
    abstract fun bindNewsViewModel(viewModel: ImageViewModel): ViewModel
}