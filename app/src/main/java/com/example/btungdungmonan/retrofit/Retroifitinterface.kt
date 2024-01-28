package com.example.btungdungmonan.retrofit

import com.example.btungdungmonan.imgmonan.monan
import com.example.btungdungmonan.imgmonanrandomrcl1.MonAnall
import com.example.btungdungmonan.imgmonanrcl2kcop.docMonAn
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Retroifitinterface {
    // Đối tượng companion để cấu hình và tạo đối tượng API từ Retrofit
    companion object{
        // Khởi tạo Retrofit
        val retrofit=Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/") // Địa chỉ cơ sở của API
            .addConverterFactory(GsonConverterFactory.create()).build() // Sử dụng Gson để chuyển đổi JSON

        // Tạo đối tượng API từ Retrofit
        val monanApi= retrofit.create(Retroifitinterface::class.java)

    }

    //www.themealdb.com/api/json/v1/1/random.php
    // Phương thức GET để lấy dữ liệu món ăn ngẫu nhiên từ API
    @GET("random.php")
    fun getdatamonan():Call<MonAnall>

    //www.themealdb.com/api/json/v1/1/lookup.php?i=5277
    // Phương thức GET để lấy thông tin một món ăn dựa trên ID từ API
    @GET("lookup.php?")
    fun getViewApi(@Query("i") id:String) :Call<MonAnall>

    //https://www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    @GET("filter.php?")
    fun getfilterApi(@Query("c") name:String):Call<monan>

    //www.themealdb.com/api/json/v1/1/categories.php
    @GET("categories.php")
    fun getCategiriesApi():Call<docMonAn>

    //https://www.themealdb.com/api/json/v1/1/filter.php?c=beef
    @GET("filter.php?")    // txt va img lay theo getfilterApi
    fun getFileterItemApi(@Query("c") c:String):Call<monan>

    //www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    @GET("search.php")
    fun getsearchApi(@Query("s") s :String) :Call<MonAnall>



}