package org.rakulee.buup.fragments.employer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentEmployerMessageBinding
import org.rakulee.buup.model.BuupJobSeekerProfile

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerMessage.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EmployerMessage : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentEmployerMessageBinding
    private val args : EmployerMessageArgs by navArgs()
    lateinit var jobSeekerProfile : BuupJobSeekerProfile

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
        val gson = Gson()
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_employer_message, container, false)
        binding.lifecycleOwner = this
        binding.buupJobSeekerProfile = gson.fromJson<BuupJobSeekerProfile>(args.jobSeekerProfile, BuupJobSeekerProfile::class.java)
        jobSeekerProfile = gson.fromJson<BuupJobSeekerProfile>(args.jobSeekerProfile, BuupJobSeekerProfile::class.java)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerMessage.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerMessage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}