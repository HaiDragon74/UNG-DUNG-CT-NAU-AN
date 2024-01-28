package com.example.bai30rom.typeCoverter

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
 class MealTypeCoverter {

    // chuyen di kieu du lieu

    @TypeConverter
    fun fromAnytoString(data:Any?):String{
        // Kiểm tra xem data có null không
        if (data==null)
            return ""
        // Ép kiểu data thành String
        return data as String
    }
    @TypeConverter
    fun fromStringtoAny(data:String?):Any{
        // Kiểm tra xem data có null không
        if (data==null)
            return ""
        // tra ve kieu data String
        return data
    }
}