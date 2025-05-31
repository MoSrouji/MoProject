package com.example.myapplication.utils

import kotlinx.coroutines.flow.Flow
import android.util.Log
suspend fun <T> Flow<Response<T>>.collectAndHandle(

    onError:(Throwable?)-> Unit ={
        Log.e("collectAndHandle", "collectAndHandle: $it")
    } ,
    onLoading:()-> Unit ={},
    stateReducer:(T)-> Unit
){
    collect{response->
        when(response){
            is Response.Error ->{
                onError(response.error)
            }
            is Response.Success ->{
                stateReducer(response.data)
            }
            is Response.Loading ->{
                onLoading()
            }
        }

    }

}