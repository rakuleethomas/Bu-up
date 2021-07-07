package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemProfileSkillsBinding
import org.rakulee.buup.model.JobSeekerSkill

class JobSeekerProfileSkillsListAdapter : RecyclerView.Adapter<JobSeekerProfileSkillsListAdapter.ViewHolder>() {

    private var mItems = ArrayList<JobSeekerSkill>()
    private lateinit var mContext : Context


    fun updateItems(items : ArrayList<JobSeekerSkill>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_skills, parent, false)
        mContext = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(holder.adapterPosition))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val binding = ItemProfileSkillsBinding.bind(itemView)

        init {
            // initialize event listeners if needed
        }

        fun bind(item : JobSeekerSkill){
            binding.skill = item.skill
        }
    }
}