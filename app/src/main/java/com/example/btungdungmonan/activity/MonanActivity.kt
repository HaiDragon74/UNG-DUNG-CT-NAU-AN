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

        val mealDatabase= DatabaseMeal.getDatabaseMeal(this)
        val mealViewModelFactory= MealViewModelFactory(mealDatabase)
        monanModel= ViewModelProvider(this,mealViewModelFactory)[MonanViewModel::class.java]

        getdatamonan()  // chuyen activity truyen du lieu

        setdatamonan()  // set img homfracmnt
        loadinglpi()   // hien load

        monanModel.getlivedata(monanId)
        monanLiveDaTa()     // set img txt MonanActivity

        // imgyoutobe
        youtobeclick()


        //click trai tim luu mon an lai
        clickFavorite()




    }

    private fun clickFavorite() {
        binding.fabtntim.setOnClickListener {
            // Thực hiện việc chèn dữ liệu vào cơ sở dữ liệu thông qua monanModel
                monanModel.insertDataMeal(saveMeal!!)
                Toast.makeText(this,"add ${saveMeal!!.strMeal} to favorites successfully",Toast.LENGTH_SHORT).show()


        }
    }

    private fun youtobeclick() {
        binding.imgyoutobe.setOnClickListener {
            // Tạo một Intent để mở một trình duyệt web và chuyển đến đường dẫn YouTube (youtobeclick)
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtobeclick))
            startActivity(intent)
        }
    }
    private var saveMeal:Meal?=null // lay ra luu
    private fun monanLiveDaTa() {
        // Sử dụng LiveData để quan sát sự thay đổi trong dữ liệu từ monanModel
        monanModel.obslivedata().observe(this
        ) { value ->
            saveMeal = value  // lay ra luu
            // Gọi hàm offloadinglpi() để thực hiện các xử lý liên quan đến hiển thị
            offloadinglpi()
            // Cập nhật các TextViews để hiển thị thông tin món ăn
            binding.txtthit.text = "Category ${value.strCategory}"
            binding.txtkhuvuc.text = "Area ${value.strArea}"
            binding.txthuongdan.text = value.strInstructions
            // Lưu đường dẫn YouTube vào biến youtobeclick
            youtobeclick = value.strYoutube
        }
    }

    private fun setdatamonan() {
        // Sử dụng thư viện Glide để tải hình ảnh vào ImageView
        Glide.with(applicationContext)
            .load(monanThumb) // Đường dẫn của hình ảnh
            .into(binding.imgtollbar)// ImageView để hiển thị hình ảnh

        // Cập nhật tiêu đề của CollapsingToolbarLayout
        binding.ctollbar.title=monanName
        // Đặt màu cho tiêu đề mở rộng của CollapsingToolbarLayout
        binding.ctollbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getdatamonan() {
        // Lấy Intent từ Activity hoặc Fragment hiện tại
        val intent=intent
        monanId= intent.getStringExtra("DATAID").toString()
        monanName=intent.getStringExtra("DATANAME")!!
        monanThumb=intent.getStringExtra("DATATHUMB")!!
    }
    private fun loadinglpi(){
        // Hiển thị LoadingProgressBar
        binding.lpiload.visibility=View.VISIBLE
        // Ẩn FloatingActionButton
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