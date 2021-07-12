package org.rakulee.buup.fragments.employer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.adapters.EmployerSearchListAdapter
import org.rakulee.buup.adapters.JobSearchListAdapter
import org.rakulee.buup.databinding.FragmentEmployerSearchBinding
import org.rakulee.buup.model.EmployerSearchItem
import org.rakulee.buup.model.JobSearchItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerSearch.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EmployerSearch : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentEmployerSearchBinding

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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_employer_search, container, false)
        binding.lifecycleOwner = this
        binding.loading = true

        /**
         *  create a dummy data
         *
         *  data class EmployerSearchItem(
            var seekerUserId: String,
            var seekerTitle: String,
            var location: String,
            var profileUrl: String
            )
         * */


        val list = ArrayList<EmployerSearchItem>()
        val adapter = EmployerSearchListAdapter()
        var jobSeeker : EmployerSearchItem

        for(i : Int in 1 .. 20){
            jobSeeker = if(i%2 == 1){
                EmployerSearchItem("Jake","Real Estate Broker, Loan Officer, Real Estate Appraiser", "San Jose","https://svkoreans.com/data/editor/2106/090950c8fc6f5b70802a3b1332b25246_1623993409_0159.gif")
            }else{
                EmployerSearchItem("Thomas","Android Developer, NPO", "San Jose","https://svkoreans.com/data/editor/2106/090950c8fc6f5b70802a3b1332b25246_1623993409_0159.gif")
            }
            list.add(jobSeeker)
        }

        adapter.update(list)
        adapter.setHasStableIds(true)
        binding.rvJobSeekerList.adapter = adapter
        binding.rvJobSeekerList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerTest2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerSearch().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}