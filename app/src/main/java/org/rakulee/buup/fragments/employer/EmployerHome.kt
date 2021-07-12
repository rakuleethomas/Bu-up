package org.rakulee.buup.fragments.employer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.JobSeekerListAdapter
import org.rakulee.buup.databinding.FragmentEmployerHomeBinding
import org.rakulee.buup.model.EmpInfo
import org.rakulee.buup.model.JobSeekerItem
import org.rakulee.buup.viewmodel.EmployerViewModel
import org.rakulee.buup.viewmodel.PaymentViewModel
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerHome.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EmployerHome : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentEmployerHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private val employerViewModel : EmployerViewModel by viewModels()


    override fun onResume() {
        super.onResume()
        employerViewModel.fetchEmployerData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employer_home, container, false)
        binding.lifecycleOwner = this
        binding.vm = employerViewModel

        /**
         * create dummy data
         */

        var list = ArrayList<JobSeekerItem>()
        for(i : Int in 1..20){
            var data = if(i%2 == 1){
                JobSeekerItem("Jake","svkoreans@gmail.com", "Bay Area Professional Realtor")
            }else{
                JobSeekerItem("Thomas", "thomas@rakulee.org", "Android Developer")
            }
            list.add(data)
        }

        val adapter = JobSeekerListAdapter()
        adapter.setHasStableIds(true)       // prevent blinking recyclerview items
        adapter.updateItems(list)
        binding.rvJobSeekerList.adapter = adapter
        binding.rvJobSeekerList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.btnChargePoints.setOnClickListener(this)

        return binding.root
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnChargePoints.id -> doChargePoint()
        }
    }

    private fun doChargePoint(){
        val directions : NavDirections = EmployerHomeDirections.actionMainEmpHomeToPaymentActivity()
        findNavController().navigate(directions)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerTest.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerHome().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}