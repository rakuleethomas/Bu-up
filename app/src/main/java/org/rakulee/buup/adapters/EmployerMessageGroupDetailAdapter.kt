package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemMessageGroupDetailBinding
import org.rakulee.buup.fragments.employer.message.EmployerMessageGroupDirections
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.model.EmployerMessageGroupItemDetail

class EmployerMessageGroupDetailAdapter : RecyclerView.Adapter<EmployerMessageGroupDetailAdapter.ViewHolder>() {

    private var mItems = ArrayList<EmployerMessageGroupItemDetail>()
    private lateinit var context : Context

    fun update(items : ArrayList<EmployerMessageGroupItemDetail>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message_group_detail, parent, false)
        context = view.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mItems.get(position))
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val binding = ItemMessageGroupDetailBinding.bind(itemView)
        lateinit var jobSeeker : BuupJobSeekerProfile
        init {
            itemView.setOnClickListener{
//                val direction = EmployerMessageGroupDirections.actionMainEmpMessageToEmployerMessage(jobSeeker)
//                it.findNavController().navigate(direction)
            }
        }

        fun bind(item : EmployerMessageGroupItemDetail){
            binding.message = item
        }
    }


}