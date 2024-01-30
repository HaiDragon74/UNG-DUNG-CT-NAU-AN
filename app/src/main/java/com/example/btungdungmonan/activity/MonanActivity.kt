package com.example.btungdungmonan.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.btungdungmonan.R
import com.example.btungdungmonan.databinding.ActivityMonanBinding
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.roomDatabase.DatabaseMeal
import com.example.btungdungmonan.videoModel.MealViewModelFactory
import com.example.btungdungmonan.videoModel.MonanViewModel

class MonanActivity : AppCompatActivity() {
    private lateinit var monanThumb:String
    private lateinit var monanId:String
    private lateinit var monanName:String
    private lateinit var youtobeclick:String
    private lateinit var monanModel:MonanViewModel
    private lateinit var binding: ActivityMonanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMonanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //VIEWMODEL
        val mealDatabase= DatabaseMeal.getDatabaseMeal(this)
        val mealViewModelFactory= MealViewModelFactory(mealDatabase)
        monanModel= ViewModelProvider(this,mealViewModelFactory)[MonanViewModel::class.java]
        //TRUYEN DU LIEU
        datamonan()
        //SET IMG
        setdatamonan()
        // HIEN LOAD
        loadinglpi()
        //LAY DU LIEU MEAL
        monanModel.getlivedata(monanId)
        monanLiveDaTa()
        // IMGYOUTOBE
        youtobeclick()
        //CLICK TRAI TIM LUU MON AN LAI
        clickFavorite()
    }
    private fun clickFavorite() {
        binding.fabtntim.setOnClickListener {
                // THỰC HIỆN VIỆC CHÈN DỮ LIỆU VÀO CƠ SỞ DỮ LIỆU THÔNG QUA MONANMODEL
                monanModel.insertDataMeal(saveMeal!!)
                Toast.makeText(this,"add ${saveMeal!!.strMeal} to favorites successfully",Toast.LENGTH_SHORT).show()
        }
    }
    private fun youtobeclick() {
        binding.imgyoutobe.setOnClickListener {
            // TẠO MỘT INTENT ĐỂ MỞ MỘT TRÌNH DUYỆT WEB VÀ CHUYỂN ĐẾN ĐƯỜNG DẪN YOUTUBE (YOUTOBECLICK)
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtobeclick))
            startActivity(intent)
        }
    }
    private var saveMeal:Meal?=null // LAY RA LUU
    private fun monanLiveDaTa() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT SỰ THAY ĐỔI TRONG DỮ LIỆU TỪ MONANMODEL
        monanModel.obslivedata().observe(this
        ) { value ->
            saveMeal = value  // LAY RA LUU
            // GỌI HÀM OFFLOADINGLPI() ĐỂ THỰC HIỆN CÁC XỬ LÝ LIÊN QUAN ĐẾN HIỂN THỊ
            offloadinglpi()
            // CẬP NHẬT CÁC TEXTVIEWS ĐỂ HIỂN THỊ THÔNG TIN MÓN ĂN
            binding.txtthit.text = "Category ${value.strCategory}"
            binding.txtkhuvuc.text = "Area ${value.strArea}"
            binding.txthuongdan.text = value.strInstructions
            // LƯU ĐƯỜNG DẪN YOUTUBE VÀO BIẾN YOUTOBECLICK
            youtobeclick = value.strYoutube
        }
    }
    private fun setdatamonan() {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH VÀO IMAGEVIEW
        Glide.with(applicationContext)
            .load(monanThumb) // ĐƯỜNG DẪN CỦA HÌNH ẢNH
            .into(binding.imgtollbar)// IMAGEVIEW ĐỂ HIỂN THỊ HÌNH ẢNH

        // CẬP NHẬT TIÊU ĐỀ CỦA COLLAPSINGTOOLBARLAYOUT
        binding.ctollbar.title=monanName
        // ĐẶT MÀU CHO TIÊU ĐỀ MỞ RỘNG CỦA COLLAPSINGTOOLBARLAYOUT
        binding.ctollbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }
    private fun datamonan() {
        // LẤY INTENT TỪ ACTIVITY HOẶC FRAGMENT HIỆN TẠI
        val intent=intent
        monanId= intent.getStringExtra("DATAID").toString()
        monanName=intent.getStringExtra("DATANAME")!!
        monanThumb=intent.getStringExtra("DATATHUMB")!!
    }
    private fun loadinglpi(){
        // HIỂN THỊ LOADINGPROGRESSBAR
        binding.lpiload.visibility=View.VISIBLE
        // ẨN FLOATINGACTIONBUTTON
        binding.fabtntim.visibility=View.INVISIBLE
        binding.txtthit.visibility=View.INVISIBLE
        binding.txtkhuvuc.visibility=View.INVISIBLE
        binding.txthuongdan.visibility=View.INVISIBLE
        binding.imgyoutobe.visibility=View.INVISIBLE
    }
    private fun offloadinglpi(){
        binding.lpiload.visibility=View.INVISIBLE
        binding.fabtntim.visibility=View.VISIBLE
        binding.txtthit.visibility=View.VISIBLE
        binding.txtkhuvuc.visibility=View.VISIBLE
        binding.txthuongdan.visibility=View.VISIBLE
        binding.imgyoutobe.visibility=View.VISIBLE
    }
}