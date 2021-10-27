package org.rakulee.buup.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ItemEmployerMessageCardBinding
import org.rakulee.buup.fragments.employer.message.EmployerMessageGroupDirections
import org.rakulee.buup.model.EmployerMessageGroupItem

class EmployerMessageGroupAdapter : RecyclerView.Adapter<EmployerMessageGroupAdapter.ViewHolder>()  {

    private var mItems = ArrayList<EmployerMessageGroupItem>()
    private lateinit var context : Context

    fun update(items : ArrayList<EmployerMessageGroupItem>){
        mItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_employer_message_card, parent, false)
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

        var binding = ItemEmployerMessageCardBinding.bind(itemView)
        init {

            itemView.setOnClickListener {
                val direction = EmployerMessageGroupDirections.actionMainEmpMessageToEmployerMessageDetail()
                it.findNavController().navigate(direction)
            }
        }

        fun bind(item : EmployerMessageGroupItem){
            binding.item = item
        }
    }
}