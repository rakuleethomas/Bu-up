package org.rakulee.buup.fragments.employer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.EmployerSavedListAdapter
import org.rakulee.buup.databinding.FragmentEmployerSavedBinding
import org.rakulee.buup.model.EmployerSavedListItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerSaved.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EmployerSaved : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentEmployerSavedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employer_saved, container, false)


        /**
        data class EmployerSavedListItem(
            val jobTitle: String,
            val companyTitle: String,
            val priceLow: String,
            val priceHigh: String,
            val location: String,
            val distanceMiles: String,
            val liked: Boolean
        )
        */

        val list = ArrayList<EmployerSavedListItem>()
        val adapter = EmployerSavedListAdapter()
        var savedItem : EmployerSavedListItem

        for(i : Int in 1 .. 15){
            savedItem = if(i%2 == 1){
                EmployerSavedListItem(
                    "Warehouse Part-Time Worker",
                    "Amazon",
                    "${25+i}",
                    "${50+i}",
                    "Santa Clara",
                    "${19+i*0.1}",
                    true
                )
            }else{
                EmployerSavedListItem(
                    "Warehouse Part-Time Worker",
                    "Amazon",
                    "${25+i}",
                    "${50+i}",
                    "Santa Clara",
                    "${19+i*0.1}",
                    false
                )
            }
            list.add(savedItem)
        }
        adapter.update(list)
        adapter.setHasStableIds(true)

        binding.rvSavedJob.adapter = adapter
        binding.rvSavedJob.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.fabMapMode.setOnClickListener { goMap() }

        return binding.root
    }

    fun goMap(){
        val directions : NavDirections = EmployerSavedDirections.actionEmployerSavedToMainEmpHome()
        findNavController().navigate(directions)
    }

    fun showDetail(){
        val directions : NavDirections = EmployerSavedDirections.actionMainEmpSavedToEmployerJobDetail()
        findNavController().navigate(directions)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerSaved.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerSaved().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}