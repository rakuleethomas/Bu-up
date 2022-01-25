package org.rakulee.buup.fragments.jobseeker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.rakulee.buup.R
import org.rakulee.buup.adapters.EmployerSavedListAdapter
import org.rakulee.buup.databinding.FragmentJobSeekerSavedJobsBinding
import org.rakulee.buup.model.BuupGetJobPostingByDistanceResponse
import org.rakulee.buup.model.EmployerSavedListItem
import org.rakulee.buup.repo.BuupAPIRepo
import org.rakulee.buup.viewmodel.JobSeekerViewModel
import retrofit2.Response
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerSavedJobs.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class JobSeekerSavedJobs : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentJobSeekerSavedJobsBinding

    private val viewModel: JobSeekerViewModel by activityViewModels()
    private var TAG = "JobSeekerSaved"
    val list = ArrayList<EmployerSavedListItem>()
    val adapter = EmployerSavedListAdapter()


    @Inject
    lateinit var buupRepo: BuupAPIRepo

    suspend fun getPostings(list: ArrayList<EmployerSavedListItem>) {

        val latitude =  viewModel.buupJobSeekerProfile.value!!.latitude
        val longitude = viewModel.buupJobSeekerProfile.value!!.longitude
        val distance = 100
        val postingResponse: Response<BuupGetJobPostingByDistanceResponse> =
            buupRepo.getJobByDistance(latitude, longitude, distance)


        if (postingResponse.isSuccessful) {
            CoroutineScope(Dispatchers.Main).launch {
                var i = 0
                for (body in postingResponse.body()!!) {
                    if (body.liked) {
                        var savedItem: EmployerSavedListItem = EmployerSavedListItem(
                            body.jobTitle,
                            body.companyName,
                            body.payRateLow,
                            body.payRateHigh,
                            body.city,
                            "${i}",
                            body.liked
                        )
                        i += 1
                        list.add(savedItem)
                    }
                }
                Log.d("Job Postings", "posting size: ${postingResponse.body()!!.size}")
            }

        } else {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(context, "Incorrect login information!", Toast.LENGTH_SHORT)
                    .show()
                Log.d("LOGIN", "login: Error")
            }
        }


    }


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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_job_seeker_saved_jobs,
            container,
            false
        )
        binding.lifecycleOwner = this

        CoroutineScope(Dispatchers.Main).launch{
            getPostings(list)
            delay(500)
            adapter.update(list)
//            adapter.setHasStableIds(true)

            binding.rvSavedJob.adapter = adapter
            binding.rvSavedJob.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
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
         * @return A new instance of fragment JobSeekerSavedJobs.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerSavedJobs().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}