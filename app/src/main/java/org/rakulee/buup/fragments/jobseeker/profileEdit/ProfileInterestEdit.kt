package org.rakulee.buup.fragments.jobseeker.profileEdit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import org.rakulee.buup.R
import org.rakulee.buup.adapters.JobSeekerProfileInterestListAdapter
import org.rakulee.buup.adapters.ProfileInterestSelectedAdapter
import org.rakulee.buup.databinding.FragmentProfileInterestEditBinding
import org.rakulee.buup.model.JobSeekerInterestItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileInterestEdit.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileInterestEdit : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentProfileInterestEditBinding

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_interest_edit, container, false)
        binding.lifecycleOwner = this
        binding.editInterest = this


        var interestSelectedList = ArrayList<JobSeekerInterestItem>()
        var interestList = ArrayList<JobSeekerInterestItem>()

        interestSelectedList.add(JobSeekerInterestItem("Restaurant"))
        interestSelectedList.add(JobSeekerInterestItem("helllo"))
        interestSelectedList.add(JobSeekerInterestItem("test1"))
        interestSelectedList.add(JobSeekerInterestItem("test2"))
        interestSelectedList.add(JobSeekerInterestItem("Customer Service"))
        interestSelectedList.add(JobSeekerInterestItem("Sales"))
        interestSelectedList.add(JobSeekerInterestItem("Marketing"))
        interestSelectedList.add(JobSeekerInterestItem("Translation"))

        interestList.add(JobSeekerInterestItem("Restaurant"))
        interestList.add(JobSeekerInterestItem("helllo"))
        interestList.add(JobSeekerInterestItem("test1"))
        interestList.add(JobSeekerInterestItem("test2"))
        interestList.add(JobSeekerInterestItem("Customer Service"))
        interestList.add(JobSeekerInterestItem("Sales"))
        interestList.add(JobSeekerInterestItem("Marketing"))
        interestList.add(JobSeekerInterestItem("Translation"))
        interestList.add(JobSeekerInterestItem("Restaurant"))
        interestList.add(JobSeekerInterestItem("helllo"))
        interestList.add(JobSeekerInterestItem("test1"))
        interestList.add(JobSeekerInterestItem("test2"))
        interestList.add(JobSeekerInterestItem("Customer Service"))
        interestList.add(JobSeekerInterestItem("Sales"))
        interestList.add(JobSeekerInterestItem("Marketing"))
        interestList.add(JobSeekerInterestItem("Translation"))



        val interestSelectedListAdapter = ProfileInterestSelectedAdapter()
        interestSelectedListAdapter.updateItems(interestSelectedList)

        val interestListAdapter = JobSeekerProfileInterestListAdapter()
        interestListAdapter.updateItems(interestList)

        binding.recyclerView.adapter = interestSelectedListAdapter
        binding.rvInterest.adapter = interestListAdapter

        binding.cancel.setOnClickListener{
            val direction : NavDirections = ProfileInterestEditDirections.actionEditInterestToProfile()
            findNavController().navigate(direction)
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileInterestEdit.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileInterestEdit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}