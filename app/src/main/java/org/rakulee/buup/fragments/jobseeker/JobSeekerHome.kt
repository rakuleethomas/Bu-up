package org.rakulee.buup.fragments.jobseeker

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.rakulee.buup.R
import org.rakulee.buup.adapters.JobListAdapter
import org.rakulee.buup.databinding.FragmentJobSeekerHomeBinding
import org.rakulee.buup.model.JobItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerHome.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class JobSeekerHome : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentJobSeekerHomeBinding



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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_home, container, false)
        binding.lifecycleOwner = this
        binding.vm = this

        // create a dummy data
        /**
         * data class JobItem(
        var imageUrl : String = "",
        var companyTitle : String = "",
        var jobDescription : String = ""
        )
         */
        var list = ArrayList<JobItem>()
        val adapter = JobListAdapter()


        var company : JobItem
        for(i : Int in 1..10){
            company = if(i%2 == 1){
                JobItem("https://svkoreans.com/img/svlogo1-1.jpg", "SVKoreans", "Looking for part timers!")
            }else{
                JobItem("https://svkoreans.com/img/svlogo1-1.jpg", "Rakulee, Inc.", "Looking for volunteers!")
            }
            list.add(company)
        }

        // end of create the dummy data
        adapter.updateItems(list)
        adapter.setHasStableIds(true)       // prevent blinking recyclerview items

        binding.rvAdList.adapter = adapter
        binding.rvAdList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//        binding.executePendingBindings()
        return binding.root
    }

    fun showPaymentPage(){

        val direction : NavDirections = JobSeekerHomeDirections.actionMainSeekerHomeToPaymentFragment()
        findNavController().navigate(direction)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobSeekerTest1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}