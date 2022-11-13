package dev.danascape.stormci.api

import dev.danascape.stormci.util.Constants.Companion.DRONE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DroneAPI {
    private var mRetrofit: Retrofit? = null
    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(DRONE_URL)
                    .build()
            }
            return mRetrofit!!
        }
}