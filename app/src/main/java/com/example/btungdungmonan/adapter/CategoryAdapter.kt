package com.example.btungdungmonan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongItemdocBinding
import com.example.btungdungmonan.dataclass.Search.SearchMeal

class CategoryAdapter():RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    //CLICK
    lateinit var clickCategory:((SearchMeal)->Unit)
    //LIST
    private var lisSearch:MutableList<SearchMeal> = mutableListOf()
    //CHUYỂN SANG MUTABLELIST
    fun getlistItem(lisSearch:MutableList<SearchMeal>){
        // GÁN DANH SÁCH MỚI ĐƯỢC TRUYỀN VÀO CHO THUỘC TÍNH LISTITEM CỦA LỚP
        this.lisSearch= lisSearch
        // THÔNG BÁO CHO ADAPTER RẰNG DỮ LIỆU ĐÃ THAY ĐỔI, CẦN CẬP NHẬT GIAO DIỆN
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: DongItemdocBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= DongItemdocBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return lisSearch.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView)
            .load(lisSearch[position].strMealThumb)   // ĐƯỜNG DẪN CỦA HÌNH ẢNH
            .into(holder.binding.imgItemDoc)  // IMAGEVIEW ĐỂ HIỂN THỊ HÌNH ẢNH
        holder.binding.tvItemDoc.text=lisSearch[position].strMeal
        //Click
        holder.itemView.setOnClickListener {
            clickCategory.invoke(lisSearch[position])
        }
    }
}