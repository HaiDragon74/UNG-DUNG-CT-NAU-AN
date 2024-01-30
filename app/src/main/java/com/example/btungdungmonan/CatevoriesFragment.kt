package com.example.btungdungmonan


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.btungdungmonan.activity.ItemdocActivyty
import com.example.btungdungmonan.adapter.CustomDocAdapter
import com.example.btungdungmonan.databinding.FragmentCatevoriesBinding
import com.example.btungdungmonan.imgmonanrcl2kcop.docMeal
import com.example.btungdungmonan.roomDatabase.DatabaseMeal
import com.example.btungdungmonan.videoModel.HomeVideoModel
import com.example.btungdungmonan.videoModel.HomeViewModelFactory

class CatevoriesFragment : Fragment() {
    private lateinit var binding: FragmentCatevoriesBinding
    private lateinit var customDocAdapter: CustomDocAdapter
    private lateinit var homeVideoModel: HomeVideoModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        // KHỞI TẠO ĐỐI TƯỢNG DATABASEMEAL TỪ LỚP COMPANION OBJECT
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        // KHỞI TẠO ĐỐI TƯỢNG HOMEVIEWMODELFACTORY VỚI ĐỐI SỐ LÀ ĐỐI TƯỢNG DATABASEMEAL
        val homeViewModelFactory = HomeViewModelFactory(home)
        // SỬ DỤNG VIEWMODELPROVIDER ĐỂ LẤY HOẶC TẠO MỚI VIEWMODEL CỦA LỚP HOMEVIDEOMODEL
        homeVideoModel = ViewModelProvider(this, homeViewModelFactory)[HomeVideoModel::class.java]
        customDocAdapter = CustomDocAdapter()
        binding = FragmentCatevoriesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //AD RCL VAO CATEVORIES
        addrclcatevories()
        // LDT CATEVORIES
        homeVideoModel.getDocMonAnApi()
        ldtcatevories()
        //CLICK ITEM
        clickItemrcl()
        super.onViewCreated(view, savedInstanceState)
    }
    private fun clickItemrcl() {
        customDocAdapter.clickDocMonAn = { docmeal ->
            val intent = Intent(requireContext(), ItemdocActivyty::class.java)
            intent.putExtra("DATANAMEDOC", docmeal.strCategory)
            startActivity(intent)
        }
    }
    private fun ldtcatevories() {
        // SỬ DỤNG LIVEDATA ĐỂ QUAN SÁT THAY ĐỔI TRONG DANH SÁCH CÁC MỤC ĂN
        homeVideoModel.ldtDocMonAnDaTa().observe(viewLifecycleOwner, Observer { docmeal ->
            // KHI DỮ LIỆU THAY ĐỔI, CẬP NHẬT DANH SÁCH MỤC ĂN TRONG ADAPTER
            customDocAdapter.setDocMonAn(docMonAn = docmeal as MutableList<docMeal>)
        })
    }
    private fun addrclcatevories() {
        binding.rclCaevories.adapter = customDocAdapter
        binding.rclCaevories.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }
}