package com.example.btungdungmonan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongItemBinding
import com.example.btungdungmonan.imgmonan.MonAnMeal

class CustomitemdocAdapter():RecyclerView.Adapter<CustomitemdocAdapter.ViewHolder>() {

    //LIST
    private var listitem:MutableList<MonAnMeal> = mutableListOf()
    //CHUYỂN SANG MUTABLELIST
    fun getlistItem(listitem:MutableList<MonAnMeal>){
        // GÁN DANH SÁCH MỚI ĐƯỢC TRUYỀN VÀO CHO THUỘC TÍNH LISTITEM CỦA LỚP
        this.listitem= listitem
        // THÔNG BÁO CHO ADAPTER RẰNG DỮ LIỆU ĐÃ THAY ĐỔI, CẦN CẬP NHẬT GIAO DIỆN
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: DongItemBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= DongItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return listitem.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView)
            .load(listitem[position].strMealThumb)   // ĐƯỜNG DẪN CỦA HÌNH ẢNH
            .into(holder.binding.imgitem)  // IMAGEVIEW ĐỂ HIỂN THỊ HÌNH ẢNH
        holder.binding.txtItem.text=listitem[position].strMeal
    }
}