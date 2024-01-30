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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        customAdapterFavorites = CustomAdapterFavorites()
        //VIEWMODEL
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homehvm = ViewModelProvider(this, homeFactory)[HomeVideoModel::class.java]
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        binding = FragmentFavorilesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //ADD RECYCLERVIEW
        addRecyclerViewFavorites()
        //LDTFAVORITES
        ldtFavorites()
        //LUOT PHAI XOA ITEM
        deleteItemFavorites()
        //CLICK ITEM
        clickItemRcl()
        super.onViewCreated(view, savedInstanceState)
    }
    //TRUYỀN DỮ LIỆU
    private fun clickItemRcl() {
        customAdapterFavorites.clickItem = { meal ->
            val intent = Intent(requireContext(), MonanActivity::class.java)
            intent.putExtra("DATAID", meal.idMeal)
            intent.putExtra("DATANAME", meal.strMeal)
            intent.putExtra("DATATHUMB", meal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun deleteItemFavorites() {
        // TẠO MỘT ĐỐI TƯỢNG ITEMTOUCHHELPER ĐỂ XỬ LÝ SỰ KIỆN VUỐT TRONG RECYCLERVIEW
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            //CTRL I
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var getmeal: Meal? = null
                // LẤY DANH SÁCH HIỆN TẠI TỪ ADAPTER
                val curentList = customAdapterFavorites.differ.currentList
                // LẶP QUA DANH SÁCH ĐỂ LẤY THÔNG TIN CỦA MÓN ĂN ĐƯỢC VUỐT
                curentList.forEach { meal ->
                    getmeal = meal
                }
                val position = viewHolder.adapterPosition
                // KIỂM TRA NẾU VỊ TRÍ HỢP LỆ TRONG DANH SÁCH
                if (position >= 0 && position < curentList.size) {
                    // XÓA MỤC KHỎI CƠ SỞ DỮ LIỆU
                    homehvm.deleteDataMeal(curentList[position])
                    // HIỂN THỊ SNACKBAR ĐỂ THÔNG BÁO VỀ VIỆC XÓA MỤC
                    Snackbar.make(
                        requireView(), "delete ${getmeal!!.strMeal} ", Snackbar.LENGTH_LONG
                    ).setAction("undoed") {
                            // KHÔI PHỤC DỮ LIỆU ĐÃ XÓA NẾU NGƯỜI DÙNG BẤM NÚT "UNDOED" TRÊN SNACKBAR
                            homehvm.insertDataMeal(curentList[position])
                        }.show()
                }
            }
        }
        // GẮN ITEMTOUCHHELPER VÀO RECYCLERVIEW (RCLFAVORITE) ĐỂ XỬ LÝ SỰ KIỆN VUỐT
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rclFavorite)
    }
    private fun addRecyclerViewFavorites() {
        binding.rclFavorite.adapter = customAdapterFavorites
        binding.rclFavorite.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }
    private fun ldtFavorites() {
        homehvm.ldtFavoritesMeal().observe(viewLifecycleOwner, Observer { meal ->
            customAdapterFavorites.differ.submitList(meal)
        })
    }
}