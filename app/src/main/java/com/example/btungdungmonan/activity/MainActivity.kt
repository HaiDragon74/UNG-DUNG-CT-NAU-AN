package com.example.btungdungmonan.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.btungdungmonan.CatevoriesFragment
import com.example.btungdungmonan.FavorilesFragment
import com.example.btungdungmonan.HomeFragment
import com.example.btungdungmonan.R
import com.example.btungdungmonan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var trangChu= HomeFragment()
    private var uuThich= FavorilesFragment()
    private var danhMuc= CatevoriesFragment()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bắt đầu một giao dịch Fragment với FragmentManager
            supportFragmentManager.beginTransaction().apply {
                // Thực hiện việc thay thế Fragment trong container (R.id.frmLayout) bằng Fragment mới (trangChu)
                replace(R.id.frmLayout, trangChu)
                // Áp dụng các thay đổi vào giao dịch
                commit()


        }


        //xuli su kien click vao menu trong nav
    binding.btnNav.setOnNavigationItemSelectedListener {
        when(it.itemId)
        {
            R.id.trangchu ->supportFragmentManager.beginTransaction().apply {
                replace(R.id.frmLayout,trangChu)
                commit()
            }
            R.id.uuThich -> supportFragmentManager.beginTransaction().apply {
                replace(R.id.frmLayout, uuThich)
                commit()
            }
            R.id.danhmuc ->supportFragmentManager.beginTransaction().apply {
                replace(R.id.frmLayout,danhMuc)
                commit()
            }
        }
        true
    }

    }

    // NAV QUAY LAI
    override fun onBackPressed() {
        // KIEM TRA MAN FAI HOME KHONG
        if (binding.btnNav.selectedItemId!=R.id.trangchu)
            //NEU KHONG QUAY LAI HOME
            binding.btnNav.selectedItemId=R.id.trangchu
        else
            super.onBackPressed()


    }

}