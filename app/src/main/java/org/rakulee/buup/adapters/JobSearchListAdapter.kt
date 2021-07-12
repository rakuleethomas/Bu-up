package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemSearchJobListBinding
import org.rakulee.buup.model.JobSearchItem

class JobSearchListAdapter : RecyclerView.Adapter<JobSearchListAdapter.ViewHolder>(){

    private var mItems = ArrayList<JobSearchItem>()
    private lateinit var context : Context
    fun updateItems(items : ArrayList<JobSearchItem>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_job_list, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(position))

    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemSearchJobListBinding.bind(itemView)
        init {
            // initialize event listeners if needed
        }

        fun bind(item : JobSearchItem){
            binding.tvCompanyName.text = item.companyName
            binding.tvJobDescription.text = item.description
            binding.tvJobLocation.text = item.location
        }
    }
}