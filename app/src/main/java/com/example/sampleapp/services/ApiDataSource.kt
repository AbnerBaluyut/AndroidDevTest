package com.example.sampleapp.services

import com.example.sampleapp.common.Endpoints
import com.example.sampleapp.common.Responses
import com.example.sampleapp.common.models.entity.UsersData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiDataSource {

    // Users
    @GET(Endpoints.USERS)
    fun getAllUsersAsync() : Deferred<List<UsersData>>

    //Detail
    @GET(Endpoints.DETAIL + "{username}")
    fun getDetailAsync(@Path("username") username: String) : Deferred<Responses.GetUserDetail>
}