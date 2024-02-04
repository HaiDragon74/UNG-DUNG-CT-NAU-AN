package com.example.btungdungmonan.tablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.btungdungmonan.tablayout.tablayoutfrafment.AllMealFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.BeefFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.BreakfastFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.Chickenragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.DessertFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.GoatFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.LambFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.MiscellaneousFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.PastaFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.PorkFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.SeafoodFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.SideFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.StarterFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.VeganFragment
import com.example.btungdungmonan.tablayout.tablayoutfrafment.VegetarianFragment

class Adapter(fragmentManager:FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 15
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> AllMealFragment()
            1-> BeefFragment()
            2-> Chickenragment()
            3-> DessertFragment()
            4-> LambFragment()
            5-> MiscellaneousFragment()
            6-> PastaFragment()
            7-> PorkFragment()
            8-> SeafoodFragment()
            9-> SideFragment()
            10-> StarterFragment()
            11-> VeganFragment()
            12-> VegetarianFragment()
            13-> BreakfastFragment()
            14-> GoatFragment()
            else -> BeefFragment()
        }
    }
}