package com.example.smartphoneapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartphoneapp.Model.ItemsModel
import com.example.smartphoneapp.R
import com.google.android.material.imageview.ShapeableImageView

class FavoriteAdapter(
    private val items: MutableList<ItemsModel>,
    private val onItemRemoved: (ItemsModel) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ShapeableImageView = view.findViewById(R.id.img)
        val title: TextView = view.findViewById(R.id.titleTxt)
        val subtitle: TextView = view.findViewById(R.id.subtitleTxt)
        val deleteIcon: ImageView = view.findViewById(R.id.deleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context

        holder.title.text = item.title
        holder.subtitle.text = item.description

        val resId = if (item.picUrl.isNotEmpty())
            context.resources.getIdentifier(item.picUrl[0], "drawable", context.packageName)
        else 0

        holder.image.setImageResource(if (resId != 0) resId else R.drawable.default_image)

        // Handle delete icon click
        holder.deleteIcon.setOnClickListener {
            val removedItem = items[position]
            items.removeAt(position)
            notifyItemRemoved(position)
            onItemRemoved(removedItem) // Notify parent
        }
    }

    override fun getItemCount(): Int = items.size
}
