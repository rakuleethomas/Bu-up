package org.rakulee.buup.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemEmployerRecommendedBinding
import org.rakulee.buup.databinding.ItemJobSeekerCandidateBinding
import org.rakulee.buup.databinding.ItemRvBadgeListBinding
import org.rakulee.buup.fragments.employer.EmployerHome
import org.rakulee.buup.fragments.employer.EmployerHomeDirections
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
            lateinit var selectedCandidate : BuupJobSeekerProfile
            init {
                itemView.setOnClickListener {
                    val gson = Gson()
                    val jsonObj = gson.toJsonTree(selectedCandidate)
                    val directions : NavDirections = EmployerHomeDirections.actionMainEmpDashboardToEmployerPositionCandidate(jsonObj.toString())
                    it.findNavController().navigate(directions)
                }
            }

            fun bind(item : BuupJobSeekerProfile){
                binding.item = item
                selectedCandidate = item
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
            val badgeMap = HashMap<EmployerHome.BadgeStatus, Int>()
            init {
                badgeMap.put(EmployerHome.BadgeStatus.Healthcare, R.drawable.ic_industry_healthcare)
                badgeMap.put(EmployerHome.BadgeStatus.Airline, R.drawable.ic_industry_airline)
                badgeMap.put(EmployerHome.BadgeStatus.ArtMedia, R.drawable.ic_industry_art_media)
                badgeMap.put(EmployerHome.BadgeStatus.Automotive, R.drawable.ic_industry_automotive)
                badgeMap.put(EmployerHome.BadgeStatus.Cleaner, R.drawable.ic_industry_cleaner)
                badgeMap.put(EmployerHome.BadgeStatus.Hotel, R.drawable.ic_industry_hotel)
                badgeMap.put(EmployerHome.BadgeStatus.Law, R.drawable.ic_industry_law)
                badgeMap.put(EmployerHome.BadgeStatus.Manufacture, R.drawable.ic_industry_manufacture)
                badgeMap.put(EmployerHome.BadgeStatus.Moving, R.drawable.ic_industry_moving)
                badgeMap.put(EmployerHome.BadgeStatus.Pharmacy, R.drawable.ic_industry_pharmacy)
                badgeMap.put(EmployerHome.BadgeStatus.Policy, R.drawable.ic_industry_policy)
                badgeMap.put(EmployerHome.BadgeStatus.Childcare, R.drawable.ic_baseline_child_care_24)
            }

            fun bind(item : JobSeekerSignInResponse.Message.Badge){
                val name = enumValueOf<EmployerHome.BadgeStatus>(item.name) as EmployerHome.BadgeStatus
                val drawableResource = badgeMap.get(name)!!
                val drawable = ContextCompat.getDrawable(itemView.context, drawableResource)
                binding.ivBadge.setImageDrawable(drawable)
            }
        }
    }
}