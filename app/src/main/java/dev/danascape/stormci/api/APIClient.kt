package dev.danascape.stormci.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIClient {
    private val BASE_URL = "https://raw.githubusercontent.com/stormbreaker-project/official_devices/master/devices/"
    private var mRetrofit: Retrofit? = null
    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()
            }
            return this.mRetrofit!!
        }
}