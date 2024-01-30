package com.example.btungdungmonan.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btungdungmonan.adapter.CustomitemdocAdapter
import com.example.btungdungmonan.databinding.ActivityItemdocActivytyBinding
import com.example.btungdungmonan.imgmonan.MonAnMeal
import com.example.btungdungmonan.videoModel.ItemDocViewModel

class ItemdocActivyty : AppCompatActivity() {
    private lateinit var nameDaTaDoc: String
    private lateinit var binding: ActivityItemdocActivytyBinding
    private lateinit var itemDocViewModel: ItemDocViewModel
    private lateinit var itemdocAdapter: CustomitemdocAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ///LATEINIT
        binding = ActivityItemdocActivytyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemDocViewModel = ViewModelProvider(this)[ItemDocViewModel::class.java]
        itemdocAdapter = CustomitemdocAdapter()
        // LAY DU LIEU HOMACTIVITY
        getdatahomeactibyti()
        // LAY DU LIEU TU API
        setApiAdapter()
        // GAN DL VAO RCL
        setrcl()
    }

    private fun setrcl() {
        // THIẾT LẬP RECYCLERVIEW
        binding.rclitem.apply {
            // Thiết lập LayoutManager
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = itemdocAdapter
        }

    }

    private fun setApiAdapter() {
        // GỌI PHƯƠNG THỨC GETFILTERITEMAPI() TỪ ITEMDOCVIEWMODEL ĐỂ LẤY DỮ LIỆU ĐƯỢC LỌC (LOC THEO TEN)
        itemDocViewModel.getFilterItemApi(nameDaTaDoc)
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT SỰ THAY ĐỔI TRONG DANH SÁCH ĐƯỢC LỌC
        itemDocViewModel.getldtFiltrItem().observe(this, Observer {
            it
            // TRUYỀN DANH SÁCH ĐƯỢC LỌC TỚI ADAPTER
            itemdocAdapter.getlistItem(listitem = it as MutableList<MonAnMeal>)
            Log.d("AAAAA", "$it")
        })
    }

    private fun getdatahomeactibyti() {
        // LẤY INTENT TỪ ACTIVITY HOẶC FRAGMENT HIỆN TẠI
        val intent = intent
        // LẤY GIÁ TRỊ CỦA "DATANAMEDOC" TỪ INTENT VÀ CHUYỂN THÀNH CHUỖI, SỬ DỤNG "!!"
        // ĐỂ BẢO ĐẢM KHÔNG NULL
        nameDaTaDoc = intent.getStringExtra("DATANAMEDOC")!!
        // THIẾT LẬP VĂN BẢN CHO TEXTVIEW "TXTITEMDOC" ĐỂ HIỂN THỊ THÔNG TIN LÀM MÓN
        binding.txtitemdoc.text = "How make dishes $nameDaTaDoc"
        Log.d("DATANAMEDOC", "$nameDaTaDoc")
    }
}