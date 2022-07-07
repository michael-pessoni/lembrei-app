package com.michaelpessoni.lembrei.ui.reminders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.michaelpessoni.lembrei.data.Reminder
import com.michaelpessoni.lembrei.databinding.ReminderItemBinding
import javax.inject.Inject

class ReminderListAdapter(private val viewModel: ReminderViewModel) : ListAdapter<Reminder, ReminderListAdapter.ReminderItemViewHolder>(
    ReminderDiffCallback()
){

    override fun onBindViewHolder(holder: ReminderItemViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item, viewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderItemViewHolder {
        return ReminderItemViewHolder.from(parent)
    }



    class ReminderItemViewHolder private constructor(val binding: ReminderItemBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Reminder, viewModel: ReminderViewModel){
            binding.viewmodel = viewModel
            binding.reminder = item
            binding.executePendingBindings()

        }

        companion object{
            fun from(parent: ViewGroup): ReminderItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ReminderItemBinding.inflate(layoutInflater, parent, false)

                return ReminderItemViewHolder(binding)
            }
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
