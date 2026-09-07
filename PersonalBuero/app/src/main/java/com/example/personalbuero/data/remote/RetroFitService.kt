package com.example.personalbuero.data.remote

import com.example.personalbuero.data.employee.Employee
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetroFitService {

    @GET("employee")
    suspend fun getEmployees(): List<Employee>

    @POST("employee")
    suspend fun addEmployee(@Body employee: Employee)

    @DELETE("employee/{id}")
    suspend fun removeEmployee(@Path("id") id: String)

}

object EmployeeRetrofit {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://6a9c55d90ad174e139e92a8b.mockapi.io/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val employeeApi = retrofit.create(RetroFitService::class.java)


}