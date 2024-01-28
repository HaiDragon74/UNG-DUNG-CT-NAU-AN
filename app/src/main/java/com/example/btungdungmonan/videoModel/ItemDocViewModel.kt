package com.example.btungdungmonan.videoModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.btungdungmonan.imgmonan.MonAnMeal
import com.example.btungdungmonan.imgmonan.monan
import com.example.btungdungmonan.retrofit.Retroifitinterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemDocViewModel():ViewModel() {
    private var itemDocLiveData=MutableLiveData<List<MonAnMeal>>()

    // Hàm để lấy danh sách mục được lọc từ API
    fun getFilterItemApi(c: String) {
        // Gọi API sử dụng Retrofitinterface.monanApi.getFileterItemApi(c)
        Retroifitinterface.monanApi.getFileterItemApi(c).enqueue(object : Callback<monan>{
            //ctrl i
            override fun onResponse(call: Call<monan>, response: Response<monan>) {
                // Kiểm tra xem có phản hồi từ API không
                if (response.body()!=null)
                {
                    itemDocLiveData.value=response.body()!!.meals
                    Log.d("XXXXX",response.body().toString())

                }
            }
            // Xử lý khi có lỗi kết nối hoặc lỗi từ API
            override fun onFailure(call: Call<monan>, t: Throwable) {

                Log.d("XXXXX",t.message.toString())
            }
        })
    }
    // Hàm để nhận LiveData cho các mục đã được lọc
    fun getldtFiltrItem():LiveData<List<MonAnMeal>>{
        // Trả về đối tượng LiveData (itemDocLiveData)
        return itemDocLiveData
    }
}