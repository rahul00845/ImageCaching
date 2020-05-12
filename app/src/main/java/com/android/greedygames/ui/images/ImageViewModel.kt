package com.android.greedygames.ui.images

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.greedygames.data.models.ImageBaseModel
import com.android.greedygames.data.repositories.ImageRepository
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImageViewModel @Inject constructor (val compositeDisposable: CompositeDisposable, val imageRepository: ImageRepository): ViewModel() {

    private val imageListLiveData = MutableLiveData<ImageBaseModel>()

    fun getNewsList() {
        val data = imageRepository.getNewsList()
        compositeDisposable.add(
            data.subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        Log.d("ImgaeData", Gson().toJson(it))
                        val imageBaseModel = it.copy(code = 200)
                        imageListLiveData.postValue(imageBaseModel)
                    },
                    {
                        imageListLiveData.postValue(ImageBaseModel(404, null))
                    }
                )
        )
    }

    fun getImageListLiveData(): LiveData<ImageBaseModel> {
        return imageListLiveData
    }

}