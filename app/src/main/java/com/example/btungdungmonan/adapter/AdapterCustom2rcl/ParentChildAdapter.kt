package com.example.btungdungmonan.adapter.AdapterCustom2rcl

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btungdungmonan.activity.CategoryActivity
import com.example.btungdungmonan.activity.InstructionActivity
import com.example.btungdungmonan.databinding.DongParentchildBinding

class ParentChildAdapter():RecyclerView.Adapter<ParentChildAdapter.ViewHolder>() {

    val TYPE_CHILD=1
    val TYPE_PARENT=2
    private lateinit var context:Context
    private lateinit var listParetChil:MutableList<ListDataParentChild>
    fun setDataParetChild(context:Context,listParetChil:MutableList<ListDataParentChild>){
        this.listParetChil=listParetChil
        this.context=context
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: DongParentchildBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun getItemViewType(position: Int): Int {
        return listParetChil[position].getType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=DongParentchildBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (listParetChil!=null)
            return listParetChil.size
        else
            return 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listParentChild=listParetChil[position]
        if (listParentChild==null){
            return
        }
        if (TYPE_CHILD==holder.itemViewType)
        {
            val layoutManager=GridLayoutManager(context,2,GridLayoutManager.HORIZONTAL,false)
            holder.binding.rclParentChild.layoutManager=layoutManager
            holder.binding.rclParentChild.isFocusable=false
            holder.binding.rclParentChild.isNestedScrollingEnabled=true
            val childAdapter= ChildAdapter()
            childAdapter.setConDocMonAn(listParentChild.getconDocMeal())
            holder.binding.rclParentChild.adapter=childAdapter
            childAdapter.clickItem={monAnMeal->
                val intent=Intent(context,InstructionActivity::class.java)
                intent.putExtra("DATAID",monAnMeal.idMeal)
                intent.putExtra("DATANAME",monAnMeal.strMeal)
                intent.putExtra("DATATHUMB",monAnMeal.strMealThumb)
                context.startActivity(intent)
            }

        }
        else if (TYPE_PARENT==holder.itemViewType)
        {
            val layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            holder.binding.rclParentChild.layoutManager=layoutManager
            holder.binding.rclParentChild.isFocusable=false
            holder.binding.rclParentChild.isNestedScrollingEnabled=true
            val parentAdapter= ParentAdapter()
            parentAdapter.setDocMonAn(listParentChild.getDocMonAn())
            holder.binding.rclParentChild.adapter=parentAdapter
            parentAdapter.clickDocMonAn={categoriMeal->
                val intent = Intent(context, CategoryActivity::class.java)
                intent.putExtra("NAMECATEGORI", categoriMeal.strCategory)
                context.startActivity(intent)
            }

        }

    }
}