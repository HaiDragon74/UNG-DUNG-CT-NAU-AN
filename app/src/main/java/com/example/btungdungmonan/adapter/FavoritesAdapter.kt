package com.example.btungdungmonan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongItemdocBinding
import com.example.btungdungmonan.dataclass.imgmonanrandomrcl1.Meal

class FavoritesAdapter() : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: DongItemdocBinding) : RecyclerView.ViewHolder(binding.root)
    //TẠO SỰ KIỆN CLICK
    lateinit var clickMeal: ((Meal) -> Unit)
    private var diffUtil = object : DiffUtil.ItemCallback<Meal>() {
        // KIỂM TRA XEM HAI MỤC CÓ CÙNG MỘT ĐỊNH DANH KHÔNG, THƯỜNG LÀ KIỂM TRA ID.
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }
        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            // KIỂM TRA XEM NỘI DUNG CỦA HAI MỤC CÓ GIỐNG NHAU KHÔNG.
            // BẠN CÓ THỂ TÙY CHỈNH PHẦN NÀY DỰA TRÊN YÊU CẦU CỤ THỂ CỦA ĐỐI TƯỢNG MEAL.
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DongItemdocBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = differ.currentList[position]
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgItemDoc)
        holder.binding.tvItemDoc.text = meal.strMeal
        //CLICK ITEM
        holder.itemView.setOnClickListener {
            clickMeal.invoke(meal)
        }
    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}