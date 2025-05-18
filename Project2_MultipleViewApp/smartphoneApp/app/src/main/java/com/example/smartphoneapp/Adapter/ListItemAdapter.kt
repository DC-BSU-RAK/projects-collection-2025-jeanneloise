package com.example.smartphoneapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smartphoneapp.Activity.DetailActivity
import com.example.smartphoneapp.Model.ItemsModel
import com.example.smartphoneapp.databinding.ViewholderItemBinding

class ListItemAdapter(val items:MutableList<ItemsModel>):
RecyclerView.Adapter<ListItemAdapter.ViewHolder>(){
    private var context: Context?=null
    class ViewHolder(val binding: ViewholderItemBinding):RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemAdapter.ViewHolder {
        context=parent.context
        val binding=ViewholderItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemAdapter.ViewHolder, position: Int) {
        holder.binding.titleTxt.text=items[position].title
        holder.binding.priceTxt.text="AED"+items[position].price.toString()
        holder.binding.subtitleTxt.text=items[position].extra

        val imageName = items[position].picUrl[0]  // e.g., "milktea1"
        val imageResId = holder.itemView.context.resources.getIdentifier(imageName, "drawable", holder.itemView.context.packageName)

        Glide.with(holder.itemView.context)
            .load(imageResId)
            .into(holder.binding.img)

        holder.itemView.setOnClickListener{
            val intent= Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object",items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}