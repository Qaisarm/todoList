package com.greybox.todolist.repository

import com.greybox.todolist.network.RequestBodies
import com.greybox.todolist.network.RetrofitInstance


class AppRepository {

    suspend fun loginUser(body: RequestBodies.LoginBody) = RetrofitInstance.loginApi.loginUser(body)
}