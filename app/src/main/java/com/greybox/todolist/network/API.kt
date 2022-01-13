package com.greybox.todolist.network

import com.greybox.todolist.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface API {

    @POST("api/login")
    suspend fun loginUser(@Body body: RequestBodies.LoginBody): Response<LoginResponse>
}