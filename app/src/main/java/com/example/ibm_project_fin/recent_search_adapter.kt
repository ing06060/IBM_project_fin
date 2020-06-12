package com.example.ibm_project_fin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class recent_search_adapter(var items:ArrayList<String>):RecyclerView.Adapter<recent_search_adapter.MyViewHolder>() {
    lateinit var itemclick:OnItemClick

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var terms=itemView.findViewById<TextView>(R.id.recent_search_terms)
        var delete=itemView.findViewById<TextView>(R.id.recent_search_delete)

        init{
            terms.setOnClickListener {
                itemclick.itemclick(this,it,items[adapterPosition],adapterPosition)
            }
            delete.setOnClickListener {
                itemclick.itemclick(this,it,items[adapterPosition],adapterPosition)
            }

        }
    }

    interface OnItemClick{
        fun itemclick(viewholder:MyViewHolder,view:View, data:String, position: Int)
    }

    fun deleteData(position:Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addData(data:String){
        for(i in 0 until items.size){
            if(items[i]==data){
                return;
            }
        }
        items.add(data)
        notifyItemInserted(items.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.recent_search_layout,parent,false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.terms.text=items[position]
    }
}