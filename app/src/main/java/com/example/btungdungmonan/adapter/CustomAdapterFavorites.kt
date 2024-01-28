package com.example.btungdungmonan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DocMonanBinding
import com.example.btungdungmonan.databinding.DongItemBinding
import com.example.btungdungmonan.imgmonanrandomrcl1.Meal

class CustomAdapterFavorites():RecyclerView.Adapter<CustomAdapterFavorites.ViewHolder>() {
    inner class ViewHolder(val binding: DongItemBinding):RecyclerView.ViewHolder(binding.root)
    lateinit var clickItem:((Meal)-> Unit)


    private var diffUtil=object : DiffUtil.ItemCallback<Meal>(){
        // Kiểm tra xem hai mục có cùng một định danh không, thường là kiểm tra ID.
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal==newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            // Kiểm tra xem nội dung của hai mục có giống nhau không.
            // Bạn có thể tùy chỉnh phần này dựa trên yêu cầu cụ thể của đối tượng Meal.
            return oldItem==newItem
        }
    }
    val differ=AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=DongItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal=differ.currentList[position]
        // Sử dụng thư viện Glide để tải hình ảnh từ đường dẫn và hiển thị vào ImageView
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgitem)
        holder.binding.txtItem.text=meal.strMeal

        //click item
        holder.itemView.setOnClickListener {
            clickItem.invoke(meal)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}