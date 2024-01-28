package com.example.btungdungmonan.adapter
import android.icu.text.Transliterator.Position
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.Click
import com.example.btungdungmonan.databinding.DongMonanBinding
import com.example.btungdungmonan.imgmonan.MonAnMeal
class CustomNgangadapter(): RecyclerView.Adapter<CustomNgangadapter.ViewHoler>() {
    // Khai báo một thuộc tính lateinit kiểu hàm, sẽ được gọi khi một mục được nhấp
    lateinit var clickitem:((MonAnMeal) -> Unit)
    // Khai báo một thuộc tính lateinit kiểu hàm, sẽ được gọi khi một mục được nhấp và giữ lâu
    lateinit var clickLong:((MonAnMeal) -> Unit)
    // Khai báo một danh sách có thể thay đổi chứa đối tượng MonAnMeal
    private var datalist:MutableList<MonAnMeal> = mutableListOf()
    fun setlistmonan(datalist:MutableList<MonAnMeal>){
        // Gán danh sách đối tượng MonAnMeal được truyền vào cho thuộc tính datalist của lớp
        this.datalist=datalist
        // Thông báo cho Adapter rằng dữ liệu đã thay đổi, cần cập nhật giao diện
        notifyDataSetChanged()
    }
    class ViewHoler(var binding: DongMonanBinding):RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val view=DongMonanBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHoler(view)
    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        // Sử dụng thư viện Glide để tải hình ảnh từ đường dẫn và hiển thị vào ImageView
        Glide.with(holder.itemView)
            .load(datalist[position].strMealThumb)
            .into(holder.binding.imganhmau)

        //click  item
        holder.itemView.setOnClickListener {
            // Gọi hàm lambda hoặc tham chiếu đến hàm được gán cho clickitem và truyền đối tượng MonAnMeal tương ứng
            clickitem.invoke(datalist[position])
        }

        // click long item
        holder.itemView.setOnLongClickListener {
            clickLong.invoke(datalist[position])
            true
        }

    }
}