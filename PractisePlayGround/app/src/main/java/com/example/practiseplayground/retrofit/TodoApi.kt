package com.example.practiseplayground.retrofit


import com.example.practiseplayground.data.animal.Animal
import com.example.practiseplayground.data.animal.AnimalDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryName


interface TodoApi {

    @GET("{id}")
    suspend fun getTodo(@Path("id") id: Int): Todo

}

interface AnimalApi {
    @GET("animals")
    suspend fun getAnimals(): List<Animal>

    @POST("animals")
    suspend fun addAnimal(@Body animal: AnimalDto)

    @DELETE("animals/{id}")
    suspend fun removeAnimalById(@Path("id") id: String)

}

object RetrofitTodo{
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/todos/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()

    val todoApi = retrofit.create(TodoApi::class.java)
}

object RetrofitAnimal {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://6a9c55d90ad174e139e92a8b.mockapi.io/api/v1/")
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()


    val animalsApi = retrofit.create(AnimalApi::class.java)
}