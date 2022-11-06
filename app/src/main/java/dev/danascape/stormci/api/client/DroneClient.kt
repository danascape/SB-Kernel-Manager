package dev.danascape.stormci.api.client

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DroneClient {
    private val BASE_URL = "https://cloud.drone.io/api/repos/stormbreaker-project/StormCI/"
    private var mRetrofit: Retrofit? = null
    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return mRetrofit!!
        }
}