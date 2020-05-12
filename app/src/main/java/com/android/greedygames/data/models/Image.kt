package com.android.greedygames.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageBaseModel (
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val data: BaseData?
): Parcelable

@Parcelize
data class BaseData (
    @SerializedName("children")
    val children: List<Children>?
): Parcelable

@Parcelize
data class Children (
    @SerializedName("data")
    val data: ImageData?
) : Parcelable

@Parcelize
data class ImageData (
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("preview")
    val preview: Preview?
): Parcelable

@Parcelize
data class Preview (
    @SerializedName("images")
    val images: List<Image>?
): Parcelable

@Parcelize
data class Image (
    @SerializedName("source")
    val source: Source?
): Parcelable

@Parcelize
data class Source (
    @SerializedName("url")
    val imageUrl: String?
): Parcelable