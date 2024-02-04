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
import com.example.btungdungmonan.adapter.AdapterCustom2rcl.ListDataParentChild
import com.example.btungdungmonan.adapter.AdapterCustom2rcl.ParentChildAdapter
import com.example.btungdungmonan.databinding.FragmentBeefBinding
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory

class BeefFragment : Fragment() {
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var parentChildAdapter: ParentChildAdapter
    private lateinit var binding: FragmentBeefBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentBeefBinding.inflate(layoutInflater,container,false)
        //VIEWMODEL
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
        parentChildAdapter= ParentChildAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        homeViewModel.getCategoryMeal()
        homeViewModel.getBeefApi()
        getListParentChild()
        super.onViewCreated(view, savedInstanceState)

    }
    private fun getListParentChild() {
        var listParetChil1:MutableList<ListDataParentChild> = mutableListOf()
        var parentCategoriMeal:MutableList<CategoriMeal> = mutableListOf()
        homeViewModel.ldtCategoryMealDaTa().observe(viewLifecycleOwner, Observer {listCategoriMeal->
            Log.d("ASSSSssssAA",listCategoriMeal[0].strCategory)
            parentCategoriMeal.add(CategoriMeal("${listCategoriMeal[0].idCategory}","${listCategoriMeal[0].strCategory}","${listCategoriMeal[0].strCategoryDescription}","${listCategoriMeal[0].strCategoryThumb}"))
            var chillBeef:MutableList<SearchMeal> = mutableListOf()
            homeViewModel.ldtBeefMonanMeal().observe(viewLifecycleOwner, Observer {listMonAnMeal->
                chillBeef.addAll(listMonAnMeal)
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentCategoriMeal,null))
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chillBeef))
                binding.rclBeef.layoutManager= LinearLayoutManager(requireContext())
                parentChildAdapter.setDataParetChild(requireContext(),listParetChil1)
                binding.rclBeef.adapter=parentChildAdapter
                binding.probarImgMeal.visibility=View.GONE
            })
        })

    }
}