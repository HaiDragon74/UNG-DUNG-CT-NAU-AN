package com.example.btungdungmonan.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.btungdungmonan.databinding.DongSeafoodBinding
import com.example.btungdungmonan.dataclass.Search.SearchMeal
class SeafoodAdapter(): RecyclerView.Adapter<SeafoodAdapter.ViewHoler>() {
   //CLICK
    lateinit var clickSearch:((SearchMeal) -> Unit)
    // CLICK LÂU
    lateinit var clickSearchLong:((SearchMeal) -> Unit)
    // AS SANG MUTABLeLIST
    private var listSearch:MutableList<SearchMeal> = mutableListOf()
    fun setlistmonan(listSearch:MutableList<SearchMeal>){
        this.listSearch=listSearch
        notifyDataSetChanged()
    }
    class ViewHoler(var binding: DongSeafoodBinding):RecyclerView.ViewHolder(binding.root) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val view=DongSeafoodBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHoler(view)
    }
    override fun getItemCount(): Int {
        return listSearch.size
    }
    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        // SỬ DỤNG THƯ VIỆN GLIDE ĐỂ TẢI HÌNH ẢNH TỪ ĐƯỜNG DẪN VÀ HIỂN THỊ VÀO IMAGEVIEW
        Glide.with(holder.itemView)
            .load(listSearch[position].strMealThumb)
            .into(holder.binding.imgSeafood)
        holder.binding.tvSeafood.text=listSearch[position].strMeal
        //CLICK  ITEM
        holder.itemView.setOnClickListener {
            // GỌI HÀM LAMBDA HOẶC THAM CHIẾU ĐẾN HÀM ĐƯỢC GÁN CHO CLICKITEM VÀ TRUYỀN ĐỐI TƯỢNG MONANMEAL TƯƠNG ỨNG
            clickSearch.invoke(listSearch[position])
        }
        // CLICK LONG ITEM
        holder.itemView.setOnLongClickListener {
            clickSearchLong.invoke(listSearch[position])
            true
        }
    }

}
