package com.example.btungdungmonan.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: Meal)

    @Update
    suspend fun updateMeal(meal: Meal)

    @Query("SELECT * FROM BANG_MONAN")
    fun realDataMeal():LiveData<List<Meal>>

    @Delete
    suspend fun deleteMeal(meal: Meal)

}