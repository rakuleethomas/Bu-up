package org.rakulee.buup.fragments.employer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.parse.ParseUser
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentEmployerProfileBinding
import org.rakulee.buup.fragments.jobseeker.profileEdit.ProfileInterestEditDirections

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerProfile.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class EmployerProfile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding : FragmentEmployerProfileBinding

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

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_employer_profile, container, false)
        binding.lifecycleOwner = this
        binding.employerProfile = this

        var EmployerimageUrl = ParseUser.getCurrentUser().get("ImageUrl").toString()
//            val uri = Uri.parse("https://svkoreans.com/img/svlogo1-1.jpg");
        if("".equals(EmployerimageUrl)){
            EmployerimageUrl = "https://svkoreans.com/img/svlogo1-1.jpg";
        }
        Glide.with(requireContext()).load(EmployerimageUrl).into(binding.ivEmployer)

        var companyImageUrl = ParseUser.getCurrentUser().get("CompanyImageUrl").toString()
//            val uri = Uri.parse("https://svkoreans.com/img/svlogo1-1.jpg");
        if("".equals(companyImageUrl)){
            companyImageUrl = "https://svkoreans.com/img/svlogo1-1.jpg";
        }
        Glide.with(requireContext()).load(companyImageUrl).into(binding.ivCompanyLogo)
        
        val firstName = ParseUser.getCurrentUser().get("FirstName").toString()
        val lastName = ParseUser.getCurrentUser().get("LastName").toString()
        binding.tvEmployerName.text = "${firstName} ${lastName}"
        binding.tvCompanyName.text = ParseUser.getCurrentUser().get("CompanyTitle").toString()



        binding.btnEdit.setOnClickListener{
            val direction : NavDirections = EmployerProfileDirections.actionMainEmpProfileToProfileEdit()
            findNavController().navigate(direction)
        }

        binding.button.setOnClickListener{
            val direction : NavDirections = EmployerProfileDirections.actionMainEmpProfileToJobPosting()
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
         * @return A new instance of fragment EmployerTest3.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}