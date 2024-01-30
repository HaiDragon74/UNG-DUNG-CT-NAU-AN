package com.example.btungdungmonan.roomDatabase

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bai30rom.typeCoverter.MealTypeCoverter
import com.example.btungdungmonan.dao.MealDao
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal
import com.example.btungdungmonan.videoModel.HomeVideoModel

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeCoverter::class)
abstract class DatabaseMeal:RoomDatabase() {
    // HÀM TRỪU TƯỢNG ĐỂ NHẬN DAO (DATA ACCESS OBJECT) CHO CÁC ĐỐI TƯỢNG MEAL
   abstract  fun mealDao():MealDao
    // ĐỐI TƯỢNG COMPANION ĐỂ KHAI BÁO MỘT PHIÊN BẢN DUY NHẤT CỦA DATABASEMEAL
    companion object{
        // @VOLATILE: ĐẢM BẢO RẰNG GIÁ TRỊ CỦA INSTANCE LUÔN ĐƯỢC CẬP NHẬT
        @Volatile
        private var INSTANCE:DatabaseMeal? =null
        // PHƯƠNG THỨC ĐỂ LẤY HOẶC TẠO RA MỘT ĐỐI TƯỢNG DATABASEMEAL
        fun getDatabaseMeal(context: Context):DatabaseMeal{
            val datainstance= INSTANCE
            // KIỂM TRA XEM ĐÃ CÓ MỘT PHIÊN BẢN CỦA DATABASEMEAL CHƯA
            if (datainstance!=null)
                return datainstance
            // NẾU KHÔNG CÓ, TẠO MỘT ĐỐI TƯỢNG MỚI VÀ GÁN CHO INSTANCE
            synchronized(this){
                // TẠO MỘT ĐỐI TƯỢNG CƠ SỞ DỮ LIỆU ROOM VỚI TÊN LÀ "BANG_MONAN"
                val newInstance=Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseMeal::class.java,
                    "BANG_MONAN"
                ).build()
                INSTANCE=newInstance
                return newInstance
            }
        }
    }
}