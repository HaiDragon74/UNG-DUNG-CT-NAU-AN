package com.example.btungdungmonan.frament

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btungdungmonan.activity.CategoryActivity
import com.example.btungdungmonan.activity.InstructionActivity
import com.example.btungdungmonan.adapter.CatevoriesAdapter
import com.example.btungdungmonan.databinding.FragmentCatevoriesBinding
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory

class CatevoriesFragment : Fragment() {
    private lateinit var binding: FragmentCatevoriesBinding
    private lateinit var catevoriesAdapter: CatevoriesAdapter
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        // KHỞI TẠO ĐỐI TƯỢNG DATABASEMEAL TỪ LỚP COMPANION OBJECT
        val dataRomMeal = DatabaseMeal.getDatabaseMeal(requireContext())
        // KHỞI TẠO ĐỐI TƯỢNG HOMEVIEWMODELFACTORY VỚI ĐỐI SỐ LÀ ĐỐI TƯỢNG DATABASEMEAL
        val homeViewModelFactory = HomeViewModelFactory(dataRomMeal)
        // SỬ DỤNG VIEWMODELPROVIDER ĐỂ LẤY HOẶC TẠO MỚI VIEWMODEL CỦA LỚP HOMEVIDEOMODEL
        homeViewModel = ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
        catevoriesAdapter = CatevoriesAdapter()
        binding = FragmentCatevoriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //AD RCL VAO CATEVORIES
        rclCatevories()
        // LDT CATEVORIES
        homeViewModel.getCategoryMeal()
        adaterCatevories()
        //CLICK ITEM RCL Category
        clickRclCategory()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun clickRclCategory() {
        catevoriesAdapter.clickCategory={categoriMeal->
            val intent=Intent(requireContext(),CategoryActivity::class.java)
            intent.putExtra("NAMECATEGORI",categoriMeal.strCategory)
            startActivity(intent)
        }
    }

    private fun adaterCatevories() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT THAY ĐỔI TRONG DANH SÁCH CÁC MỤC ĂN
        homeViewModel.ldtCategoryMealDaTa().observe(viewLifecycleOwner, Observer { CategoriesMeal ->
            // KHI DỮ LIỆU THAY ĐỔI, CẬP NHẬT DANH SÁCH MỤC ĂN TRONG ADAPTER
            catevoriesAdapter.setDocMonAn(listCategory = CategoriesMeal as MutableList<CategoriMeal>)
        })
    }
    private fun rclCatevories() {
        binding.rclCaevories.adapter = catevoriesAdapter
        binding.rclCaevories.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }
}