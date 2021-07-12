package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemCardExperiencesBinding
import org.rakulee.buup.databinding.ItemProfileSkillsBinding
import org.rakulee.buup.model.JobSeekerExperiences
import org.rakulee.buup.model.JobSeekerItem

class JobSeekerProfileExperiencesListAdapter : RecyclerView.Adapter<JobSeekerProfileExperiencesListAdapter.ViewHolder>() {

    private var mItems = ArrayList<JobSeekerExperiences>()
    private lateinit var mContext : Context

    fun updateItems(items : ArrayList<JobSeekerExperiences>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_experiences, parent, false)

        mContext = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(holder.adapterPosition))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        var binding = ItemCardExperiencesBinding.bind(itemView)


        init {
            // initialize event listeners if needed
        }

        fun bind(item : JobSeekerExperiences){
            binding.item = item
        }
    }
}