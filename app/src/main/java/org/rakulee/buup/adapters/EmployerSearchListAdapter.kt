package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemJobSeekersListBinding
import org.rakulee.buup.model.EmployerSearchItem

class EmployerSearchListAdapter : RecyclerView.Adapter<EmployerSearchListAdapter.ViewHolder>() {


    private var mItems = ArrayList<EmployerSearchItem>()
    private lateinit var context : Context

    fun update(items : ArrayList<EmployerSearchItem>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job_seekers_list, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(holder.adapterPosition))
    }

    override fun getItemCount(): Int {

        return mItems.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemJobSeekersListBinding.bind(itemView)
        init {
            // initialize event listeners if needed
        }

        fun bind(item : EmployerSearchItem){
            binding.tvUsername.text = item.seekerUserId
            binding.tvApplicantTitle.text = item.seekerTitle
            binding.tvLocation.text = item.location
//            val target = GlideDrawableImageViewTarget(binding.ivProfile)
            Glide.with(itemView.context).asGif().load(item.profileUrl).into(binding.ivProfile)
        }

    }
}