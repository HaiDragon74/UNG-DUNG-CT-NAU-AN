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
    private lateinit var nameDaTaDoc:String
    private lateinit var binding: ActivityItemdocActivytyBinding
    private lateinit var itemDocViewModel: ItemDocViewModel
    private lateinit var itemdocAdapter:CustomitemdocAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityItemdocActivytyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemDocViewModel=ViewModelProvider(this)[ItemDocViewModel::class.java]
        itemdocAdapter=CustomitemdocAdapter()




        // lay du lieu homactivity
        getdatahomeactibyti()


        // lay du lieu tu api
        setApiAdapter()
        // gan dl vao rcl
        setrclItem()






    }

    private fun setrclItem() {
        // Thiết lập RecyclerView
        binding.rclitem.apply {
            // Thiết lập LayoutManager
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=itemdocAdapter
        }

    }


    private fun setApiAdapter() {
        // Gọi phương thức getFilterItemApi() từ itemDocViewModel để lấy dữ liệu được lọc (loc theo ten)
        itemDocViewModel.getFilterItemApi(nameDaTaDoc)
        // Sử dụng LiveData để quan sát sự thay đổi trong danh sách được lọc
        itemDocViewModel.getldtFiltrItem().observe(this,Observer{it
            // Truyền danh sách được lọc tới Adapter
            itemdocAdapter.getlistItem(listitem = it as MutableList<MonAnMeal>)
            Log.d("AAAAA","$it")

        })
    }

    private fun getdatahomeactibyti() {
        // Lấy Intent từ Activity hoặc Fragment hiện tại
        val intent=intent
        // Lấy giá trị của "DATANAMEDOC" từ Intent và chuyển thành chuỗi, sử dụng "!!"
        // để bảo đảm không null
        nameDaTaDoc=intent.getStringExtra("DATANAMEDOC")!!
        // Thiết lập văn bản cho TextView "txtitemdoc" để hiển thị thông tin làm món
        binding.txtitemdoc.text="How make dishes $nameDaTaDoc"
        Log.d("DATANAMEDOC","$nameDaTaDoc")
    }
}