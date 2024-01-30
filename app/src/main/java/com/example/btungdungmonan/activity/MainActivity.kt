package com.example.btungdungmonan.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.btungdungmonan.CatevoriesFragment
import com.example.btungdungmonan.FavorilesFragment
import com.example.btungdungmonan.HomeFragment
import com.example.btungdungmonan.R
import com.example.btungdungmonan.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var trangChu = HomeFragment()
    private var uuThich = FavorilesFragment()
    private var danhMuc = CatevoriesFragment()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // BẮT ĐẦU MỘT GIAO DỊCH FRAGMENT VỚI FRAGMENTMANAGER
        supportFragmentManager.beginTransaction().apply {
            // THỰC HIỆN VIỆC THAY THẾ FRAGMENT TRONG CONTAINER (R.ID.FRMLAYOUT) BẰNG FRAGMENT MỚI (TRANGCHU)
            replace(R.id.frmLayout, trangChu)
            // ÁP DỤNG CÁC THAY ĐỔI VÀO GIAO DỊCH
            commit()
        }
        //XU LI SU KIEN CLICK VAO MENU TRONG NAV
        binding.btnNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.trangchu -> supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frmLayout, trangChu)
                    commit()
                }
                R.id.uuThich -> supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frmLayout, uuThich)
                    commit()
                }
                R.id.danhmuc -> supportFragmentManager.beginTransaction().apply {
                    replace(R.id.frmLayout, danhMuc)
                    commit()
                }
            }
            true
        }
    }
    // NAV QUAY LAI
    override fun onBackPressed() {
        // KIEM TRA MAN FAI HOME KHONG
        if (binding.btnNav.selectedItemId != R.id.trangchu)
        //NEU KHONG QUAY LAI HOME
            binding.btnNav.selectedItemId = R.id.trangchu
        else
            super.onBackPressed()
    }
}