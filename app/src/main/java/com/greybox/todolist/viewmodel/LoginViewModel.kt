package com.greybox.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.greybox.todolist.R
import com.greybox.todolist.app.MyApplication
import com.greybox.todolist.model.LoginResponse
import com.greybox.todolist.network.RequestBodies
import com.greybox.todolist.repository.AppRepository
import com.greybox.todolist.util.Event
import com.greybox.todolist.util.Resource
import com.greybox.todolist.util.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class LoginViewModel(
    app: Application,
    private val appRepository: AppRepository
) : AndroidViewModel(app) {

    private val _loginResponse = MutableLiveData<Event<Resource<LoginResponse>>>()
    val loginResponse:LiveData<Event<Resource<LoginResponse>>> = _loginResponse


    fun loginUser(body: RequestBodies.LoginBody) = viewModelScope.launch {
        login(body)
    }

    private suspend fun login(body: RequestBodies.LoginBody) {
        _loginResponse.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(getApplication<MyApplication>())) {
                val response = appRepository.loginUser(body)
                _loginResponse.postValue(handlePicsResponse(response))
            } else {
                _loginResponse.postValue(Event(Resource.Error(getApplication<MyApplication>().getString(R.string.no_internet_connection))))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _loginResponse.postValue(
                        Event(
                            Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.network_failure
                            )
                        ))
                    )
                }
                else -> {
                    _loginResponse.postValue(
                        Event(
                            Resource.Error(
                            getApplication<MyApplication>().getString(
                                R.string.conversion_error
                            )
                        ))
                    )
                }
            }
        }
    }

    private fun handlePicsResponse(response: Response<LoginResponse>): Event<Resource<LoginResponse>>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Error(response.message()))
    }
}