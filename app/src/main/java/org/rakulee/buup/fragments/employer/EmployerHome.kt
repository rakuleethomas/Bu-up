package org.rakulee.buup.fragments.employer

import android.graphics.Point
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.work.WorkManager
import com.google.android.gms.maps.*
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.internal.notifyAll
import org.rakulee.buup.R
import org.rakulee.buup.adapters.EmployerHomeJobPostingListAdapter
import org.rakulee.buup.adapters.EmployerHomeRecommendedListAdapter
import org.rakulee.buup.databinding.FragmentEmployerHomeBinding
import org.rakulee.buup.fragments.jobseeker.JobSeekerProfile
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.model.EmployerHomeJobPostingItem
import org.rakulee.buup.model.EmployerHomeRecommendedItem
import org.rakulee.buup.model.JobSeekerSignInResponse
import org.rakulee.buup.util.Util
import org.rakulee.buup.viewmodel.EmployerViewModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.coroutines.coroutineContext

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
class EmployerHome : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentEmployerHomeBinding

    val viewModel : EmployerViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    private val employerViewModel : EmployerViewModel by viewModels()
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    var markerSelected : Boolean = false



    enum class BadgeStatus{
        Healthcare, Airline, ArtMedia, Automotive, Cleaner, Hotel, Law, Manufacture, Moving, Pharmacy, Policy, Childcare
    }

    override fun onResume() {
        super.onResume()
//        employerViewModel.fetchEmployerData()

//        scope.launch {
//            mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//            mapFragment.getMapAsync(callback)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employer_home, container, false)
        binding.lifecycleOwner = this


        // RecyclerView setup
        val jobPostingAdapter = EmployerHomeJobPostingListAdapter()
        binding.rvJobPosting.adapter = jobPostingAdapter
        val jobPostingList = ArrayList<EmployerHomeJobPostingItem>()
        jobPostingList.add(EmployerHomeJobPostingItem("Server", 100, "$100","$100", "Expires in 30 days"))
        jobPostingList.add(EmployerHomeJobPostingItem("Cashier", 101, "$100","$100", "Expires in 30 days"))
        jobPostingList.add(EmployerHomeJobPostingItem("Cleaner", 104, "$100","$100", "Expires in 30 days"))
        jobPostingList.add(EmployerHomeJobPostingItem("Server", 105, "$100","$100", "Expires in 30 days"))
        jobPostingAdapter.updateItems(jobPostingList)
        jobPostingAdapter.notifyDataSetChanged()


        val recommendedAdapter = EmployerHomeRecommendedListAdapter()
        binding.rvRecommended.adapter = recommendedAdapter
        val recommendedItemList = ArrayList<EmployerHomeRecommendedItem>()
        val jobSeekerProfileList = ArrayList<BuupJobSeekerProfile>()
        lateinit var tempJobSeekerProfile : BuupJobSeekerProfile
        var tempBadgeList = ArrayList<JobSeekerSignInResponse.Message.Badge>()
        val tempIndustryList = ArrayList<String>()

        val nameMap = HashMap<String, String>()
        nameMap.put("Jake","Min")
        nameMap.put("Thomas","Han")
        nameMap.put("Sean","Jung")
        nameMap.put("Peter","Stevenson")
        nameMap.put("Jessica","Shin")
        nameMap.put("Sylvia","Campbell")
        nameMap.put("Paul","Simon")
        nameMap.put("Sam","Hamilton")

//        Healthcare, Airline, ArtMedia, Automotive, Cleaner, Hotel, Law, Manufacture, Moving, Pharmacy, Policy, Childcare
        for(i in 1..5){
            tempBadgeList.add(JobSeekerSignInResponse.Message.Badge("",1,BadgeStatus.Healthcare.toString()))
        }

        val badgeMap = HashMap<String, ArrayList<JobSeekerSignInResponse.Message.Badge>>()
        var badgePos = 0
        for(name in nameMap){
            tempBadgeList = ArrayList<JobSeekerSignInResponse.Message.Badge>()
            for(i in 1..5){
                tempBadgeList.add(JobSeekerSignInResponse.Message.Badge("",1, BadgeStatus.values()[badgePos%12].toString()))
                badgePos++
            }
            badgeMap.put(name.key, tempBadgeList)
        }



        tempIndustryList.add("Restaurant")
        tempIndustryList.add("Design")
        tempIndustryList.add("Research Industry")
        tempIndustryList.add("Packaging/Containers")

        var index = 0
        val availabilityList = ArrayList<ArrayList<Boolean>>()
        for(name in nameMap){
            tempJobSeekerProfile = BuupJobSeekerProfile()
            tempJobSeekerProfile.badgeList = badgeMap.get(name.key)!!
            tempJobSeekerProfile.firstName = name.key
            tempJobSeekerProfile.lastName = name.value
            tempJobSeekerProfile.email = "${name.key}.${name.value}@svkoreans.com"
            tempJobSeekerProfile.industry = tempIndustryList
            tempJobSeekerProfile.verified = (index%2==0)
            tempJobSeekerProfile.wageMin = "$${20+index}"
            tempJobSeekerProfile.wageMax = "$${35+index}"
            index++
            jobSeekerProfileList.add(tempJobSeekerProfile)
        }
        viewModel.updateJobSeekerList(jobSeekerProfileList)
        recommendedItemList.add(EmployerHomeRecommendedItem("Restaurant", jobSeekerProfileList))
        recommendedItemList.add(EmployerHomeRecommendedItem("Arts/Crafts", jobSeekerProfileList))
        recommendedItemList.add(EmployerHomeRecommendedItem("Design", jobSeekerProfileList))
        recommendedItemList.add(EmployerHomeRecommendedItem("Packaging/Containers", jobSeekerProfileList))
        recommendedAdapter.update(recommendedItemList)
//        recommendedAdapter.notifyDataSetChanged()


        binding.fabWritePost.setOnClickListener {
            val directions : NavDirections = EmployerHomeDirections.actionMainEmpDashboardToEmployerJobPosting()
            findNavController().navigate(directions)
        }


        return binding.root
    }

    fun buildDummy(){
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }

    private fun doChargePoint(){
//        val directions : NavDirections = EmployerHomeDirections.actionMainEmpHomeToPaymentActivity()
//        findNavController().navigate(directions)
    }

    private fun showDetail(){
        val directions : NavDirections = EmployerHomeDirections.actionMainEmpHomeToEmployerJobDetail()
        findNavController().navigate(directions)
    }

    private fun showLists(){
        val directions : NavDirections = EmployerHomeDirections.actionMainEmpHomeToEmployerSaved()
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