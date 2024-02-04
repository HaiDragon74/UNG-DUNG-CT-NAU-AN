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
import com.example.btungdungmonan.databinding.FragmentAllMealBinding
import com.example.btungdungmonan.dataclass.Search.SearchMeal
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal
import com.example.btungdungmonan.rom.DatabaseMeal
import com.example.btungdungmonan.api.viewmodel.HomeViewModel
import com.example.btungdungmonan.api.viewmodel.HomeViewModelFactory

class AllMealFragment : Fragment() {
    private var beefFragment= BeefFragment()
    private var chickenragment= Chickenragment()
    private var dessertFragment=DessertFragment()
    private var lambFragment=LambFragment()
    private var miscellaneousFragment=MiscellaneousFragment()
    private var pastaFragment=PastaFragment()
    private var porkFragment=PorkFragment()
    private var seafoodFragment=SeafoodFragment()
    private var sideFragment=SideFragment()
    private var starterFragment=StarterFragment()
    private var veganFragment=VeganFragment()
    private var vegetarianFragment=VegetarianFragment()
    private var breakfastFragment=BreakfastFragment()
    private var goatFragment=GoatFragment()
    private lateinit var homehvm : HomeViewModel
    private lateinit var parentChildAdapter: ParentChildAdapter
    private lateinit var binding: FragmentAllMealBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAllMealBinding.inflate(layoutInflater,container,false)
        //VIEWMODEL
        val home = DatabaseMeal.getDatabaseMeal(requireContext())
        val homeFactory = HomeViewModelFactory(home)
        homehvm = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
        parentChildAdapter= ParentChildAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homehvm.getCategoryMeal()
        homehvm.getBeefApi()
        homehvm.getChickenApi()
        homehvm.getDessertApi()
        homehvm.getLambApi()
        //rclParentChild()
        allTab()

    }

    private fun allTab() {
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmBeef,beefFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmChicken,chickenragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmDessert,dessertFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmLamb,lambFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmMiscellaneous,miscellaneousFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmPasta,pastaFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmPork,porkFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmSeafood,seafoodFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmSide,sideFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmStarter,starterFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmVegan,veganFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmVegetarian,vegetarianFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmBreakfast,breakfastFragment)
            commit()
        }
        childFragmentManager.beginTransaction().apply {
            replace(R.id.frmGoat,goatFragment)
            commit()
        }

    }
    //GOI CHONG API
    private fun rclParentChild() {
        var listParetChil:MutableList<ListDataParentChild> = mutableListOf()
        var parentBeef:MutableList<CategoriMeal> = mutableListOf()
        var parentChicken:MutableList<CategoriMeal> = mutableListOf()
        var parentDessert:MutableList<CategoriMeal> = mutableListOf()
        var parentLamb:MutableList<CategoriMeal> = mutableListOf()
        homehvm.ldtCategoryMealDaTa().observe(viewLifecycleOwner, Observer {listCategoriMeal->
            parentBeef.add(CategoriMeal("${listCategoriMeal[0].idCategory}","${listCategoriMeal[0].strCategory}","${listCategoriMeal[0].strCategoryDescription}","${listCategoriMeal[0].strCategoryThumb}"))
            parentChicken.add(CategoriMeal("${listCategoriMeal[1].idCategory}","${listCategoriMeal[1].strCategory}","${listCategoriMeal[1].strCategoryDescription}","${listCategoriMeal[1].strCategoryThumb}"))
            parentDessert.add(CategoriMeal("${listCategoriMeal[2].idCategory}","${listCategoriMeal[2].strCategory}","${listCategoriMeal[2].strCategoryDescription}","${listCategoriMeal[2].strCategoryThumb}"))
            parentLamb.add(CategoriMeal("${listCategoriMeal[3].idCategory}","${listCategoriMeal[3].strCategory}","${listCategoriMeal[3].strCategoryDescription}","${listCategoriMeal[3].strCategoryThumb}"))
            var chilBeef:MutableList<SearchMeal> = mutableListOf()
            var chilChicken:MutableList<SearchMeal> = mutableListOf()
            var chilDessert:MutableList<SearchMeal> = mutableListOf()
            var chilLamb:MutableList<SearchMeal> = mutableListOf()
            homehvm.ldtBeefMonanMeal().observe(viewLifecycleOwner, Observer {beef->
                chilBeef.addAll(beef)
                homehvm.ldtChickenMonanMeal().observe(viewLifecycleOwner, Observer {chicken->
                    chilChicken.addAll(chicken)
                    homehvm.ldtDessertMonanMeal().observe(viewLifecycleOwner, Observer {dessert->
                        chilDessert.addAll(dessert)
                        homehvm.ldtLambtMonanMeal().observe(viewLifecycleOwner, Observer {lamb->
                            chilLamb.addAll(lamb)
                listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentBeef,null))
                listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chilBeef))
                    listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentChicken,null))
                    listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chilChicken))
                            listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentDessert,null))
                            listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chilDessert))
                                listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_PARENT,parentLamb,null))
                                listParetChil.add(ListDataParentChild(parentChildAdapter.TYPE_CHILD,null,chilLamb))

                binding.rclMonAn.layoutManager= LinearLayoutManager(requireContext())
                parentChildAdapter.setDataParetChild(requireContext(),listParetChil)
                binding.rclMonAn.adapter=parentChildAdapter
                        })
                    })
                })
            })
        })
    }
}