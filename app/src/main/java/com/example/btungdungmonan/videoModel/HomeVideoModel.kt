package com.example.btungdungmonan.videoModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btungdungmonan.imgmonan.monan
import com.example.btungdungmonan.imgmonan.MonAnMeal
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.imgmonanrandomrcl1.MonAnall
import com.example.btungdungmonan.imgmonanrcl2kcop.docMeal
import com.example.btungdungmonan.imgmonanrcl2kcop.docMonAn
import com.example.btungdungmonan.retrofit.Retroifitinterface
import com.example.btungdungmonan.roomDatabase.DatabaseMeal
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeVideoModel(var databaseMeal: DatabaseMeal): ViewModel() {
    private var randomMuteldata= MutableLiveData<Meal>()
    private var monanmutablelistdata=MutableLiveData<List<MonAnMeal>>()
    private var docMonAnList=MutableLiveData<List<docMeal>>()
    private var bottomMonAnList=MutableLiveData<Meal>()
    private var favoritesMealbottom=databaseMeal.mealDao().realDataMeal()
    private var searchMeal=MutableLiveData<List<Meal>>()


    //www.themealdb.com/api/json/v1/1/random.php
    // Hàm để lấy danh sách mục được lọc từ API
    fun getrandommal() {
        //goi api
        Retroifitinterface.monanApi.getdatamonan().enqueue(object : Callback<MonAnall> {
            //ctrl i
            override fun onResponse(call: Call<MonAnall>, response: Response<MonAnall>) {
                if (response.body() != null) {
                    val monanall = response.body()!!.meals[0]
                    // them du lieu vao list
                    randomMuteldata.value=monanall

                } else
                    return
            }

            override fun onFailure(call: Call<MonAnall>, t: Throwable) {
                Log.d("HOME", "SAIIIIII")
            }
        })






        //www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    }
    fun getfilerApi(){
        Retroifitinterface.monanApi.getfilterApi("Seafood").enqueue(object :Callback<monan>{
            override fun onResponse(call: Call<monan>, response: Response<monan>) {
                if (response.body()!=null)
                {
                    monanmutablelistdata.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<monan>, t: Throwable) {
                Log.d("HOME1", "SAIIIIII")
            }
        })
    }

    //www.themealdb.com/api/json/v1/1/categories.php
    fun getDocMonAnApi(){
        Retroifitinterface.monanApi.getCategiriesApi().enqueue(object :Callback<docMonAn>{
            override fun onResponse(call: Call<docMonAn>, response: Response<docMonAn>) {
                if (response.body()!=null)
                {
                    docMonAnList.value=response.body()!!.categories

                }
            }

            override fun onFailure(call: Call<docMonAn>, t: Throwable) {
               Log.d("BBBBB","HI")
            }
        })
    }

    // bottom meal
    fun getMealBottom(id:String){
        Retroifitinterface.monanApi.getViewApi(id).enqueue(object : Callback<MonAnall>{
            override fun onResponse(call: Call<MonAnall>, response: Response<MonAnall>) {
                if (response.body()!=null)
                {
                    bottomMonAnList.value= response.body()!!.meals.first()

                }
            }

            override fun onFailure(call: Call<MonAnall>, t: Throwable) {
                Log.d("AABBB","SAI ROI")
            }
        })
    }

    //
    fun getsearchMealApi(s:String){
        Retroifitinterface.monanApi.getsearchApi(s).enqueue(object : Callback<MonAnall>{
            override fun onResponse(call: Call<MonAnall>, response: Response<MonAnall>) {
                if (response.body()!=null)
                {
                    searchMeal.value=response.body()!!.meals
                }
                Log.d("getsearchMealApi","Failure")
            }

            override fun onFailure(call: Call<MonAnall>, t: Throwable) {
                Log.d("onFailure",t.message.toString())
            }
        })
    }






    //+
    // Hàm để nhận LiveData cho một bữa ăn ngẫu nhiên
    fun ldtRanDomMeal():LiveData<Meal>{
        // Trả về đối tượng LiveData (randomMuteldata)
        return randomMuteldata
    }



    //+
    fun ldtMonanMealData():LiveData<List<MonAnMeal>>{
        return monanmutablelistdata
    }

    //
    fun ldtDocMonAnDaTa():LiveData<List<docMeal>>{
        return docMonAnList
    }

    fun ldtFavoritesMeal():LiveData<List<Meal>>{
        return favoritesMealbottom
    }

    fun ldtBottomMeal():LiveData<Meal>{
        return bottomMonAnList

    }

    //deletedu lieu
    fun deleteDataMeal(meal: Meal){
        // Sử dụng viewModelScope để tạo một coroutine scope liên quan đến ViewModel.
        viewModelScope.launch {
            // Gọi phương thức deleteMeal() trên đối tượng MealDao trong cơ sở dữ liệu.
            // Cách triển khai của phương thức này phụ thuộc vào cách bạn định nghĩa MealDao.
            databaseMeal.mealDao().deleteMeal(meal)
        }
    }

    // inser dulieu
    fun insertDataMeal(meal: Meal){
        viewModelScope.launch {
            databaseMeal.mealDao().insertMeal(meal)
        }
    }


    //ldtsearch
    fun ldtsearchMeal():LiveData<List<Meal>>{
        return searchMeal
    }

}