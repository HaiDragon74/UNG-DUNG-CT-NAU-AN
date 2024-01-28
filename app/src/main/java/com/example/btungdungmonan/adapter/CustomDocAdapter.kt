package com.example.btungdungmonan.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DocMonanBinding
import com.example.btungdungmonan.imgmonanrcl2kcop.docMeal

class CustomDocAdapter():RecyclerView.Adapter<CustomDocAdapter.ViewHolder>() {
    lateinit var clickDocMonAn:((docMeal) -> Unit)


    private var docMonAn:MutableList<docMeal> = mutableListOf()
    fun setDocMonAn(docMonAn: MutableList<docMeal>){
        // Gán danh sách mới được truyền vào cho thuộc tính docMonAn của lớp
        this.docMonAn=docMonAn
        // Thông báo cho Adapter rằng dữ liệu đã thay đổi, cần cập nhật giao diện
        notifyDataSetChanged()
    }
    fun setcatevories(meal:List<docMeal>){

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
        // Sử dụng thư viện Glide để tải hình ảnh từ đường dẫn và hiển thị vào ImageView
        Glide.with(holder.itemView)
            .load(docMonAn[position].strCategoryThumb)
            .into(holder.binding.imgdocmonan)
        holder.binding.txtdocten.text=docMonAn[position].strCategory

        //click vao item
        holder.itemView.setOnClickListener {
            clickDocMonAn.invoke(docMonAn[position])
        }
/*        holder.itemView.setOnClickListener {
            clickitm.onclick(docMonAn[position])
        }*/


    }
}