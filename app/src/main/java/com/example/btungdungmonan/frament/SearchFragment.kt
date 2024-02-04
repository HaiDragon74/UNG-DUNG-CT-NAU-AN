package com.example.btungdungmonan.frament

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btungdungmonan.R
import com.example.btungdungmonan.activity.InstructionActivity
import com.example.btungdungmonan.adapter.FavoritesAdapter
import com.example.btungdungmonan.databinding.FragmentSearchBinding
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var homeFragment: HomeFragment
    private lateinit var favoritesAdapter: FavoritesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        homeFragment= HomeFragment()
        favoritesAdapter = FavoritesAdapter()
        // KHỞI TẠO ĐỐI TƯỢNG DATABASEMEAL TỪ CONTEXT
        val dataRomMeal = DatabaseMeal.getDatabaseMeal(requireContext())
        // TẠO MỘT ĐỐI TƯỢNG HOMEVIEWMODELFACTORY VÀ CHUYỂN ĐỐI TƯỢNG DATABASEMEAL VÀO NÓ
        val homeViewModelFactory = HomeViewModelFactory(dataRomMeal)
        // SỬ DỤNG VIEWMODELPROVIDER ĐỂ TẠO HOẶC LẤY VIEWMODEL TỪ FACTORY ĐÃ ĐƯỢC CUNG CẤP
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        //chuyển con trỏ chuột vào edt tim kiếm
        binding.edtSearch.requestFocus()
        // HIỂN THỊ BÀN PHÍM ẢO
        showSoftKeyboard()
    }
    //HIỂN THỊ BÀN PHÍM ẢO
    private fun showSoftKeyboard() {
        // LẤY RA inputMethodManager
        val inputMethodManager = requireActivity().getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // SHOW_IMPLICIT tự động hiển thị
        inputMethodManager.showSoftInput(binding.edtSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // ADAPTER RCL
        Adapterrclseach()
        //KIEM TRA TRONG EDT CO DU LIEU K
        binding.imgBlack.setOnClickListener {
            querySearch()
            //TAT BAN PHIM AO
            closeKeyboard()
        }
        //AUTO SEARCH
        autoSearch()
        // ADD ADAPTER
        AdapterSearch()
        //CLICK RCL
        ClickItemRclSearch()
        //TÌM KIẾM KHI EDT CHƯA CÓ DỮ LIỆU(TRỐNG)
        showSuggestions()
        onBlack()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun onBlack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            childFragmentManager.beginTransaction().apply {
                add(R.id.framentLayoutHome,homeFragment)
                commit()
                binding.framentLayoutHome.visibility=View.VISIBLE
            }
        }
    }

    private fun closeKeyboard() {
        val inputMethodManager=requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken,InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    private fun showSuggestions() {
        // GỌI HÀM TRONG HOMEVIDEOMODEL ĐỂ LẤY DỮ LIỆU TỪ API VỚI THAM SỐ LÀ CHUỖI TÌM KIẾM
        homeViewModel.getsearchMealApi("")   //TÌM KIẾM KHI EDT CHƯA CÓ ĐỮ LIỆU
    }
    // XU LY KHI CLICK VAO ITEM RCL
    private fun ClickItemRclSearch() {
        // CLICK CHUYEN MAN HINH
        favoritesAdapter.clickMeal = { meal ->
            //TRUYEN DU LIEU
            val intent = Intent(requireContext(), InstructionActivity::class.java)
            intent.putExtra("DATAID", meal.idMeal)
            intent.putExtra("DATANAME", meal.strMeal)
            intent.putExtra("DATATHUMB", meal.strMealThumb)
            startActivity(intent)
        }
    }
    //LỌC TÌM KIẾM
    private fun autoSearch() {
        var autosearch: Job? = null
        binding.edtSearch.addTextChangedListener { search ->
            // HỦY JOB TRƯỚC NẾU NÓ ĐANG CHẠY
            autosearch?.cancel()
            // KHỞI TẠO MỘT JOB MỚI BẰNG CÁCH SỬ DỤNG LIFECYCLESCOPE
            autosearch = lifecycleScope.launch {
                     // HẠN CHẾ TẦN SUẤT TÌM KIẾM
                delay(500)
                    // GỌI HÀM TRONG HOMEVIDEOMODEL ĐỂ LẤY DỮ LIỆU TỪ API VỚI THAM SỐ LÀ CHUỖI TÌM KIẾM
                    homeViewModel.getsearchMealApi(search.toString())
            }
        }
    }
    private fun AdapterSearch() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT DỮ LIỆU TỪ HOMEVIDEOMODEL.LDTSEARCHMEAL()
        homeViewModel.ldtsearchMeal().observe(viewLifecycleOwner, Observer { meal ->
            // KHI DỮ LIỆU THAY ĐỔI, GỬI NÓ VÀO ADAPTER ĐỂ CẬP NHẬT RECYCLERVIEW
            favoritesAdapter.differ.submitList(meal)
        })
    }

    private fun querySearch() {
       childFragmentManager.beginTransaction().apply {
           replace(R.id.framentLayoutHome,homeFragment)
           commit()
           binding.framentLayoutHome.visibility= View.VISIBLE
       }
    }

    private fun Adapterrclseach() {
        // ÁNH XẠ RECYCLERVIEW TỪ LAYOUT
        binding.rclSeach.apply {
            // CẤU HÌNH LAYOUTMANAGER CHO RECYCLERVIEW - Ở ĐÂY SỬ DỤNG GRIDLAYOUTMANAGER VỚI 2 CỘT
            layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            // THIẾT LẬP ADAPTER CHO RECYCLERVIEW - CUSTOMADAPTERFAVORITES LÀ MỘT ADAPTER ĐÃ ĐƯỢC KHỞI TẠO TRƯỚC ĐÓ
            adapter = favoritesAdapter
        }
    }

}
