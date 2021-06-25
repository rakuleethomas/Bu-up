package org.rakulee.buup.fragments.jobseeker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.JobListAdapter
import org.rakulee.buup.adapters.JobSearchListAdapter
import org.rakulee.buup.databinding.FragmentJobSeekerSearchBinding
import org.rakulee.buup.model.JobItem
import org.rakulee.buup.model.JobSearchItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerSearch.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class JobSeekerSearch : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentJobSeekerSearchBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_search, container, false)
        binding.lifecycleOwner = this
        binding.loading = true

        // create a dummy data
        /**
         * data class JobSearchItem(
        var companyName : String = "",
        var description : String = "",
        var location : String = ""
        )
         */
        var list = ArrayList<JobSearchItem>()
        val adapter = JobSearchListAdapter()
        var company : JobSearchItem
        for(i : Int in 1..10){
            company = if(i%2 == 1){
                JobSearchItem("SVKoreans", "Silicon Valley Koreans", "San Jose")

            }else{
                JobSearchItem("Rakulee, Inc.", "Rakulee, Inc. 501(c)(3) Tax Exempt Organization", "San Jose")
            }
            list.add(company)
        }

        // end of create the dummy data
        adapter.updateItems(list)
        adapter.setHasStableIds(true)       // prevent blinking recyclerview items
        binding.rvCompanyList.adapter = adapter
        binding.rvCompanyList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobSeekerTest2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerSearch().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}