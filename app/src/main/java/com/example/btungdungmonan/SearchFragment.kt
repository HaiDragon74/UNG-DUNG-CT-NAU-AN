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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        customAdapterFavorites = CustomAdapterFavorites()
        // KHỞI TẠO ĐỐI TƯỢNG DATABASEMEAL TỪ CONTEXT
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        // TẠO MỘT ĐỐI TƯỢNG HOMEVIEWMODELFACTORY VÀ CHUYỂN ĐỐI TƯỢNG DATABASEMEAL VÀO NÓ
        val homeFactory = HomeViewModelFactory(home)
        // SỬ DỤNG VIEWMODELPROVIDER ĐỂ TẠO HOẶC LẤY VIEWMODEL TỪ FACTORY ĐÃ ĐƯỢC CUNG CẤP
        homeVideoModel = ViewModelProvider(this, homeFactory)[HomeVideoModel::class.java]
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ADAPTER RCL
        Adapterrclseach()
        //KIEM TRA TRONG EDT CO DU LIEU K
        binding.imgNext.setOnClickListener {
            querySearch()
        }
        //AUTO SEARCH
        autosearch()
        // ADD ADAPTER
        addAdapterRcl()
        //CLICK RCL
        ClickItemRcl()
        super.onViewCreated(view, savedInstanceState)
    }
    // XU LY KHI CLICK VAO ITEM RCL
    private fun ClickItemRcl() {
        // CLICK CHUYEN MAN HINH
        customAdapterFavorites.clickItem = { meal ->
            //TRUYEN DU LIEU
            val intent = Intent(requireContext(), MonanActivity::class.java)
            intent.putExtra("DATAID", meal.idMeal)
            intent.putExtra("DATANAME", meal.strMeal)
            intent.putExtra("DATATHUMB", meal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun autosearch() {
        var autosearch: Job? = null
        binding.extSearch.addTextChangedListener { search ->
            // HỦY JOB TRƯỚC NẾU NÓ ĐANG CHẠY
            autosearch?.cancel()
            // KHỞI TẠO MỘT JOB MỚI BẰNG CÁCH SỬ DỤNG LIFECYCLESCOPE
            autosearch = lifecycleScope.launch {
                delay(500)
                // GỌI HÀM TRONG HOMEVIDEOMODEL ĐỂ LẤY DỮ LIỆU TỪ API VỚI THAM SỐ LÀ CHUỖI TÌM KIẾM
                homeVideoModel.getsearchMealApi(search.toString())
            }
        }
    }
    private fun addAdapterRcl() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT DỮ LIỆU TỪ HOMEVIDEOMODEL.LDTSEARCHMEAL()
        homeVideoModel.ldtsearchMeal().observe(viewLifecycleOwner, Observer { meal ->
            // KHI DỮ LIỆU THAY ĐỔI, GỬI NÓ VÀO ADAPTER ĐỂ CẬP NHẬT RECYCLERVIEW
            customAdapterFavorites.differ.submitList(meal)
        })
    }
    private fun querySearch() {
        val querySearch = binding.extSearch.text.toString()
        // KIỂM TRA XEM TRƯỜNG NHẬP LIỆU CÓ GIÁ TRỊ KHÔNG RỖNG
        if (querySearch.isNotEmpty()) {
            // NẾU CÓ GIÁ TRỊ, GỌI HÀM TRONG HOMEVIDEOMODEL ĐỂ THỰC HIỆN TÌM KIẾM DỰA TRÊN TRUY VẤN
            homeVideoModel.getsearchMealApi(querySearch)
        }
        Log.d("querySearch", "LOI")
    }
    private fun Adapterrclseach() {
        // ÁNH XẠ RECYCLERVIEW TỪ LAYOUT
        binding.rclSeach.apply {
            // CẤU HÌNH LAYOUTMANAGER CHO RECYCLERVIEW - Ở ĐÂY SỬ DỤNG GRIDLAYOUTMANAGER VỚI 2 CỘT
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            // THIẾT LẬP ADAPTER CHO RECYCLERVIEW - CUSTOMADAPTERFAVORITES LÀ MỘT ADAPTER ĐÃ ĐƯỢC KHỞI TẠO TRƯỚC ĐÓ
            adapter = customAdapterFavorites
        }
    }
}

