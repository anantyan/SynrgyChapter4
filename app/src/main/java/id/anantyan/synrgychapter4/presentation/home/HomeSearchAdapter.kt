package id.anantyan.synrgychapter4.presentation.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.synrgychapter4.R
import id.anantyan.synrgychapter4.data.local.entities.Product
import id.anantyan.synrgychapter4.databinding.ListItemSearchBinding
import kotlin.random.Random

class HomeSearchAdapter :
    ListAdapter<Product, HomeSearchAdapter.ProductViewHolder>(ProductComparator) {

    private var _onInteraction: HomeInteraction? = null

    private object ProductComparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ListItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindItem(getItem(position))
        val randomColor = holder.itemView.context.resources.getIntArray(R.array.card_colors)
        val randomIndex = Random.nextInt(randomColor.size)
        val colorTint = ColorStateList.valueOf(randomColor[randomIndex])
        ViewCompat.setBackgroundTintList(holder.itemView, colorTint)
    }

    inner class ProductViewHolder(private val binding: ListItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onInteraction?.onClick(bindingAdapterPosition, getItem(bindingAdapterPosition))
            }
        }

        fun bindItem(item: Product) {
            binding.txtTitle.text = item.name
            binding.txtDescription.text = item.description
            binding.txtPrice.text = item.price.toString()
        }
    }

    fun onInteraction(listener: HomeInteraction) {
        _onInteraction = listener
    }
}