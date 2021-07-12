package org.rakulee.buup.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemCardJobPostingBinding
import org.rakulee.buup.databinding.ItemJobSeekersListBinding
import org.rakulee.buup.model.Job
import org.rakulee.buup.model.JobSeekerItem

class JobPostingAdapter : RecyclerView.Adapter<JobPostingAdapter.JobPostingViewHolder>(){

    private var mItems = ArrayList<Job>()
    private lateinit var mContext : Context

    fun updateItems(items : ArrayList<Job>){
        mItems = items
        notifyDataSetChanged()
    }

    inner class JobPostingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemCardJobPostingBinding.bind(itemView)
        init {
            // initialize event listeners if needed
        }
        @SuppressLint("SetTextI18n")
        fun bind(item : Job){
            binding.tvCompanyName.text = item.companyTitle
            binding.tvJobdescription.text = item.jobDescription
            binding.tvPay.text = "$ ${item.jobPay}"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobPostingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_job_posting, parent, false)
        mContext = view.context
        return JobPostingViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobPostingViewHolder, position: Int) {
        holder.bind(mItems.get(position))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }
}