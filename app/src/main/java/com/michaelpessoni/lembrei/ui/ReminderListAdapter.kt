package com.michaelpessoni.lembrei.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.michaelpessoni.lembrei.R
import com.michaelpessoni.lembrei.database.Reminder
import com.michaelpessoni.lembrei.databinding.ReminderItemBinding

class ReminderListAdapter(val onClickListener: ReminderListener) : ListAdapter<Reminder, ReminderListAdapter.ReminderViewHolder>(ReminderDiffCallback()){

    class ReminderViewHolder(private val binding: ReminderItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(reminder: Reminder) {
            binding.reminder = reminder
            binding.starredButton.setImageResource(
                when(reminder.priority){
                    true -> R.drawable.ic_star
                    else -> R.drawable.ic_star_outline
                }
            )
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderListAdapter.ReminderViewHolder {
        return ReminderViewHolder(ReminderItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ReminderListAdapter.ReminderViewHolder, position: Int) {
        val reminder = getItem(position)
        holder.bind(reminder)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(reminder)
        }

    }

}

class ReminderDiffCallback : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem.reminderId == newItem.reminderId
    }

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
        return oldItem == newItem
    }

}

class ReminderListener(val clickListener: (reminderId: Int) -> Unit) {
    fun onClick(reminder: Reminder) = clickListener(reminder.reminderId)
}