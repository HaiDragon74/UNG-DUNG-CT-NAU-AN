package com.example.btungdungmonan.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongCatevoriesBinding
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal

class CatevoriesAdapter():RecyclerView.Adapter<CatevoriesAdapter.ViewHolder>() {
    //TẠO SỰ KIỆNCLICK
    lateinit var clickCategory:((CategoriMeal) -> Unit)
    // CHUYỂN SANG MUTABLLIST
    private var listCategory:MutableList<CategoriMeal> = mutableListOf()
    fun setDocMonAn(listCategory: MutableList<CategoriMeal>){
        // GÁN DANH SÁCH MỚI ĐƯỢC TRUYỀN VÀO CHO THUỘC TÍNH DOCMONAN CỦA LỚP
        this.listCategory=listCategory
        // THÔNG BÁO CHO ADAPTER RẰNG DỮ LIỆU ĐÃ THAY ĐỔI, CẦN CẬP NHẬT GIAO DIỆN
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: DongCatevoriesBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= DongCatevoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return listCategory.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView)
            .load(listCategory[position].strCategoryThumb)
            .into(holder.binding.imgCatevories)
        holder.binding.tvCatevories.text=listCategory[position].strCategory
        //CLICK VAO ITEM
        holder.itemView.setOnClickListener {
            clickCategory.invoke(listCategory[position])
        }
    }

}