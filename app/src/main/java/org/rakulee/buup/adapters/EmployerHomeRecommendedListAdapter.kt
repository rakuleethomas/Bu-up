package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemEmployerRecommendedBinding
import org.rakulee.buup.databinding.ItemJobSeekerCandidateBinding
import org.rakulee.buup.databinding.ItemRvBadgeListBinding
import org.rakulee.buup.fragments.jobseeker.JobSeekerProfile
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.model.EmployerHomeRecommendedItem
import org.rakulee.buup.model.JobSeekerSignInResponse

class EmployerHomeRecommendedListAdapter : RecyclerView.Adapter<EmployerHomeRecommendedListAdapter.ViewHolder>() {

    private var mItems = ArrayList<EmployerHomeRecommendedItem>()
    private lateinit var mContext : Context

    fun update(items : ArrayList<EmployerHomeRecommendedItem>){
        mItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employer_recommended, parent, false)
        mContext = parent.context

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(position))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = ItemEmployerRecommendedBinding.bind(itemView)
        init {
            // initialize event listeners if needed

        }

        fun bind(item : EmployerHomeRecommendedItem){
            binding.item = item

            // pass item to the inner recyclerview
            val innerAdapter = InnerAdapter()
            binding.rvJobSeekerCandidate.adapter = innerAdapter
            innerAdapter.update(item.jobSeekerInfo)
            innerAdapter.notifyDataSetChanged()
        }
    }


    class InnerAdapter : RecyclerView.Adapter<InnerAdapter.InnerViewHolder>(){
        private var mItems = ArrayList<BuupJobSeekerProfile>()

        fun update(items : ArrayList<BuupJobSeekerProfile>){
            mItems = items
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job_seeker_candidate, parent, false)


            return InnerViewHolder(view)
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
            holder.bind(mItems.get(position))
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        class InnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            val binding = ItemJobSeekerCandidateBinding.bind(itemView)
            fun bind(item : BuupJobSeekerProfile){
                binding.item = item

                val badgeAdapter = BadgeAdapter()
                badgeAdapter.update(item.badgeList)
                binding.rvBadge.adapter = badgeAdapter
                badgeAdapter.notifyDataSetChanged()
            }
        }

    }

    class BadgeAdapter : RecyclerView.Adapter<BadgeAdapter.BadgeViewHolder>(){

        private var mItems = ArrayList<JobSeekerSignInResponse.Message.Badge>()

        fun update(items : ArrayList<JobSeekerSignInResponse.Message.Badge>){
            mItems = items
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BadgeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_badge_list, parent, false)
            return BadgeAdapter.BadgeViewHolder(view)
        }

        override fun onBindViewHolder(holder: BadgeViewHolder, position: Int) {
            holder.bind(mItems.get(position))
        }

        override fun getItemCount(): Int {
            return mItems.size
        }

        class BadgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

            val binding = ItemRvBadgeListBinding.bind(itemView)

            fun bind(item : JobSeekerSignInResponse.Message.Badge){

            }
        }
    }
}