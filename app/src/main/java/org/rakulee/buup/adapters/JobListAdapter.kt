package org.rakulee.buup.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemAdListBinding
import org.rakulee.buup.model.Job
import org.rakulee.buup.model.JobItem

class JobListAdapter() : RecyclerView.Adapter<JobListAdapter.ViewHolder>() {

    private var mItems : ArrayList<Job> = ArrayList<Job>()
    private lateinit var context : Context

    fun updateItems(items : ArrayList<Job>){
        mItems = items;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ad_list, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(position))
//        context = holder.itemView.context
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemAdListBinding.bind(itemView)
        init {
            // initialize event listeners if needed
        }

        fun bind(item : Job){
//            binding.item = item;
            var imageUrl = item.ImageUrl
//            val uri = Uri.parse("https://svkoreans.com/img/svlogo1-1.jpg");
            if("".equals(imageUrl)){
                imageUrl = "https://svkoreans.com/img/svlogo1-1.jpg";
            }
            Glide.with(context).load(imageUrl).into(binding.ivCompanyLogo)
            binding.tvCompanyName.text = item.companyTitle
            binding.tvJobDescription.text = item.jobDescription
            binding.executePendingBindings()
        }
    }

}