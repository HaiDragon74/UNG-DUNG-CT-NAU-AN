package com.example.btungdungmonan.frament

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.btungdungmonan.R
import com.example.btungdungmonan.activity.InstructionActivity
import com.example.btungdungmonan.adapter.SeafoodAdapter
import com.example.btungdungmonan.adapter.AdapterCustom2rcl.ParentAdapter
import com.example.btungdungmonan.adapter.AdapterCustom2rcl.ParentChildAdapter
import com.example.btungdungmonan.databinding.FragmentHomeBinding
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var meal: Meal
    private lateinit var seafoodAdapter: SeafoodAdapter
    private var searchFragment = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //VIEWMODEL
        val dataRomMeal = DatabaseMeal.getDatabaseMeal(requireContext())
        val dataViewModelFactory = HomeViewModelFactory(dataRomMeal)
        homeViewModel = ViewModelProvider(this, dataViewModelFactory)[HomeViewModel::class.java]
        seafoodAdapter = SeafoodAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        // KAI BAO BINDING
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dataRanDom.visibility=View.GONE
        data()
        binding.srlReset.setOnRefreshListener{
            //DATA
            binding.dataRanDom.visibility=View.GONE
            binding.probarImgRanDom.visibility=View.VISIBLE
            data()
            Handler(Looper.getMainLooper()).postDelayed({
                binding.srlReset.isRefreshing=false

            },50)
        }
    }
    private fun data() {
        // TRUYEN ANH VAO IMG
        homeViewModel.getAllmeal()
        ldtRanDomMeal()
        //CLICK IMG RANDOM
        clickImgRanDom()
        //TÌM MÓN ĂN HIÊN LÊN RCL Seafood
        homeViewModel.getSeafoodApi()
        rclSeafood()
        adapterSeafood()
        putDataSearch()
        //ADAPTER
        homeViewModel.getCategoryMeal()
        //CLICK LONG ITEMNGANG
        clickLongItemNgang()
        //CLICKSACHHOME()
        binding.linearLayoutSearch.setOnClickListener {
            clickSearch()
        }
        homeViewModel.getBeefApi()
        homeViewModel.getChickenApi()
        tabLayot()
    }

    private fun tabLayot() {
        binding.viewPager.adapter=com.example.btungdungmonan.tablayout.Adapter(childFragmentManager,lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tabLayout, position ->
            when (position) {
                0 -> tabLayout.text = "All"
                1 -> tabLayout.text = "Beef"
                2 -> tabLayout.text = "Chicken"
                3 -> tabLayout.text = "Dessert"
                4 -> tabLayout.text = "Lamb"
                5 -> tabLayout.text = "Miscellaneous"
                6 -> tabLayout.text = "Pasta"
                7 -> tabLayout.text = "Pork"
                8 -> tabLayout.text = "Seafood"
                9 -> tabLayout.text = "Side"
                10 -> tabLayout.text = "Starter"
                11 -> tabLayout.text = "Vegan"
                12 -> tabLayout.text = "Vegetarian"
                13 -> tabLayout.text = "Breakfast"
                14 -> tabLayout.text = "Goat"
            }
        }.attach()
        binding.viewPager.isUserInputEnabled = false
    }
    private fun clickSearch() {
        childFragmentManager.beginTransaction().apply {
            // THAY THẾ FRAGMENT HIỆN TẠI BẰNG FRAGMENT MỚI (TIMKIEM)
            replace(R.id.fragmentlayoutSearch, searchFragment)
            // ÁP DỤNG CÁC THAY ĐỔI VÀO FRAGMENTTRANSACTION
            commit()
            // HIỂN THỊ FRAGMENTLAYOUT
            binding.fragmentlayoutSearch.visibility = View.VISIBLE
        }
    }
    private fun clickLongItemNgang() {
        // ĐOẠN MÃ XỬ LÝ KHI MỘT MỤC TRONG RECYCLERVIEW ĐƯỢC CLICK
        seafoodAdapter.clickSearchLong = { searchMeal ->
            val bottomSearchFragment = BottomSearchFragment.newInstance(searchMeal.idMeal)
            bottomSearchFragment.show(childFragmentManager, "MEAL_ID")
        }
    }
    private fun putDataSearch() {
        // SỬ DỤNG PROPERTY CLICKITEM TRONG ADAPTER ĐỂ ĐỊNH NGHĨA HÀNH ĐỘNG KHI MỘT MỤC ĐƯỢC CLICK
        seafoodAdapter.clickSearch = { searchMeal ->
            // TẠO INTENT ĐỂ CHUYỂN DỮ LIỆU SANG MONANACTIVITY
            var intent = Intent(activity, InstructionActivity::class.java)
            intent.putExtra("DATAID", searchMeal.idMeal)
            intent.putExtra("DATANAME", searchMeal.strMeal)
            intent.putExtra("DATATHUMB", searchMeal.strMealThumb)
            // KHỞI CHẠY MONANACTIVITY VỚI INTENT ĐÃ ĐƯỢC CẤU HÌNH
            startActivity(intent)
        }
    }
    private fun adapterSeafood() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT DANH SÁCH MÓN ĂN
        homeViewModel.ldtseafoodMealData().observe(
            viewLifecycleOwner
        ) { listsearchMeal ->
            // KHI DỮ LIỆU THAY ĐỔI, CẬP NHẬT DANH SÁCH MÓN ĂN VÀO ADAPTER
            seafoodAdapter.setlistmonan(listSearch = listsearchMeal as MutableList<SearchMeal>)
        }

    }
    private fun rclSeafood() {
        // GẮN LAYOUTMANAGER VÀ ADAPTER CHO RECYCLERVIEW CÓ ID LÀ RCLPHOBIEN
        binding.rclParentChild.apply {
            // CẤU HÌNH LAYOUTMANAGER CHO RECYCLERVIEW - Ở ĐÂY SỬ DỤNG LINEARLAYOUTMANAGER VỚI CHIỀU NGANG
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            // THIẾT LẬP ADAPTER CHO RECYCLERVIEW - MONANADAPTER LÀ MỘT ADAPTER ĐÃ ĐƯỢC KHỞI TẠO TRƯỚC ĐÓ
            adapter = seafoodAdapter
        }
    }
    private fun clickImgRanDom() {
        binding.imgRanDomMeal.setOnClickListener {
            // CHUYEN DU LIEU SANG MAN MONANACTIVITY
            val intent = Intent(activity, InstructionActivity::class.java)
            // ĐƯA DỮ LIỆU CẦN CHUYỂN VÀO INTENT
            intent.putExtra("DATAID", meal.idMeal)
            intent.putExtra("DATANAME", meal.strMeal)
            intent.putExtra("DATATHUMB", meal.strMealThumb)
            // KHỞI CHẠY MONANACTIVITY VỚI INTENT ĐÃ ĐƯỢC CẤU HÌNH
            startActivity(intent)
        }
    }
    private fun ldtRanDomMeal() {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ URL VÀ HIỂN THỊ TRONG IMAGEVIEW (BINDING.IMGMONAN)
        homeViewModel.ldtRanDomMeal().observe(
            viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imgRanDomMeal)
            binding.imgNameRanDom.text=value.strMeal
            // LƯU GIỮ DỮ LIỆU BỮA ĂN NGẪU NHIÊN ĐỂ SỬ DỤNG NẾU CẦN THIẾT
            this@HomeFragment.meal = value
            binding.dataRanDom.visibility=View.VISIBLE
            binding.probarImgRanDom.visibility=View.GONE
        }
    }

}
