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
    private lateinit var ranDomMeal:Meal
    private lateinit var monAnAdapter: CustomNgangadapter
    private lateinit var docMonAnAdapter: CustomDocAdapter
    private var timkiem=SearchFragment()

/*    companion object{
        const val MONAN_ID="package com.example.btungdungmonan.idMeal"
        const val MONAN_NAME="package com.example.btungdungmonan.nameMeal"
        const val MONAN_THUMB="package com.example.btungdungmonan.thumbdMeal"
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val home=DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory=HomeViewModelFactory(home)
        homehvm=ViewModelProvider(this,homeFactory)[HomeVideoModel::class.java]
        monAnAdapter= CustomNgangadapter()
        docMonAnAdapter= CustomDocAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // kai bao binding
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // truyen anh vao img
        homehvm.getrandommal()
        ldtRanDommeal()
        clickimghome()

        addRecyclerview()

        //
        homehvm.getfilerApi()
        ldtmonanmealdata()

        //

        clickItemrcl()


        //

        homehvm.getDocMonAnApi()
        getldtDocMonAn()

        //
        rclDocMonAn()

        // item click rcl doc , chuyen man itemdocactivity
        clickitemrcldoc()


        //click long itemngang
        clickLongItemNgang()


        //clickSachHome()
        binding.imgSearchHome.setOnClickListener {
            clickSachHome()
        }













    }



    private fun clickSachHome() {
        childFragmentManager.beginTransaction().apply {
            // Thay thế Fragment hiện tại bằng Fragment mới (timkiem)
            replace(R.id.fragmentlayout,timkiem)
            // Áp dụng các thay đổi vào FragmentTransaction
            commit()
            // Hiển thị fragmentlayout
            binding.fragmentlayout.visibility=View.VISIBLE
        }
    }

    private fun clickLongItemNgang() {
        // Đoạn mã xử lý khi một mục trong RecyclerView được click
        monAnAdapter.clickLong={monanmeal->
            val bottomMealFragment=BottomMealFragment.newInstance(monanmeal.idMeal)
            bottomMealFragment.show(childFragmentManager,"MEAL_ID")
            Toast.makeText(requireContext(),"Hai vip",Toast.LENGTH_SHORT).show()

        }
    }


    private fun clickitemrcldoc() {
        // Đoạn mã xử lý khi một mục trong RecyclerView được click
        docMonAnAdapter.clickDocMonAn={docMeal->
            val intent=Intent(activity, ItemdocActivyty::class.java)
            intent.putExtra("DATANAMEDOC",docMeal.strCategory)
            startActivity(intent)

        }

    }

    private fun rclDocMonAn() {
        // Cấu hình LayoutManager cho RecyclerView có id là rclMonan
        binding.rclMonan.layoutManager=GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
        binding.rclMonan.adapter=docMonAnAdapter
        // Cho phép RecyclerView có khả năng cuộn lồng (nested scrolling)
        binding.rclMonan.isNestedScrollingEnabled=true
    }

    private fun getldtDocMonAn() {
        // Sử dụng LiveData để quan sát danh sách món ăn theo dạng lưới
        homehvm.ldtDocMonAnDaTa().observe(viewLifecycleOwner) {it
            // Khi dữ liệu thay đổi, cập nhật danh sách món ăn vào Adapter
            docMonAnAdapter.setDocMonAn(docMonAn = it as MutableList<docMeal>)

        }
    }


    private fun clickItemrcl() {
        // Sử dụng property clickitem trong Adapter để định nghĩa hành động khi một mục được click
        monAnAdapter.clickitem={MonAnMeal->
            // Tạo Intent để chuyển dữ liệu sang MonanActivity
            var intent=Intent(activity, MonanActivity::class.java)
            intent.putExtra("DATAID",MonAnMeal.idMeal)
            intent.putExtra("DATANAME",MonAnMeal.strMeal)
            intent.putExtra("DATATHUMB",MonAnMeal.strMealThumb)
            // Khởi chạy MonanActivity với Intent đã được cấu hình
            startActivity(intent)



        }
    }

    private fun ldtmonanmealdata() {
        // Sử dụng LiveData để quan sát danh sách món ăn
        homehvm.ldtMonanMealData().observe(viewLifecycleOwner
        ) {list->
            // Khi dữ liệu thay đổi, cập nhật danh sách món ăn vào Adapter
            monAnAdapter.setlistmonan(datalist = list as MutableList<MonAnMeal>)

        }
    }

    private fun addRecyclerview() {
        // Gắn LayoutManager và Adapter cho RecyclerView có id là rclphobien
        binding.rclphobien.apply {
            // Cấu hình LayoutManager cho RecyclerView - ở đây sử dụng LinearLayoutManager với chiều ngang
            layoutManager=LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            // Thiết lập Adapter cho RecyclerView - monAnAdapter là một Adapter đã được khởi tạo trước đó
            adapter=monAnAdapter
        }
    }

    private fun clickimghome() {
        binding.imgmonan.setOnClickListener {
            // CHUYEN DU LIEU SANG MAN MONANACTIVITY
            val intent=Intent(activity, MonanActivity::class.java)
            // Đưa dữ liệu cần chuyển vào Intent
            intent.putExtra("DATAID",ranDomMeal.idMeal)
            intent.putExtra("DATANAME",ranDomMeal.strMeal)
            intent.putExtra("DATATHUMB",ranDomMeal.strMealThumb)
            // Khởi chạy MonanActivity với Intent đã được cấu hình
            startActivity(intent)
        }
    }

    private fun ldtRanDommeal() {
        // Sử dụng thư viện Glide để tải hình ảnh từ URL và hiển thị trong ImageView (binding.imgmonan)
        homehvm.ldtRanDomMeal().observe(viewLifecycleOwner
        ) { value ->
            Glide.with(this@HomeFragment)
                .load(value.strMealThumb)
                .into(binding.imgmonan)
            // Lưu giữ dữ liệu bữa ăn ngẫu nhiên để sử dụng nếu cần thiết
            this@HomeFragment.ranDomMeal = value
        }
    }
}