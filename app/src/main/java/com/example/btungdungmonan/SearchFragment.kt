package com.example.btungdungmonan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btungdungmonan.activity.MonanActivity
import com.example.btungdungmonan.adapter.CustomAdapterFavorites
import com.example.btungdungmonan.databinding.FragmentFavorilesBinding
import com.example.btungdungmonan.databinding.FragmentSearchBinding
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.roomDatabase.DatabaseMeal
import com.example.btungdungmonan.videoModel.HomeVideoModel
import com.example.btungdungmonan.videoModel.HomeViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.http.Query


class SearchFragment : Fragment() {
    private lateinit var homeVideoModel: HomeVideoModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var customAdapterFavorites: CustomAdapterFavorites

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        customAdapterFavorites= CustomAdapterFavorites()
        // Khởi tạo đối tượng DatabaseMeal từ context
        val home=DatabaseMeal.getDatabaseMeal(requireContext())
        // Tạo một đối tượng HomeViewModelFactory và chuyển đối tượng DatabaseMeal vào nó
        val homeFactory=HomeViewModelFactory(home)
        // Sử dụng ViewModelProvider để tạo hoặc lấy ViewModel từ Factory đã được cung cấp
        homeVideoModel=ViewModelProvider(this,homeFactory)[HomeVideoModel::class.java]
        // Inflate the layout for this fragment
        binding=FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // adapter rcl
        Adapterrclseach()

        //kiem tra trong edt co du lieu k
        binding.imgNext.setOnClickListener {
            querySearch()
        }
        //auto search
        autosearch()

        // add adapter
        addAdapterRcl()
        //click Rcl
        ClickItemRcl()

        super.onViewCreated(view, savedInstanceState)
    }

    // xu ly khi click vao item rcl
    private fun ClickItemRcl() {
        // click chuyen man hinh
        customAdapterFavorites.clickItem={meal->
            //truyen du lieu
            val intent=Intent(requireContext(),MonanActivity::class.java)
            intent.putExtra("DATAID",meal.idMeal)
            intent.putExtra("DATANAME",meal.strMeal)
            intent.putExtra("DATATHUMB",meal.strMealThumb)
            startActivity(intent)
            git add  }
    }

    private fun autosearch() {
        var autosearch:Job?=null
        binding.extSearch.addTextChangedListener {search->
            // Hủy job trước nếu nó đang chạy
            autosearch?.cancel()
            // Khởi tạo một job mới bằng cách sử dụng lifecycleScope
            autosearch =lifecycleScope.launch {
                delay(500)
                // Gọi hàm trong homeVideoModel để lấy dữ liệu từ API với tham số là chuỗi tìm kiếm
                homeVideoModel.getsearchMealApi(search.toString())
            }
        }

    }

    private fun addAdapterRcl() {
        // Sử dụng LiveData để quan sát dữ liệu từ homeVideoModel.ldtsearchMeal()
        homeVideoModel.ldtsearchMeal().observe(viewLifecycleOwner, Observer {meal->
            // Khi dữ liệu thay đổi, gửi nó vào Adapter để cập nhật RecyclerView
            customAdapterFavorites.differ.submitList(meal)
        })
    }

    private fun querySearch() {
        val querySearch=binding.extSearch.text.toString()
        // Kiểm tra xem trường nhập liệu có giá trị không rỗng
        if (querySearch.isNotEmpty())
        {
            // Nếu có giá trị, gọi hàm trong homeVideoModel để thực hiện tìm kiếm dựa trên truy vấn
            homeVideoModel.getsearchMealApi(querySearch)
        }
        Log.d("querySearch","LOI")
    }

    private fun Adapterrclseach() {
        // Ánh xạ RecyclerView từ layout
        binding.rclSeach.apply {
            // Cấu hình LayoutManager cho RecyclerView - ở đây sử dụng GridLayoutManager với 2 cột
            layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
            // Thiết lập Adapter cho RecyclerView - customAdapterFavorites là một Adapter đã được khởi tạo trước đó
            adapter=customAdapterFavorites
        }
    }
}