package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemSavedPositionBinding
import org.rakulee.buup.fragments.jobseeker.JobSeekerSavedJobsDirections
import org.rakulee.buup.model.EmployerSavedListItem

class EmployerSavedListAdapter : RecyclerView.Adapter<EmployerSavedListAdapter.ViewHolder>() {

    private var mItems = ArrayList<EmployerSavedListItem>()
    private lateinit var context : Context

    fun update(items : ArrayList<EmployerSavedListItem>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_position, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(holder.adapterPosition))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemSavedPositionBinding.bind(itemView)
        init {
            // initialize event listeners if needed
            itemView.setOnClickListener{
                val direction = JobSeekerSavedJobsDirections.actionMainSeekerSavedJobsToJobSeekerJobDetail()
                binding.root.findNavController().navigate(direction)
            }
        }

        fun bind(item: EmployerSavedListItem){
            binding.item = item

        }
    }


}