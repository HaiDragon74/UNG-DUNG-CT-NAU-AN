package com.example.btungdungmonan.api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.btungdungmonan.rom.DatabaseMeal

class HomeViewModelFactory(private val databaseMeal: DatabaseMeal) : ViewModelProvider.Factory {
    // Hàm này được gọi khi cần tạo một ViewModel mới //ctrl i
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Trả về một đối tượng MonanViewModel được khởi tạo với databaseMeal
        return HomeViewModel(databaseMeal) as T
    }
}