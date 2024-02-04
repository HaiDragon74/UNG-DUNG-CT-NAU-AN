package com.example.btungdungmonan.tablayout.tablayoutfrafment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.btungdungmonan.R
import com.example.btungdungmonan.adapter.AdapterCustom2rcl.ListDataParentChild
import com.example.btungdungmonan.adapter.AdapterCustom2rcl.ParentChildAdapter
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory
import com.example.btungdungmonan.databinding.FragmentDessertBinding
import com.example.btungdungmonan.databinding.FragmentMiscellaneousBinding
import com.example.btungdungmonan.databinding.FragmentPorkBinding
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.rom.DatabaseMeal

class PorkFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var parentChildAdapter: ParentChildAdapter
    private lateinit var binding: FragmentPorkBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentChildAdapter= ParentChildAdapter()
        //VIEWMODEL
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
        binding=FragmentPorkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getCategoryMeal()
        homeViewModel.getPorkApi()
        getDessert()
    }
    private fun getDessert() {
        var listParetChil1:MutableList<ListDataParentChild> = mutableListOf()
        var parentPork:MutableList<CategoriMeal> = mutableListOf()
        homeViewModel.ldtCategoryMealDaTa().observe(viewLifecycleOwner, Observer {listCategoriMeal->
            Log.d("AAAAAABBBBB",listCategoriMeal[6].strCategory)
            parentPork.add(CategoriMeal("${listCategoriMeal[6].idCategory}","${listCategoriMeal[6].strCategory}","${listCategoriMeal[6].strCategoryDescription}","${listCategoriMeal[6].strCategoryThumb}"))
            var chillPork:MutableList<SearchMeal> = mutableListOf()
            homeViewModel.ldtPorkMonanMeal().observe(viewLifecycleOwner, Observer {listMonAnMeal->
                chillPork.addAll(listMonAnMeal)
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentPork,null))
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chillPork))
                binding.rclPork.layoutManager= LinearLayoutManager(requireContext())
                parentChildAdapter.setDataParetChild(requireContext(),listParetChil1)
                binding.rclPork.adapter=parentChildAdapter
                binding.probarImgMeal.visibility=View.GONE
            })
        })
    }
}