package com.android.greedygames.data.remote

import com.android.greedygames.data.models.ImageBaseModel
import io.reactivex.Single
import retrofit2.http.GET

interface NetworkService {

    @GET(EndPoints.GET_IMAGES)
    fun getImagelist(): Single<ImageBaseModel>

}