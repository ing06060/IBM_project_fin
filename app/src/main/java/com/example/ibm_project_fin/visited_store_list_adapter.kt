package com.example.ibm_project_fin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class visited_store_list_adapter(val items:ArrayList<StoreData>):RecyclerView.Adapter<visited_store_list_adapter.MyViewHolder>() {
    lateinit var itemtouchlistener:OnItemTouch

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var touch=itemView.findViewById<LinearLayout>(R.id.visited_store_map_touch)
        var name=itemView.findViewById<TextView>(R.id.visit_store_map_name)
        var address=itemView.findViewById<TextView>(R.id.visit_store_map_address)
        var phone=itemView.findViewById<TextView>(R.id.visit_store_map_phone)
        var daysago=itemView.findViewById<TextView>(R.id.visit_store_map_dayago)
        var distance=itemView.findViewById<TextView>(R.id.visit_store_map_distance)
        var state=itemView.findViewById<TextView>(R.id.visit_store_map_state)
        var congeston=itemView.findViewById<ImageView>(R.id.visit_store_map_congestionImage)

        init{
            touch.setOnClickListener {
                itemtouchlistener.onitemtouch(this,it,items[adapterPosition],adapterPosition)
            }
        }
    }

    interface OnItemTouch{
        fun onitemtouch(viewholder:MyViewHolder,view:View,data:StoreData,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.visit_store_map_layout,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=items[position].name
        holder.address.text=items[position].address
        holder.phone.text=items[position].phone
        holder.daysago.text=items[position].visited_days_ago.toString()+"일 전"
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
            holder.congeston.setImageResource(R.drawable.congestionlow)
        }else{
            holder.congeston.setImageResource(R.drawable.congestionhigh)
        }
    }
}