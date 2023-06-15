package com.oneplatform.obeng.api

import com.oneplatform.obeng.model.TechnicianViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface TechnicianApiService {
    // Existing function to fetch all technicians
    @GET("api/technicians/data")
    suspend fun getTechnicians(): List<TechnicianViewModel.Technician>

    // New function to fetch technician by ID
    @GET("api/technicians/{technicianId}")
    suspend fun getTechnicianById(@Path("technicianId") technicianId: String): TechnicianViewModel.Technician

    companion object {
        fun create(): TechnicianApiService {
            val okHttpClient = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://us-central1-loginsignup-auth-dc6a9.cloudfunctions.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(TechnicianApiService::class.java)
        }
    }
}


val retrofit = Retrofit.Builder()
    .baseUrl("https://us-central1-loginsignup-auth-dc6a9.cloudfunctions.net/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(TechnicianApiService::class.java)


