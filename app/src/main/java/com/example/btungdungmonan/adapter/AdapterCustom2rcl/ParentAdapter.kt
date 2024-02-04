package com.example.btungdungmonan.adapter.AdapterCustom2rcl
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongParentBinding
import com.example.btungdungmonan.dataclass.Categories.CategoriMeal

class ParentAdapter():RecyclerView.Adapter<ParentAdapter.ViewHolder>() {
    //TẠO SỰ KIỆNCLICK
    lateinit var clickDocMonAn:((CategoriMeal) -> Unit)
    // CHUYỂN SANG MUTABLLIST
    private var docMonAn:MutableList<CategoriMeal> = mutableListOf()
    fun setDocMonAn(docMonAn: MutableList<CategoriMeal>){
        // GÁN DANH SÁCH MỚI ĐƯỢC TRUYỀN VÀO CHO THUỘC TÍNH DOCMONAN CỦA LỚP
        this.docMonAn=docMonAn
        // THÔNG BÁO CHO ADAPTER RẰNG DỮ LIỆU ĐÃ THAY ĐỔI, CẦN CẬP NHẬT GIAO DIỆN
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: DongParentBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= DongParentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
            if (docMonAn!=null)
                return docMonAn.size
            else
                return 0
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView)
            .load(docMonAn[position].strCategoryThumb)
            .into(holder.binding.imgdocmonan)
        holder.binding.txtdocten.text="How make dishes ${docMonAn[position].strCategory}"
        //CLICK VAO ITEM
        holder.itemView.setOnClickListener {
            clickDocMonAn.invoke(docMonAn[position])
        }
    }

}