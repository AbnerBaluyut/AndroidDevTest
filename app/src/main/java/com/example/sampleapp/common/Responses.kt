package com.example.sampleapp.common

import com.google.gson.annotations.SerializedName

object Responses {

    data class GetUserDetail(
        @SerializedName("company") val company: String?,
        @SerializedName("blog") val blog: String?,
        @SerializedName("followers") val followers: String?,
        @SerializedName("following") val following: String?)
}