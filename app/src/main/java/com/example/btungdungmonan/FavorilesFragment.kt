package com.example.btungdungmonan


import android.content.Intent
import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.btungdungmonan.activity.MonanActivity
import com.example.btungdungmonan.adapter.CustomAdapterFavorites
import com.example.btungdungmonan.databinding.FragmentFavorilesBinding
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.roomDatabase.DatabaseMeal
import com.example.btungdungmonan.videoModel.HomeVideoModel
import com.example.btungdungmonan.videoModel.HomeViewModelFactory
import com.google.android.material.snackbar.Snackbar


class FavorilesFragment : Fragment() {
    private lateinit var binding: FragmentFavorilesBinding
    private lateinit var customAdapterFavorites: CustomAdapterFavorites
    private lateinit var homehvm: HomeVideoModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        customAdapterFavorites = CustomAdapterFavorites()
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homehvm = ViewModelProvider(this, homeFactory)[HomeVideoModel::class.java]


        // Inflate the layout for this fragment
        binding = FragmentFavorilesBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //add recyclerview
        addRecyclerViewFavorites()

        //ldtFavorites
        ldtFavorites()

        //luot phai xoa item
        deleteItemFavorites()

        //click item
            clickItemRcl()







        super.onViewCreated(view, savedInstanceState)
    }

    private fun clickItemRcl() {
        customAdapterFavorites.clickItem={meal->
            val intent=Intent(requireContext(),MonanActivity::class.java)
            intent.putExtra("DATAID",meal.idMeal)
            intent.putExtra("DATANAME",meal.strMeal)
            intent.putExtra("DATATHUMB",meal.strMealThumb)

            startActivity(intent)
        }
    }

    private fun deleteItemFavorites() {
        // Tạo một đối tượng ItemTouchHelper để xử lý sự kiện vuốt trong RecyclerView
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            //ctrl i
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var getmeal:Meal?=null
                // Lấy danh sách hiện tại từ Adapter
                val curentList = customAdapterFavorites.differ.currentList
                // Lặp qua danh sách để lấy thông tin của món ăn được vuốt
                curentList.forEach { meal ->
                    getmeal=meal
                }
                val position = viewHolder.adapterPosition
                // Kiểm tra nếu vị trí hợp lệ trong danh sách
                if (position >= 0 && position < curentList.size) {
                    // Xóa mục khỏi cơ sở dữ liệu
                    homehvm.deleteDataMeal(curentList[position])
                    // Hiển thị Snackbar để thông báo về việc xóa mục
                    Snackbar.make(requireView(), "delete ${getmeal!!.strMeal} ", Snackbar.LENGTH_LONG)
                        .setAction("undoed") {
                            // Khôi phục dữ liệu đã xóa nếu người dùng bấm nút "undoed" trên Snackbar
                            homehvm.insertDataMeal(curentList[position])


                        }.show()
                }
            }
        }
        // Gắn ItemTouchHelper vào RecyclerView (rclFavorite) để xử lý sự kiện vuốt
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rclFavorite)
    }

    private fun addRecyclerViewFavorites() {
        binding.rclFavorite.adapter = customAdapterFavorites
        binding.rclFavorite.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)

    }

    private fun ldtFavorites() {
        homehvm.ldtFavoritesMeal().observe(viewLifecycleOwner, Observer { meal ->
/*            meal.forEach {meal->
                Log.d("EEEEE",meal.idMeal) //kiem tra
            }*/
            customAdapterFavorites.differ.submitList(meal)
        })
    }
}