package com.example.btungdungmonan.api.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.dataclass.Search.ListSearchMeal
import com.example.btungdungmonan.api.retrofit.Retroifitinterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatevoriViewModel():ViewModel() {
    private var itemDocLiveData=MutableLiveData<List<SearchMeal>>()

    // Hàm để lấy danh sách mục được lọc từ API
    fun getFilterItemApi(c: String) {
        // Gọi API sử dụng Retrofitinterface.monanApi.getFileterItemApi(c)
        Retroifitinterface.retroifitinterface.getFileterItemApi(c).enqueue(object : Callback<ListSearchMeal>{
            //ctrl i
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                // Kiểm tra xem có phản hồi từ API không
                if (response.body()!=null)
                {
                    itemDocLiveData.value=response.body()!!.meals
                    Log.d("XXXXX",response.body().toString())

                }
            }
            // Xử lý khi có lỗi kết nối hoặc lỗi từ API
            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {

                Log.d("XXXXX",t.message.toString())
            }
        })
    }
    // Hàm để nhận LiveData cho các mục đã được lọc
    fun getldtFiltrItem():LiveData<List<SearchMeal>>{
        // Trả về đối tượng LiveData (itemDocLiveData)
        return itemDocLiveData
    }
}