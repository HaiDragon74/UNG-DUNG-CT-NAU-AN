package com.example.btungdungmonan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongItemBinding
import com.example.btungdungmonan.imgmonan.MonAnMeal

class CustomitemdocAdapter():RecyclerView.Adapter<CustomitemdocAdapter.ViewHolder>() {

    private var listitem:MutableList<MonAnMeal> = mutableListOf()


    fun getlistItem(listitem:MutableList<MonAnMeal>){
        // Gán danh sách mới được truyền vào cho thuộc tính listitem của lớp
        this.listitem= listitem
        // Thông báo cho Adapter rằng dữ liệu đã thay đổi, cần cập nhật giao diện
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
        // Sử dụng thư viện Glide để tải hình ảnh từ đường dẫn và hiển thị vào ImageView
        Glide.with(holder.itemView)
            .load(listitem[position].strMealThumb)   // Đường dẫn của hình ảnh
            .into(holder.binding.imgitem)  // ImageView để hiển thị hình ảnh
        holder.binding.txtItem.text=listitem[position].strMeal


    }
}