package com.greybox.todolist.network

object RequestBodies {

    data class LoginBody(
        val email:String,
        val password:String
    )

}