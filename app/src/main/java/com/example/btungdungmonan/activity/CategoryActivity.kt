package com.example.btungdungmonan.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btungdungmonan.adapter.CategoryAdapter
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.api.viewmodel.CatevoriViewModel
import com.example.btungdungmonan.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var nameCatevoriMeal: String
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var catevoriViewModel: CatevoriViewModel
    private lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ///LATEINIT
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        catevoriViewModel = ViewModelProvider(this)[CatevoriViewModel::class.java]
        categoryAdapter = CategoryAdapter()
        // LAY DU LIEU HOMACTIVITY
        getDataHome()
        // LAY DU LIEU TU API
        adapterCategoryMeal()
        // GAN DL VAO RCL
        rclCategoryMeal()
        //CLICK RCLCATEGORI
        clickRclCategory()
    }

    private fun clickRclCategory() {
        categoryAdapter.clickCategory={searchMeal ->
            val intent=Intent(this,InstructionActivity::class.java)
            intent.putExtra("DATAID",searchMeal.idMeal)
            intent.putExtra("DATANAME",searchMeal.strMeal)
            intent.putExtra("DATATHUMB",searchMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun rclCategoryMeal() {
        // THIẾT LẬP RECYCLERVIEW
        binding.rclCategoryMeal.apply {
            // Thiết lập LayoutManager
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = categoryAdapter
        }
    }

    private fun adapterCategoryMeal() {
        // GỌI PHƯƠNG THỨC GETFILTERITEMAPI() TỪ ITEMDOCVIEWMODEL ĐỂ LẤY DỮ LIỆU ĐƯỢC LỌC (LOC THEO TEN)
        catevoriViewModel.getFilterItemApi(nameCatevoriMeal)
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT SỰ THAY ĐỔI TRONG DANH SÁCH ĐƯỢC LỌC
        catevoriViewModel.getldtFiltrItem().observe(this, Observer {
            it
            // TRUYỀN DANH SÁCH ĐƯỢC LỌC TỚI ADAPTER
            categoryAdapter.getlistItem(lisSearch = it as MutableList<SearchMeal>)
            Log.d("AAAAA", "$it")
        })
    }
    private fun getDataHome() {
        // LẤY INTENT TỪ ACTIVITY HOẶC FRAGMENT HIỆN TẠI
        val intent = intent
        // LẤY GIÁ TRỊ CỦA "DATANAMEDOC" TỪ INTENT VÀ CHUYỂN THÀNH CHUỖI, SỬ DỤNG "!!"
        // ĐỂ BẢO ĐẢM KHÔNG NULL
        nameCatevoriMeal = intent.getStringExtra("NAMECATEGORI")!!
        // THIẾT LẬP VĂN BẢN CHO TEXTVIEW "TXTITEMDOC" ĐỂ HIỂN THỊ THÔNG TIN LÀM MÓN
        binding.tvCatevoriMeal.text = "How make dishes $nameCatevoriMeal"
        Log.d("QQQQWWWW", "$nameCatevoriMeal")
    }
}