package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemEmployerJobPostingListBinding
import org.rakulee.buup.fragments.employer.EmployerHomeDirections
import org.rakulee.buup.model.EmployerHomeJobPostingItem

class EmployerHomeJobPostingListAdapter : RecyclerView.Adapter<EmployerHomeJobPostingListAdapter.ViewHolder>() {

    private lateinit var mItems : ArrayList<EmployerHomeJobPostingItem>
    private lateinit var mContext : Context

    fun updateItems(items : ArrayList<EmployerHomeJobPostingItem>){
        mItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employer_job_posting_list, parent, false)
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

        val binding = ItemEmployerJobPostingListBinding.bind(itemView)
        init {
            // initialize event listeners if needed
            itemView.setOnClickListener{
                val direction = EmployerHomeDirections.actionMainEmpHomeToEmployerJobDetail()
                it.findNavController().navigate(direction)
            }
        }

        fun bind(item : EmployerHomeJobPostingItem){
            binding.item = item
        }
    }
}