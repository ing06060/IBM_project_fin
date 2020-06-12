package com.example.ibm_project_fin

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class search_store_list_adapter(val items:ArrayList<StoreData>):RecyclerView.Adapter<search_store_list_adapter.MyViewHolder>() {
    lateinit var onitemtouchlistener:OnItemtouch

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var touch=itemView.findViewById<LinearLayout>(R.id.search_list_touch)
        var name=itemView.findViewById<TextView>(R.id.search_list_name)
        var address=itemView.findViewById<TextView>(R.id.search_list_address)
        var tel=itemView.findViewById<TextView>(R.id.search_list_tel)
        var distance=itemView.findViewById<TextView>(R.id.search_list_distance)
        var state=itemView.findViewById<TextView>(R.id.search_list_state)
        var conjuction=itemView.findViewById<ImageView>(R.id.search_list_congestionImage)

        init{
            touch.setOnClickListener {
                onitemtouchlistener.itemtouch(this,it,items[adapterPosition],adapterPosition)
            }
        }
    }

    interface OnItemtouch{
        fun itemtouch(viewHolder: MyViewHolder,view: View,data: StoreData,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.search_store_list_layout,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=items[position].name
        holder.address.text=items[position].address.toString()
        holder.tel.text=items[position].phone
        holder.distance.text=items[position].distance.toString()+"m"
        when(items[position].state){
            1->{
                holder.state.text="영업 중"
            }
            0->{
                holder.state.text="영업 종료"
            }
            -1->{
                holder.state.text="휴업"
            }
        }
        if(items[position].conjuction>=1.5){
            holder.conjuction.setImageResource(R.drawable.congestionlow)
        }else{
            holder.conjuction.setImageResource(R.drawable.congestionhigh)
        }
    }
}