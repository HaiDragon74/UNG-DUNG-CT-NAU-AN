package com.example.btungdungmonan.frament

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.btungdungmonan.activity.InstructionActivity
import com.example.btungdungmonan.adapter.FavoritesAdapter
import com.example.btungdungmonan.databinding.FragmentFavorilesBinding
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavoriesFragment : Fragment() {
    private lateinit var binding: FragmentFavorilesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        favoritesAdapter = FavoritesAdapter()
        //VIEWMODEL
        val dataRomMeal = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(dataRomMeal)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        binding = FragmentFavorilesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //ADD RECYCLERVIEW
        rclFavorites()
        //LDTFAVORITES
        adapterFavorites()
        //LUOT PHAI XOA ITEM
        deleteItemRclFavorites()
        //CLICK ITEM
        clickItemRcl()
        super.onViewCreated(view, savedInstanceState)
    }
    //TRUYỀN DỮ LIỆU
    private fun clickItemRcl() {
        favoritesAdapter.clickMeal = { meal ->
            val intent = Intent(requireContext(), InstructionActivity::class.java)
            intent.putExtra("DATAID", meal.idMeal)
            intent.putExtra("DATANAME", meal.strMeal)
            intent.putExtra("DATATHUMB", meal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun deleteItemRclFavorites() {
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
                val curentList = favoritesAdapter.differ.currentList
                // LẶP QUA DANH SÁCH ĐỂ LẤY THÔNG TIN CỦA MÓN ĂN ĐƯỢC VUỐT
                curentList.forEach { meal ->
                    getmeal = meal
                }
                val position = viewHolder.adapterPosition
                // KIỂM TRA NẾU VỊ TRÍ HỢP LỆ TRONG DANH SÁCH
                if (position >= 0 && position < curentList.size) {
                    // XÓA MỤC KHỎI CƠ SỞ DỮ LIỆU
                    homeViewModel.deleteDataMeal(curentList[position])
                    // HIỂN THỊ SNACKBAR ĐỂ THÔNG BÁO VỀ VIỆC XÓA MỤC
                    Snackbar.make(
                        requireView(), "delete ${getmeal!!.strMeal} ", Snackbar.LENGTH_LONG
                    ).setAction("undoed") {
                            // KHÔI PHỤC DỮ LIỆU ĐÃ XÓA NẾU NGƯỜI DÙNG BẤM NÚT "UNDOED" TRÊN SNACKBAR
                            homeViewModel.insertDataMeal(curentList[position])
                        }.show()
                }
            }
        }
        // GẮN ITEMTOUCHHELPER VÀO RECYCLERVIEW (RCLFAVORITE) ĐỂ XỬ LÝ SỰ KIỆN VUỐT
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rclFavorite)
    }
    private fun rclFavorites() {
        binding.rclFavorite.adapter = favoritesAdapter
        binding.rclFavorite.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
    }
    private fun adapterFavorites() {
        homeViewModel.ldtFavoritesMeal().observe(viewLifecycleOwner, Observer { listMeal ->
            favoritesAdapter.differ.submitList(listMeal)
        })
    }
}