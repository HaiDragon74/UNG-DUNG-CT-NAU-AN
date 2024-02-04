package com.example.btungdungmonan.api.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.btungdungmonan.dataclass.Search.ListSearchMeal
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.AllMeal
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.dataclass.Categories.ListCategoryMeal
import com.example.btungdungmonan.api.retrofit.Retroifitinterface
import com.example.btungdungmonan.rom.DatabaseMeal
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(var databaseMeal: DatabaseMeal): ViewModel() {
    private var randomMuteldata= MutableLiveData<Meal>()
    private var docMonAnList=MutableLiveData<List<CategoriMeal>>()
    private var bottomMonAnList=MutableLiveData<Meal>()
    private var favoritesMealbottom=databaseMeal.mealDao().realDataMeal()
    private var searchMeal=MutableLiveData<List<Meal>>()
    //TIM KIEM MON AN THEO TEN
    private var seafoodMutableListData=MutableLiveData<List<SearchMeal>>()
    private var chickenMutableListData=MutableLiveData<List<SearchMeal>>()
    private var dessertMutableListData=MutableLiveData<List<SearchMeal>>()
    private var lambMutableListData=MutableLiveData<List<SearchMeal>>()
    private var miscellaneousMutableListData=MutableLiveData<List<SearchMeal>>()
    private var pastaMutableListData=MutableLiveData<List<SearchMeal>>()
    private var porkMutableListData=MutableLiveData<List<SearchMeal>>()
    private var sideMutableListData=MutableLiveData<List<SearchMeal>>()
    private var starterMutableListData=MutableLiveData<List<SearchMeal>>()
    private var veganMutableListData=MutableLiveData<List<SearchMeal>>()
    private var vegetarianMutableListData=MutableLiveData<List<SearchMeal>>()
    private var breakfastMutableListData=MutableLiveData<List<SearchMeal>>()
    private var goatMutableListData=MutableLiveData<List<SearchMeal>>()
    private var beefMutableListData=MutableLiveData<List<SearchMeal>>()


    //www.themealdb.com/api/json/v1/1/random.php
    // HÀM ĐỂ LẤY DANH SÁCH MỤC ĐƯỢC LỌC TỪ API
    fun getAllmeal() {
        //goi api
        Retroifitinterface.retroifitinterface.getdatamonan().enqueue(object : Callback<AllMeal> {
            //ctrl i
            override fun onResponse(call: Call<AllMeal>, response: Response<AllMeal>) {
                if (response.body() != null) {
                    val monanall = response.body()!!.meals[0]
                    // them du lieu vao list
                    randomMuteldata.value=monanall

                } else
                    return
            }

            override fun onFailure(call: Call<AllMeal>, t: Throwable) {
                Log.d("HOME", t.message.toString())
            }
        })
    }
    //www.themealdb.com/api/json/v1/1/categories.php
    fun getCategoryMeal(){
        Retroifitinterface.retroifitinterface.getCategiriesApi().enqueue(object :Callback<ListCategoryMeal>{
            override fun onResponse(call: Call<ListCategoryMeal>, response: Response<ListCategoryMeal>) {
                if (response.body()!=null)
                {
                    docMonAnList.value=response.body()!!.categories
                }
            }
            override fun onFailure(call: Call<ListCategoryMeal>, t: Throwable) {
                Log.d("BBBBB",t.message.toString())
            }
        })
    }
    // bottom meal
    fun getAllMealBottomFrament(id:String){
        Retroifitinterface.retroifitinterface.getViewApi(id).enqueue(object : Callback<AllMeal>{
            override fun onResponse(call: Call<AllMeal>, response: Response<AllMeal>) {
                if (response.body()!=null)
                {
                    bottomMonAnList.value= response.body()!!.meals.first()

                }
            }
            override fun onFailure(call: Call<AllMeal>, t: Throwable) {
                Log.d("AABBB",t.message.toString())
            }
        })
    }
    //
    fun getsearchMealApi(s:String){
        Retroifitinterface.retroifitinterface.getsearchApi(s).enqueue(object : Callback<AllMeal>{
            override fun onResponse(call: Call<AllMeal>, response: Response<AllMeal>) {
                if (response.body()!=null)
                {
                    searchMeal.value=response.body()!!.meals
                }
                Log.d("getsearchMealApi","Failure")
            }

            override fun onFailure(call: Call<AllMeal>, t: Throwable) {
                Log.d("onFailure",t.message.toString())
            }
        })
    }
    //www.themealdb.com/api/json/v1/1/filter.php?c=Seafood
    fun getSeafoodApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Seafood").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    seafoodMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME1", t.message.toString())
            }
        })
    }
    fun getChickenApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Chicken").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    chickenMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME1", t.message.toString())
            }
        })
    }
    fun getDessertApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Dessert").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    dessertMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME1", t.message.toString())
            }
        })
    }
    fun getLambApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Lamb").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    lambMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME1", t.message.toString())
            }
        })
    }
    fun getMiscellaneousApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Miscellaneous").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    miscellaneousMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME1", t.message.toString())
            }
        })
    }
    fun getPastaApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Pasta").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    pastaMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME1", t.message.toString())
            }
        })
    }
    fun getPorkApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Pork").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    porkMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", t.message.toString())
            }
        })
    }
    fun getSideApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Side").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    sideMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", "SAI")
            }
        })
    }
    fun getStarterApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Starter").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    starterMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", "SAI")
            }
        })
    }
    fun getVeganApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Vegan").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    veganMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", "SAI")
            }
        })
    }
    fun getVegetarianApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Vegetarian").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    vegetarianMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", "SAI")
            }
        })
    }
    fun getBreakfastApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Breakfast").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    breakfastMutableListData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", "SAIIIIII")
            }
        })
    }
    fun getGoatApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Goat").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    goatMutableListData.value = response.body()!!.meals
                }
            }
            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", t.message.toString())
            }
        })
    }
    fun getBeefApi(){
        Retroifitinterface.retroifitinterface.getfilterApi("Beef").enqueue(object :Callback<ListSearchMeal>{
            override fun onResponse(call: Call<ListSearchMeal>, response: Response<ListSearchMeal>) {
                if (response.body()!=null)
                {
                    beefMutableListData.value = response.body()!!.meals
                }
            }
            override fun onFailure(call: Call<ListSearchMeal>, t: Throwable) {
                Log.d("HOME", t.message.toString())
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
    fun ldtseafoodMealData():LiveData<List<SearchMeal>>{
        return seafoodMutableListData
    }
    fun ldtChickenMonanMeal():LiveData<List<SearchMeal>>{
        return chickenMutableListData
    }
    fun ldtDessertMonanMeal():LiveData<List<SearchMeal>>{
        return dessertMutableListData
    }
    fun ldtBeefMonanMeal():LiveData<List<SearchMeal>>{
        return beefMutableListData
    }
    fun ldtLambtMonanMeal():LiveData<List<SearchMeal>>{
        return lambMutableListData
    }
    fun ldtMiscellaneousMonanMeal():LiveData<List<SearchMeal>>{
        return miscellaneousMutableListData
    }
    fun ldtPastaMonanMeal():LiveData<List<SearchMeal>>{
        return pastaMutableListData
    }
    fun ldtPorkMonanMeal():LiveData<List<SearchMeal>>{
        return porkMutableListData
    }
    fun ldtSideMonanMeal():LiveData<List<SearchMeal>>{
        return sideMutableListData
    }
    fun ldtStarterMonanMeal():LiveData<List<SearchMeal>>{
        return starterMutableListData
    }
    fun ldtVeganMonanMeal():LiveData<List<SearchMeal>>{
        return veganMutableListData
    }
    fun ldtVegetarianMonanMeal():LiveData<List<SearchMeal>>{
        return vegetarianMutableListData
    }
    fun ldtBreakfastMonanMeal():LiveData<List<SearchMeal>>{
        return breakfastMutableListData
    }
    fun ldtGoatMonanMeal():LiveData<List<SearchMeal>>{
        return goatMutableListData
    }

    //
    fun ldtCategoryMealDaTa():LiveData<List<CategoriMeal>>{
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