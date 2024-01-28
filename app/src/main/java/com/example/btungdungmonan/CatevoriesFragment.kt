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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Khởi tạo đối tượng DatabaseMeal từ lớp companion object
        val home=DatabaseMeal.getDatabaseMeal(requireContext())
        // Khởi tạo đối tượng HomeViewModelFactory với đối số là đối tượng DatabaseMeal
        val homeViewModelFactory=HomeViewModelFactory(home)
        // Sử dụng ViewModelProvider để lấy hoặc tạo mới ViewModel của lớp HomeVideoModel
        homeVideoModel=ViewModelProvider(this,homeViewModelFactory)[HomeVideoModel::class.java]
        customDocAdapter= CustomDocAdapter()
        binding= FragmentCatevoriesBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //ad rcl vao catevories
        addrclcatevories()
        // ldt catevories
        homeVideoModel.getDocMonAnApi()
        ldtcatevories()

        //click item
        clickItemrcl()
        //test//



        super.onViewCreated(view, savedInstanceState)
    }

    private fun clickItemrcl() {
        customDocAdapter.clickDocMonAn={docmeal->
            val intent=Intent(requireContext(),ItemdocActivyty::class.java)
            intent.putExtra("DATANAMEDOC",docmeal.strCategory)
            startActivity(intent)
        }
    }

    private fun ldtcatevories() {
        // Sử dụng LiveData để quan sát thay đổi trong danh sách các mục ăn
        homeVideoModel.ldtDocMonAnDaTa().observe(viewLifecycleOwner, Observer {docmeal->
            // Khi dữ liệu thay đổi, cập nhật danh sách mục ăn trong Adapter
            customDocAdapter.setDocMonAn(docMonAn = docmeal as MutableList<docMeal>)

        })
    }

    private fun addrclcatevories() {
        binding.rclCaevories.adapter=customDocAdapter
        binding.rclCaevories.layoutManager=GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)
    }

}