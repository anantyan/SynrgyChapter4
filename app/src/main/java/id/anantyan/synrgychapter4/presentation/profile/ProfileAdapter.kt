package id.anantyan.synrgychapter4.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.anantyan.synrgychapter4.databinding.ListItemProfileBinding

class ProfileAdapter : ListAdapter<ProfileModel, ProfileAdapter.ProfileModelViewHolder>(ProfileModelComparator) {

    private object ProfileModelComparator : DiffUtil.ItemCallback<ProfileModel>() {
        override fun areItemsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
            return oldItem.resId == newItem.resId
        }

        override fun areContentsTheSame(oldItem: ProfileModel, newItem: ProfileModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileModelViewHolder {
        return ProfileModelViewHolder(
            ListItemProfileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProfileModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ProfileModelViewHolder(private val binding: ListItemProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: ProfileModel) {
            binding.imageView.setImageResource(item.resId ?: 0)
            binding.txtTitle.text = item.title
            binding.txtField.text = item.field
        }
    }
}