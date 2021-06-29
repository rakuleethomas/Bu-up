package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemJobSeekersListBinding
import org.rakulee.buup.model.JobSearchItem
import org.rakulee.buup.model.JobSeekerItem

class JobSeekerListAdapter : RecyclerView.Adapter<JobSeekerListAdapter.ViewHolder>() {

    private var mItems = ArrayList<JobSeekerItem>()
    private lateinit var mContext : Context

    fun updateItems(items : ArrayList<JobSeekerItem>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job_seekers_list, parent, false)
        mContext = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(position))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemJobSeekersListBinding.bind(itemView)
        init {
            // initialize event listeners if needed
        }
        fun bind(item : JobSeekerItem){
            binding.tvUsername.text = item.username
            binding.tvApplicantTitle.text = item.title

        }
    }
}