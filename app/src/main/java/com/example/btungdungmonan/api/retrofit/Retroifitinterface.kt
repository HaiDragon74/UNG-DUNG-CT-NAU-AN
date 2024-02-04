package com.example.btungdungmonan.api.retrofit

import com.example.btungdungmonan.dataclass.Search.ListSearchMeal
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.AllMeal
import com.example.btungdungmonan.dataclass.Categories.ListCategoryMeal
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Retroifitinterface {
    // ĐỐI TƯỢNG COMPANION ĐỂ CẤU HÌNH VÀ TẠO ĐỐI TƯỢNG API TỪ RETROFIT
    companion object{
        // KHỞI TẠO RETROFIT
        val retrofit=Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") // ĐỊA CHỈ CƠ SỞ CỦA API
            .addConverterFactory(GsonConverterFactory.create()).build() // SỬ DỤNG GSON ĐỂ CHUYỂN ĐỔI JSON

        // TẠO ĐỐI TƯỢNG API TỪ RETROFIT
        val retroifitinterface= retrofit.create(Retroifitinterface::class.java)

    }
    //www.themealdb.com/api/json/v1/1/random.php
    // PHƯƠNG THỨC GET ĐỂ LẤY DỮ LIỆU MÓN ĂN NGẪU NHIÊN TỪ API
    @GET("random.php")
    fun getdatamonan():Call<AllMeal>
    //www.themealdb.com/api/json/v1/1/lookup.php?i=5277
    // PHƯƠNG THỨC GET ĐỂ LẤY THÔNG TIN MỘT MÓN ĂN DỰA TRÊN ID TỪ API
    @GET("lookup.php?")
    fun getViewApi(@Query("i") id:String) :Call<AllMeal>
    //https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php?")
    fun getfilterApi(@Query("c") name:String):Call<ListSearchMeal>
    //www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php")
    fun getCategiriesApi():Call<ListCategoryMeal>
    //https://www.themealdb.com/api/json/v1/1/filter.php?c=beef
    @GET("filter.php?")    // txt va img lay theo getfilterApi
    fun getFileterItemApi(@Query("c") c:String):Call<ListSearchMeal>
    //www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    @GET("search.php")
    fun getsearchApi(@Query("s") s :String) :Call<AllMeal>



}