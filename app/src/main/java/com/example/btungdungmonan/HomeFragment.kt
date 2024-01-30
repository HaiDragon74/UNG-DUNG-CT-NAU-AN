package com.example.btungdungmonan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.btungdungmonan.activity.ItemdocActivyty
import com.example.btungdungmonan.activity.MonanActivity
import com.example.btungdungmonan.adapter.CustomDocAdapter
import com.example.btungdungmonan.adapter.CustomNgangadapter
import com.example.btungdungmonan.databinding.FragmentHomeBinding
import com.example.btungdungmonan.databinding.FragmentSearchBinding
import com.example.btungdungmonan.imgmonan.MonAnMeal
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.imgmonanrcl2kcop.docMeal
import com.example.btungdungmonan.roomDatabase.DatabaseMeal
import com.example.btungdungmonan.videoModel.HomeVideoModel
import com.example.btungdungmonan.videoModel.HomeViewModelFactory


class HomeFragment : Fragment() {
    private lateinit var homehvm: HomeVideoModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var ranDomMeal: Meal
    private lateinit var monAnAdapter: CustomNgangadapter
    private lateinit var docMonAnAdapter: CustomDocAdapter
    private var timkiem = SearchFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //VIEWMODEL
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homehvm = ViewModelProvider(this, homeFactory)[HomeVideoModel::class.java]
        monAnAdapter = CustomNgangadapter()
        docMonAnAdapter = CustomDocAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        // KAI BAO BINDING
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TRUYEN ANH VAO IMG
        homehvm.getrandommal()
        ldtRanDommeal()
        clickimghome()
        addRecyclerview()
        //ADAPTER
        homehvm.getfilerApi()
        ldtmonanmealdata()
        //CLICK
        clickItemrcl()
        //ADAPTER
        homehvm.getDocMonAnApi()
        getldtDocMonAn()
        //RCL
        rclDocMonAn()
        // ITEM CLICK RCL DOC , CHUYEN MAN ITEMDOCACTIVITY
        clickitemrcldoc()
        //CLICK LONG ITEMNGANG
        clickLongItemNgang()
        //CLICKSACHHOME()
        binding.imgSearchHome.setOnClickListener {
            clickSachHome()
        }
    }
    private fun clickSachHome() {
        childFragmentManager.beginTransaction().apply {
            // THAY THẾ FRAGMENT HIỆN TẠI BẰNG FRAGMENT MỚI (TIMKIEM)
            replace(R.id.fragmentlayout, timkiem)
            // ÁP DỤNG CÁC THAY ĐỔI VÀO FRAGMENTTRANSACTION
            commit()
            // HIỂN THỊ FRAGMENTLAYOUT
            binding.fragmentlayout.visibility = View.VISIBLE
        }
    }
    private fun clickLongItemNgang() {
        // ĐOẠN MÃ XỬ LÝ KHI MỘT MỤC TRONG RECYCLERVIEW ĐƯỢC CLICK
        monAnAdapter.clickLong = { monanmeal ->
            val bottomMealFragment = BottomMealFragment.newInstance(monanmeal.idMeal)
            bottomMealFragment.show(childFragmentManager, "MEAL_ID")
            Toast.makeText(requireContext(), "Hai vip", Toast.LENGTH_SHORT).show()
        }
    }
    private fun clickitemrcldoc() {
        // ĐOẠN MÃ XỬ LÝ KHI MỘT MỤC TRONG RECYCLERVIEW ĐƯỢC CLICK
        docMonAnAdapter.clickDocMonAn = { docMeal ->
            val intent = Intent(activity, ItemdocActivyty::class.java)
            intent.putExtra("DATANAMEDOC", docMeal.strCategory)
            startActivity(intent)
        }
    }
    private fun rclDocMonAn() {
        // CẤU HÌNH LAYOUTMANAGER CHO RECYCLERVIEW CÓ ID LÀ RCLMONAN
        binding.rclMonan.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.rclMonan.adapter = docMonAnAdapter
        // CHO PHÉP RECYCLERVIEW CÓ KHẢ NĂNG CUỘN LỒNG (NESTED SCROLLING)
        binding.rclMonan.isNestedScrollingEnabled = true
    }
    private fun getldtDocMonAn() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT DANH SÁCH MÓN ĂN THEO DẠNG LƯỚI
        homehvm.ldtDocMonAnDaTa().observe(viewLifecycleOwner) {
            it
            // KHI DỮ LIỆU THAY ĐỔI, CẬP NHẬT DANH SÁCH MÓN ĂN VÀO ADAPTER
            docMonAnAdapter.setDocMonAn(docMonAn = it as MutableList<docMeal>)
        }
    }
    private fun clickItemrcl() {
        // SỬ DỤNG PROPERTY CLICKITEM TRONG ADAPTER ĐỂ ĐỊNH NGHĨA HÀNH ĐỘNG KHI MỘT MỤC ĐƯỢC CLICK
        monAnAdapter.clickitem = { MonAnMeal ->
            // TẠO INTENT ĐỂ CHUYỂN DỮ LIỆU SANG MONANACTIVITY
            var intent = Intent(activity, MonanActivity::class.java)
            intent.putExtra("DATAID", MonAnMeal.idMeal)
            intent.putExtra("DATANAME", MonAnMeal.strMeal)
            intent.putExtra("DATATHUMB", MonAnMeal.strMealThumb)
            // KHỞI CHẠY MONANACTIVITY VỚI INTENT ĐÃ ĐƯỢC CẤU HÌNH
            startActivity(intent)
        }
    }
    private fun ldtmonanmealdata() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT DANH SÁCH MÓN ĂN
        homehvm.ldtMonanMealData().observe(
            viewLifecycleOwner
        ) { list ->
            // KHI DỮ LIỆU THAY ĐỔI, CẬP NHẬT DANH SÁCH MÓN ĂN VÀO ADAPTER
            monAnAdapter.setlistmonan(datalist = list as MutableList<MonAnMeal>)
        }
    }
    private fun addRecyclerview() {
        // GẮN LAYOUTMANAGER VÀ ADAPTER CHO RECYCLERVIEW CÓ ID LÀ RCLPHOBIEN
        binding.rclphobien.apply {
            // CẤU HÌNH LAYOUTMANAGER CHO RECYCLERVIEW - Ở ĐÂY SỬ DỤNG LINEARLAYOUTMANAGER VỚI CHIỀU NGANG
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            // THIẾT LẬP ADAPTER CHO RECYCLERVIEW - MONANADAPTER LÀ MỘT ADAPTER ĐÃ ĐƯỢC KHỞI TẠO TRƯỚC ĐÓ
            adapter = monAnAdapter
        }
    }
    private fun clickimghome() {
        binding.imgmonan.setOnClickListener {
            // CHUYEN DU LIEU SANG MAN MONANACTIVITY
            val intent = Intent(activity, MonanActivity::class.java)
            // ĐƯA DỮ LIỆU CẦN CHUYỂN VÀO INTENT
            intent.putExtra("DATAID", ranDomMeal.idMeal)
            intent.putExtra("DATANAME", ranDomMeal.strMeal)
            intent.putExtra("DATATHUMB", ranDomMeal.strMealThumb)
            // KHỞI CHẠY MONANACTIVITY VỚI INTENT ĐÃ ĐƯỢC CẤU HÌNH
            startActivity(intent)
        }
    }
    private fun ldtRanDommeal() {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ URL VÀ HIỂN THỊ TRONG IMAGEVIEW (BINDING.IMGMONAN)
        homehvm.ldtRanDomMeal().observe(
            viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imgmonan)
            // LƯU GIỮ DỮ LIỆU BỮA ĂN NGẪU NHIÊN ĐỂ SỬ DỤNG NẾU CẦN THIẾT
            this@HomeFragment.ranDomMeal = value
        }
    }
}