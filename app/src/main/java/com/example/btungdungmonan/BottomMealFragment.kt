package com.example.btungdungmonan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.btungdungmonan.activity.MainActivity
import com.example.btungdungmonan.activity.MonanActivity
import com.example.btungdungmonan.databinding.FragmentBottomMealBinding
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.roomDatabase.DatabaseMeal
import com.example.btungdungmonan.videoModel.HomeVideoModel
import com.example.btungdungmonan.videoModel.HomeViewModelFactory
import com.example.btungdungmonan.videoModel.MealViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val MEAL_ID="param1"

class BottomMealFragment : BottomSheetDialogFragment() {
    private var mealId:String?=null
    private lateinit var homeVideoModel: HomeVideoModel
    private lateinit var binding: FragmentBottomMealBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Khởi tạo đối tượng DatabaseMeal từ lớp companion object
        val home =DatabaseMeal.getDatabaseMeal(requireContext())
        // Khởi tạo đối tượng HomeViewModelFactory với đối số là đối tượng DatabaseMeal
        val homeFactory=HomeViewModelFactory(home)
        // Sử dụng ViewModelProvider để lấy hoặc tạo mới ViewModel của lớp HomeVideoModel
        homeVideoModel=ViewModelProvider(this,homeFactory)[HomeVideoModel::class.java]

        // Inflate the layout for this fragment
        binding= FragmentBottomMealBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    //frament
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomMealFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1) //MEAL_ID
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mealId?.let { homeVideoModel.getMealBottom(it) }

        ldtBottomMeal()

        //click bottom
        clickBottom()


        super.onViewCreated(view, savedInstanceState)
    }
    private var getmeal:Meal?=null
    private fun ldtBottomMeal() {
        // Sử dụng LiveData để quan sát thay đổi trong dữ liệu món ăn
        homeVideoModel.ldtBottomMeal().observe(viewLifecycleOwner, Observer {meal->
            // Hiển thị hình ảnh món ăn trong ImageView bằng thư viện Glide
            Glide.with(this).load(meal.strMealThumb).into(binding.imgbottom)
            // Hiển thị thông tin vùng miền, tên món ăn, và danh mục của món ăn trong TextViews
            binding.txtArea.text=meal.strArea
            binding.txtSheetMeal.text=meal.strMeal
            binding.txtCategory.text=meal.strCategory
            // Lưu thông tin món ăn cho sử dụng sau này
            getmeal=meal

        })
    }

    private fun clickBottom(){
        binding.bottom.setOnClickListener {
            // Tạo Intent để chuyển dữ liệu sang MonanActivity
            val intent=Intent(requireContext(),MonanActivity::class.java)
            // Đặt dữ liệu cần chuyển theo các khóa tương ứng
            intent.putExtra("DATAID",getmeal!!.idMeal)
            intent.putExtra("DATANAME",getmeal!!.strArea)
            intent.putExtra("DATATHUMB",getmeal!!.strMealThumb)
            // Khởi chạy MonanActivity với Intent đã được cấu hình
            startActivity(intent)
        }


    }

}