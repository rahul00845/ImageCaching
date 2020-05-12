package com.android.greedygames.data.repositories

import com.android.greedygames.data.models.ImageBaseModel
import com.android.greedygames.data.remote.NetworkService
import io.reactivex.Single
import javax.inject.Inject

class ImageRepository @Inject constructor(val networkService: NetworkService) {

    fun getNewsList (): Single<ImageBaseModel> {
        return networkService.getImagelist()
    }

}