package com.akash.weatherapp.retrofit

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class NetworkConnectionInterceptor(private val mContext: Context) : Interceptor {

    private val errorJsonString = "{\"msg\": \"Something went wrong\" }"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected) {
            throw NetworkException("Please connect to internet", 404)
        }
        val builder: Request.Builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())
        if (!response.isSuccessful) {
            throw NetworkException(
                response.body?.string() ?: errorJsonString, response.code
            )
        }
        return response

    }

    private val isConnected: Boolean
        get() {
            val connectivityManager =
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
}

class NetworkException(private val errorMessage: String, val errorCode: Int) : IOException() {
    private val gson = Gson().fromJson(errorMessage, ErrorMessage::class.java)
    override val message: String
        get() = gson.errorMessage ?: gson.errorMsg
}

data class ErrorMessage(
    @SerializedName("message") val errorMessage: String?,
    @SerializedName("msg") val errorMsg: String = "Something went wrong"
)
