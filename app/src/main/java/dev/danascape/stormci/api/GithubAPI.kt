package dev.danascape.stormci.api

import dev.danascape.stormci.util.Constants.Companion.GITHUB_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubAPI {
    private var mRetrofit: Retrofit? = null
    val client: Retrofit
        get() {
            if (mRetrofit == null) {
                mRetrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(GITHUB_URL)
                    .build()
            }
            return mRetrofit!!
        }
}