package org.rakulee.buup.fragments.employer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.EmployerSavedJobSeekersAdapter
import org.rakulee.buup.adapters.EmployerSavedListAdapter
import org.rakulee.buup.databinding.FragmentEmployerSavedBinding
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.model.EmployerSavedListItem
import org.rakulee.buup.viewmodel.EmployerViewModel

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
    val viewModel : EmployerViewModel by activityViewModels()

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


        val list : ArrayList<BuupJobSeekerProfile> = viewModel.jobSeekerList.value!!
        val adapter = EmployerSavedJobSeekersAdapter()
        adapter.update(list)


        binding.rvSavedJob.adapter = adapter
        binding.rvSavedJob.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return binding.root
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