package com.example.ibm_project_fin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*

class main_visited_adapter(var item:ArrayList<StoreData>):RecyclerView.Adapter<main_visited_adapter.MyViewHolder>() {
    lateinit var itemclicklistener:OnItemClick

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var touch=itemView.findViewById<LinearLayout>(R.id.main_visited_touch)
        var name=itemView.findViewById<TextView>(R.id.main_visited_name)
        var distance=itemView.findViewById<TextView>(R.id.main_visited_distance)
        var address=itemView.findViewById<TextView>(R.id.main_visited_address)
        var state=itemView.findViewById<TextView>(R.id.main_visited_state)
        var daysago=itemView.findViewById<TextView>(R.id.main_visited_daysago)

        init{
            touch.setOnClickListener {
                itemclicklistener.itemclick(this,it,item[adapterPosition],adapterPosition)
            }
        }
    }

    interface OnItemClick{
        fun itemclick(viewholder:MyViewHolder,view:View, data:StoreData,position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.main_visited_layout,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=item[position].name
        holder.distance.text=item[position].distance.toString()+"m"
        holder.address.text=item[position].address.toString()
        when(item[position].state){
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
        holder.daysago.text=item[position].visited_days_ago.toString()+"일전 "
    }
}