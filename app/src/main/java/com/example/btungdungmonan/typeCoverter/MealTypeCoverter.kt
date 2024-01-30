package com.example.bai30rom.typeCoverter

import androidx.room.TypeConverter
import androidx.room.TypeConverters

@TypeConverters
 class MealTypeCoverter {
    // CHUYEN DI KIEU DU LIEU
    @TypeConverter
    fun fromAnytoString(data:Any?):String{
        // KIỂM TRA XEM DATA CÓ NULL KHÔNG
        if (data==null)
            return ""
        // ÉP KIỂU DATA THÀNH STRING
        return data as String
    }
    @TypeConverter
    fun fromStringtoAny(data:String?):Any{
        // KIỂM TRA XEM DATA CÓ NULL KHÔNG
        if (data==null)
            return ""
        // TRA VE KIEU DATA STRING
        return data
    }
}