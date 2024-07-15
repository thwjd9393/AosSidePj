package com.lullulalal.myrecipeapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//Retrofit.Builder()는 엔드포인트를 준비시키고 Json converter를 추가하는 일을 함
//그 다음 create 메소드를 제공하는데 이는 서비스 메소드에 대한 액세스 권한을 위임한다
private val retrofit = Retrofit.Builder()
    .baseUrl("www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recepeService = retrofit.create(ApiService::class.java)
//해당 서비스가 필요하다는 것을 의미

interface ApiService {

    //List all meal categories
    //www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php") //엔드포인트 설정
    suspend fun getCategories():CategoriesResponse

}