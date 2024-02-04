package com.example.btungdungmonan.adapter.AdapterCustom2rcl

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongChildBinding
import com.example.btungdungmonan.dataclass.Search.SearchMeal

class ChildAdapter():RecyclerView.Adapter<ChildAdapter.ViewHolder>() {
    private lateinit var conDocMonAn: MutableList<SearchMeal>
    lateinit var clickItem:((SearchMeal)-> Unit)
    fun setConDocMonAn(conDocMonAn: MutableList<SearchMeal>) {
        // GÁN DANH SÁCH MỚI ĐƯỢC TRUYỀN VÀO CHO THUỘC TÍNH DOCMONAN CỦA LỚP
        this.conDocMonAn = conDocMonAn
        // THÔNG BÁO CHO ADAPTER RẰNG DỮ LIỆU ĐÃ THAY ĐỔI, CẦN CẬP NHẬT GIAO DIỆN
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: DongChildBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = DongChildBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (conDocMonAn!=null)
            return conDocMonAn.size
        else
            return 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(conDocMonAn[position].strMealThumb)
            .into(holder.binding.imgChild)
        holder.binding.tvChild.text=conDocMonAn[position].strMeal
        holder.itemView.setOnClickListener {
            clickItem.invoke(conDocMonAn[position])
        }
    }
}