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

private const val MEAL_ID = "param1"

class BottomMealFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
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
        // KHỞI TẠO ĐỐI TƯỢNG DATABASEMEAL TỪ LỚP COMPANION OBJECT
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        // KHỞI TẠO ĐỐI TƯỢNG HOMEVIEWMODELFACTORY VỚI ĐỐI SỐ LÀ ĐỐI TƯỢNG DATABASEMEAL
        val homeFactory = HomeViewModelFactory(home)
        // SỬ DỤNG VIEWMODELPROVIDER ĐỂ LẤY HOẶC TẠO MỚI VIEWMODEL CỦA LỚP HOMEVIDEOMODEL
        homeVideoModel = ViewModelProvider(this, homeFactory)[HomeVideoModel::class.java]
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        binding = FragmentBottomMealBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    //FRAMENT
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
        //LẤY DỮ LIỆU MEAL
        ldtBottomMeal()
        //CLICK BOTTOM
        clickBottom()
        super.onViewCreated(view, savedInstanceState)
    }
    private var getmeal: Meal? = null
    private fun ldtBottomMeal() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT THAY ĐỔI TRONG DỮ LIỆU MÓN ĂN
        homeVideoModel.ldtBottomMeal().observe(viewLifecycleOwner, Observer { meal ->
            // HIỂN THỊ HÌNH ẢNH MÓN ĂN TRONG IMAGEVIEW BẰNG THƯ VIỆN GLIDE
            Glide.with(this).load(meal.strMealThumb).into(binding.imgbottom)
            // HIỂN THỊ THÔNG TIN VÙNG MIỀN, TÊN MÓN ĂN, VÀ DANH MỤC CỦA MÓN ĂN TRONG TEXTVIEWS
            binding.txtArea.text = meal.strArea
            binding.txtSheetMeal.text = meal.strMeal
            binding.txtCategory.text = meal.strCategory
            // LƯU THÔNG TIN MÓN ĂN CHO SỬ DỤNG SAU NÀY
            getmeal = meal
        })
    }
    private fun clickBottom() {
        binding.bottom.setOnClickListener {
            // TẠO INTENT ĐỂ CHUYỂN DỮ LIỆU SANG MONANACTIVITY
            val intent = Intent(requireContext(), MonanActivity::class.java)
            // ĐẶT DỮ LIỆU CẦN CHUYỂN THEO CÁC KHÓA TƯƠNG ỨNG
            intent.putExtra("DATAID", getmeal!!.idMeal)
            intent.putExtra("DATANAME", getmeal!!.strArea)
            intent.putExtra("DATATHUMB", getmeal!!.strMealThumb)
            // KHỞI CHẠY MONANACTIVITY VỚI INTENT ĐÃ ĐƯỢC CẤU HÌNH
            startActivity(intent)
        }
    }

}