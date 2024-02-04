package com.example.btungdungmonan.frament

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.btungdungmonan.activity.InstructionActivity
import com.example.btungdungmonan.databinding.FragmentBottomMealBinding
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
private const val MEAL_ID = "param1"
class BottomSearchFragment : BottomSheetDialogFragment() {

    private var mealId: String? = null
    private lateinit var homeViewModel: HomeViewModel
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
        val dataRomMeal = DatabaseMeal.getDatabaseMeal(requireContext())
        // KHỞI TẠO ĐỐI TƯỢNG HOMEVIEWMODELFACTORY VỚI ĐỐI SỐ LÀ ĐỐI TƯỢNG DATABASEMEAL
        val viewModelFactory = HomeViewModelFactory(dataRomMeal)
        // SỬ DỤNG VIEWMODELPROVIDER ĐỂ LẤY HOẶC TẠO MỚI VIEWMODEL CỦA LỚP HOMEVIDEOMODEL
        homeViewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        binding = FragmentBottomMealBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    //FRAMENT
    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            BottomSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(MEAL_ID, param1) //MEAL_ID
                }
            }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mealId?.let { homeViewModel.getAllMealBottomFrament(it) }
        //LẤY DỮ LIỆU MEAL
        setdataBottomMeal()
        //CLICK BOTTOM
        clickBottom()
        super.onViewCreated(view, savedInstanceState)
    }
    private var getmeal: Meal? = null
    private fun setdataBottomMeal() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT THAY ĐỔI TRONG DỮ LIỆU MÓN ĂN
        homeViewModel.ldtBottomMeal().observe(viewLifecycleOwner, Observer { meal ->
            // HIỂN THỊ HÌNH ẢNH MÓN ĂN TRONG IMAGEVIEW BẰNG THƯ VIỆN GLIDE
            Glide.with(this).load(meal.strMealThumb).into(binding.imgbottom)
            // HIỂN THỊ THÔNG TIN VÙNG MIỀN, TÊN MÓN ĂN, VÀ DANH MỤC CỦA MÓN ĂN TRONG TEXTVIEWS
            binding.tvArea.text = meal.strArea
            binding.tvMeal.text = meal.strMeal
            binding.tvCategory.text = meal.strCategory
            // LƯU THÔNG TIN MÓN ĂN CHO SỬ DỤNG SAU NÀY
            getmeal = meal
        })
    }
    private fun clickBottom() {
        binding.bottom.setOnClickListener {
            // TẠO INTENT ĐỂ CHUYỂN DỮ LIỆU SANG MONANACTIVITY
            val intent = Intent(requireContext(), InstructionActivity::class.java)
            // ĐẶT DỮ LIỆU CẦN CHUYỂN THEO CÁC KHÓA TƯƠNG ỨNG
            intent.putExtra("DATAID", getmeal!!.idMeal)
            intent.putExtra("DATANAME", getmeal!!.strArea)
            intent.putExtra("DATATHUMB", getmeal!!.strMealThumb)
            // KHỞI CHẠY MONANACTIVITY VỚI INTENT ĐÃ ĐƯỢC CẤU HÌNH
            startActivity(intent)
        }
    }

}