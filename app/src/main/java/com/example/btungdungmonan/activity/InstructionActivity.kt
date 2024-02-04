package com.example.btungdungmonan.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.btungdungmonan.R
import com.example.btungdungmonan.databinding.ActivityInstructionBinding
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.InstructionViewModelFactory
import com.example.btungdungmonan.api.viewmodel.InstructionViewModel

class InstructionActivity : AppCompatActivity() {
    private lateinit var thumb:String
    private lateinit var id:String
    private lateinit var name:String
    private lateinit var youtobe:String
    private lateinit var instructionViewModel: InstructionViewModel
    private lateinit var binding: ActivityInstructionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityInstructionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //VIEWMODEL
        val dataromDatabase= DatabaseMeal.getDatabaseMeal(this)
        val instructionViewModelFactory= InstructionViewModelFactory(dataromDatabase)
        instructionViewModel= ViewModelProvider(this,instructionViewModelFactory)[InstructionViewModel::class.java]
        //TRUYEN DU LIEU
        getDataMeal()
        //SET IMG
        setData()
        // HIEN LOAD
        loadinglpi()
        //LAY DU LIEU MEAL
        instructionViewModel.getlivedata(id)
        apiSetData()
        // IMGYOUTOBE
        youtobeclick()
        //CLICK TRAI TIM LUU MON AN LAI
        clickFavorite()
    }
    private fun clickFavorite() {
        binding.fabtnFavorite.setOnClickListener {
                // THỰC HIỆN VIỆC CHÈN DỮ LIỆU VÀO CƠ SỞ DỮ LIỆU THÔNG QUA MONANMODEL
            instructionViewModel.insertDataMeal(saveMeal!!)
                Toast.makeText(this,"add ${saveMeal!!.strMeal} to favorites successfully",Toast.LENGTH_SHORT).show()
        }
    }
    private fun youtobeclick() {
        binding.imgYoutobe.setOnClickListener {
            // TẠO MỘT INTENT ĐỂ MỞ MỘT TRÌNH DUYỆT WEB VÀ CHUYỂN ĐẾN ĐƯỜNG DẪN YOUTUBE (YOUTOBECLICK)
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtobe))
            startActivity(intent)
        }
    }
    private var saveMeal: Meal?=null // LAY RA LUU
    private fun apiSetData() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT SỰ THAY ĐỔI TRONG DỮ LIỆU TỪ MONANMODEL
        instructionViewModel.obslivedata().observe(this
        ) { meal ->
            saveMeal = meal  // LAY RA LUU
            // GỌI HÀM OFFLOADINGLPI() ĐỂ THỰC HIỆN CÁC XỬ LÝ LIÊN QUAN ĐẾN HIỂN THỊ
            offLoadinglpi()
            // CẬP NHẬT CÁC TEXTVIEWS ĐỂ HIỂN THỊ THÔNG TIN MÓN ĂN
            binding.edtMeal.text = "Category ${meal.strCategory}"
            binding.tvMap.text = "Area ${meal.strArea}"
            binding.tvInstruction.text = meal.strInstructions
            // LƯU ĐƯỜNG DẪN YOUTUBE VÀO BIẾN YOUTOBECLICK
            youtobe = meal.strYoutube
        }
    }
    private fun setData() {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH VÀO IMAGEVIEW
        Glide.with(applicationContext)
            .load(thumb) // ĐƯỜNG DẪN CỦA HÌNH ẢNH
            .into(binding.imgtollbar)// IMAGEVIEW ĐỂ HIỂN THỊ HÌNH ẢNH

        // CẬP NHẬT TIÊU ĐỀ CỦA COLLAPSINGTOOLBARLAYOUT
        binding.ctollbar.title=name
        // ĐẶT MÀU CHO TIÊU ĐỀ MỞ RỘNG CỦA COLLAPSINGTOOLBARLAYOUT
        binding.ctollbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }
    private fun getDataMeal() {
        // LẤY INTENT TỪ ACTIVITY HOẶC FRAGMENT HIỆN TẠI
        val intent=intent
        id= intent.getStringExtra("DATAID").toString()
        name=intent.getStringExtra("DATANAME")!!
        thumb=intent.getStringExtra("DATATHUMB")!!
    }
    private fun loadinglpi(){
        // HIỂN THỊ LOADINGPROGRESSBAR
        binding.lpiload.visibility=View.VISIBLE
        // ẨN FLOATINGACTIONBUTTON
        binding.fabtnFavorite.visibility=View.INVISIBLE
        binding.edtMeal.visibility=View.INVISIBLE
        binding.tvMap.visibility=View.INVISIBLE
        binding.tvInstruction.visibility=View.INVISIBLE
        binding.imgYoutobe.visibility=View.INVISIBLE
    }
    private fun offLoadinglpi(){
        binding.lpiload.visibility=View.INVISIBLE
        binding.fabtnFavorite.visibility=View.VISIBLE
        binding.edtMeal.visibility=View.VISIBLE
        binding.tvMap.visibility=View.VISIBLE
        binding.tvInstruction.visibility=View.VISIBLE
        binding.imgYoutobe.visibility=View.VISIBLE
    }
}
