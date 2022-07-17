package dev.danascape.stormci

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface BuildInterface {

    @GET("test.json")
    fun getBuildInfo(): Call<BuildModel>

    companion object{
        private const val BASE_URL = "https://abhiramshibu.tuxforums.com/~saalim/"

        fun create(): BuildInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(BuildInterface::class.java)
        }
    }
}