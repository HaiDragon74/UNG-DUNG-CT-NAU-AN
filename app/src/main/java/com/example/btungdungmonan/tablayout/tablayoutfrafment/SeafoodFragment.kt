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
import com.example.btungdungmonan.databinding.FragmentSeafoodBinding
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.rom.DatabaseMeal

class SeafoodFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var parentChildAdapter: ParentChildAdapter
    private lateinit var binding: FragmentSeafoodBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentChildAdapter= ParentChildAdapter()
        //VIEWMODEL
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
        binding=FragmentSeafoodBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getCategoryMeal()
        homeViewModel.getSeafoodApi()
        getDessert()
    }
    private fun getDessert() {
        var listParetChil1:MutableList<ListDataParentChild> = mutableListOf()
        var parentSeafood:MutableList<CategoriMeal> = mutableListOf()
        homeViewModel.ldtCategoryMealDaTa().observe(viewLifecycleOwner, Observer {listCategoriMeal->
            Log.d("AAAAAABBBBB",listCategoriMeal[7].strCategory)
            parentSeafood.add(CategoriMeal("${listCategoriMeal[7].idCategory}","${listCategoriMeal[7].strCategory}","${listCategoriMeal[7].strCategoryDescription}","${listCategoriMeal[7].strCategoryThumb}"))
            var chillSeafood:MutableList<SearchMeal> = mutableListOf()
            homeViewModel.ldtseafoodMealData().observe(viewLifecycleOwner, Observer {listMonAnMeal->
                chillSeafood.addAll(listMonAnMeal)
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentSeafood,null))
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chillSeafood))
                binding.rclSeafood.layoutManager= LinearLayoutManager(requireContext())
                parentChildAdapter.setDataParetChild(requireContext(),listParetChil1)
                binding.rclSeafood.adapter=parentChildAdapter
                binding.probarImgMeal.visibility=View.GONE
            })
        })
    }
}