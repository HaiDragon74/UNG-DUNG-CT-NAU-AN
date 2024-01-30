package com.example.btungdungmonan.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongMonanBinding
import com.example.btungdungmonan.imgmonan.MonAnMeal
class CustomNgangadapter(): RecyclerView.Adapter<CustomNgangadapter.ViewHoler>() {
   //CLICK
    lateinit var clickitem:((MonAnMeal) -> Unit)
    // CLICK LÂU
    lateinit var clickLong:((MonAnMeal) -> Unit)
    // AS SANG MUTABLeLIST
    private var datalist:MutableList<MonAnMeal> = mutableListOf()
    fun setlistmonan(datalist:MutableList<MonAnMeal>){
        this.datalist=datalist
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
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView)
            .load(datalist[position].strMealThumb)
            .into(holder.binding.imganhmau)
        //CLICK  ITEM
        holder.itemView.setOnClickListener {
            // GỌI HÀM LAMBDA HOẶC THAM CHIẾU ĐẾN HÀM ĐƯỢC GÁN CHO CLICKITEM VÀ TRUYỀN ĐỐI TƯỢNG MONANMEAL TƯƠNG ỨNG
            clickitem.invoke(datalist[position])
        }
        // CLICK LONG ITEM
        holder.itemView.setOnLongClickListener {
            clickLong.invoke(datalist[position])
            true
        }
    }
}