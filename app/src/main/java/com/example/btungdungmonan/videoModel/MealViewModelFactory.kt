package com.example.btungdungmonan.videoModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.btungdungmonan.roomDatabase.DatabaseMeal

class MealViewModelFactory(private val databaseMeal: DatabaseMeal) : ViewModelProvider.Factory {
    // Hàm này được gọi khi cần tạo một ViewModel mới //ctrl i
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Trả về một đối tượng MonanViewModel được khởi tạo với databaseMeal
        return MonanViewModel(databaseMeal) as T
    }
}