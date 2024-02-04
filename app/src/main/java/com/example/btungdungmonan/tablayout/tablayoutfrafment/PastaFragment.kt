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
import com.example.btungdungmonan.databinding.FragmentPastaBinding
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.rom.DatabaseMeal

class PastaFragment : Fragment() {
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var parentChildAdapter: ParentChildAdapter
    private lateinit var binding: FragmentPastaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentChildAdapter= ParentChildAdapter()
        //VIEWMODEL
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
        binding=FragmentPastaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getCategoryMeal()
        homeViewModel.getPastaApi()
        getDessert()
    }
    private fun getDessert() {
        var listParetChil1:MutableList<ListDataParentChild> = mutableListOf()
        var parentPasta:MutableList<CategoriMeal> = mutableListOf()
        homeViewModel.ldtCategoryMealDaTa().observe(viewLifecycleOwner, Observer {listCategoriMeal->
            Log.d("AAAAAABBBBB",listCategoriMeal[5].strCategory)
            parentPasta.add(CategoriMeal("${listCategoriMeal[5].idCategory}","${listCategoriMeal[5].strCategory}","${listCategoriMeal[5].strCategoryDescription}","${listCategoriMeal[5].strCategoryThumb}"))
            var chillPasta:MutableList<SearchMeal> = mutableListOf()
            homeViewModel.ldtPastaMonanMeal().observe(viewLifecycleOwner, Observer {listMonAnMeal->
                chillPasta.addAll(listMonAnMeal)
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentPasta,null))
                listParetChil1.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chillPasta))
                binding.rclPasta.layoutManager= LinearLayoutManager(requireContext())
                parentChildAdapter.setDataParetChild(requireContext(),listParetChil1)
                binding.rclPasta.adapter=parentChildAdapter
                binding.probarImgMeal.visibility=View.GONE
            })
        })
    }
}