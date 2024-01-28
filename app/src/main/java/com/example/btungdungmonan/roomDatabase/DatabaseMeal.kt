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

    // Hàm trừu tượng để nhận DAO (Data Access Object) cho các đối tượng Meal
   abstract  fun mealDao():MealDao
    // Đối tượng companion để khai báo một phiên bản duy nhất của DatabaseMeal
    companion object{
        // @Volatile: Đảm bảo rằng giá trị của INSTANCE luôn được cập nhật
        @Volatile
        private var INSTANCE:DatabaseMeal? =null
        // Phương thức để lấy hoặc tạo ra một đối tượng DatabaseMeal
        fun getDatabaseMeal(context: Context):DatabaseMeal{
            val datainstance= INSTANCE
            // Kiểm tra xem đã có một phiên bản của DatabaseMeal chưa
            if (datainstance!=null)
                return datainstance
            // Nếu không có, tạo một đối tượng mới và gán cho INSTANCE
            synchronized(this){
                // Tạo một đối tượng cơ sở dữ liệu Room với tên là "BANG_MONAN"
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