package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemSavedJobSeeekersBinding
import org.rakulee.buup.fragments.employer.EmployerHomeDirections
import org.rakulee.buup.fragments.employer.EmployerSavedDirections
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.model.EmployerSavedListItem

class EmployerSavedJobSeekersAdapter : RecyclerView.Adapter<EmployerSavedJobSeekersAdapter.ViewHolder>() {


    private var mItems = ArrayList<BuupJobSeekerProfile>()
    private lateinit var context : Context

    fun update(items : ArrayList<BuupJobSeekerProfile>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_saved_job_seeekers, parent, false)
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
        val binding = ItemSavedJobSeeekersBinding.bind(itemView)
        lateinit var selectedCandidate : BuupJobSeekerProfile
        init {
            // initialize event listeners if needed
            itemView.setOnClickListener {
                val gson = Gson()
                val jsonObj = gson.toJsonTree(selectedCandidate)
                val directions : NavDirections = EmployerSavedDirections.actionMainEmpSavedToEmployerPositionCandidate(jsonObj.toString())
                it.findNavController().navigate(directions)
            }
        }

        fun bind(item: BuupJobSeekerProfile){
            binding.item = item
            selectedCandidate = item
            binding.liked = true

        }
    }
}