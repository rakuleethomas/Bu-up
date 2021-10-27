package org.rakulee.buup.fragments.jobseeker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.parse.ParseQuery
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.rakulee.buup.R
import org.rakulee.buup.adapters.EmployerSavedListAdapter
import org.rakulee.buup.adapters.JobListAdapter
import org.rakulee.buup.databinding.FragmentJobSeekerHomeBinding
import org.rakulee.buup.fragments.employer.EmployerSaved
import org.rakulee.buup.model.*
import org.rakulee.buup.repo.BuupAPIRepo
import org.rakulee.buup.screens.PartTimeJobSeekerActivity
import org.rakulee.buup.viewmodel.JobSeekerViewModel
import retrofit2.Response
import javax.inject.Inject

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
    private val viewModel: JobSeekerViewModel by activityViewModels()
    private var TAG = "JobSeekerHome"
    val list = ArrayList<EmployerSavedListItem>()
    val adapter = EmployerSavedListAdapter()


    @Inject
    lateinit var buupRepo : BuupAPIRepo


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    suspend fun getPostings(list: ArrayList<EmployerSavedListItem>){

        val jsonObject = JsonObject()
        jsonObject.addProperty("latitude", viewModel.buupJobSeekerProfile.value!!.latitude)
        jsonObject.addProperty("longitude", viewModel.buupJobSeekerProfile.value!!.longitude)
        jsonObject.addProperty("distance", 100)
        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
        val postingResponse : Response<BuupGetJobPostingByDistanceResponse> = buupRepo.getJobByDistance(requestBody)


        if(postingResponse.isSuccessful){
            CoroutineScope(Dispatchers.Main).launch {
                var i = 0
                for (body in postingResponse.body()!!){

                    var savedItem : EmployerSavedListItem = EmployerSavedListItem(
                        body.jobTitle,
                        body.companyName,
                        body.payRateLow,
                        body.payRateHigh,
                        body.city,
                        "${i}",
                        body.liked
                    )
                    i+=1
                    list.add(savedItem)
                }
            }
            Log.d("Job Postings", "posting size: ${postingResponse.body()!!.size}")

        }else{
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Incorrect login information!", Toast.LENGTH_SHORT).show()
                Log.d("LOGIN", "login: Error")
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_home, container, false)


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

        CoroutineScope(Dispatchers.Main).launch{
            getPostings(list)
            delay(500)
            adapter.update(list)
//            adapter.setHasStableIds(true)

            binding.rvSavedJob.adapter = adapter
            binding.rvSavedJob.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            binding.fabMapMode.setOnClickListener {
                val direction : NavDirections = JobSeekerHomeDirections.actionMainSeekerListviewToMainSeekerHome2()
                findNavController().navigate(direction)
            }
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