package com.example.btungdungmonan.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DocMonanBinding
import com.example.btungdungmonan.imgmonanrcl2kcop.docMeal

class CustomDocAdapter():RecyclerView.Adapter<CustomDocAdapter.ViewHolder>() {
    //TẠO SỰ KIỆNCLICK
    lateinit var clickDocMonAn:((docMeal) -> Unit)
    // CHUYỂN SANG MUTABLLIST
    private var docMonAn:MutableList<docMeal> = mutableListOf()
    fun setDocMonAn(docMonAn: MutableList<docMeal>){
        // GÁN DANH SÁCH MỚI ĐƯỢC TRUYỀN VÀO CHO THUỘC TÍNH DOCMONAN CỦA LỚP
        this.docMonAn=docMonAn
        // THÔNG BÁO CHO ADAPTER RẰNG DỮ LIỆU ĐÃ THAY ĐỔI, CẦN CẬP NHẬT GIAO DIỆN
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: DocMonanBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= DocMonanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return docMonAn.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView)
            .load(docMonAn[position].strCategoryThumb)
            .into(holder.binding.imgdocmonan)
        holder.binding.txtdocten.text=docMonAn[position].strCategory
        //CLICK VAO ITEM
        holder.itemView.setOnClickListener {
            clickDocMonAn.invoke(docMonAn[position])
        }
    }
}