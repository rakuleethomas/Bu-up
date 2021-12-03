package org.rakulee.buup.fragments.employer.employer_post

import android.app.DatePickerDialog
import android.location.Geocoder
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentEmployerJobPostingBinding
import org.rakulee.buup.model.JobPostingItem
import org.rakulee.buup.repo.BuupAPIRepo
import org.rakulee.buup.screens.PartTimeEmployerActivity
import org.rakulee.buup.viewmodel.EmployerViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerJobPosting.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class EmployerJobPosting : Fragment(), PartTimeEmployerActivity.EmployerJobPostingHelper {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentEmployerJobPostingBinding

    val viewModel : EmployerViewModel by activityViewModels()

    @Inject
    lateinit var buupRepo : BuupAPIRepo

    enum class Edit {
        Title, WageShiftDate, HiringNumber, PositionDescription
    }


    override fun updateStatus(editBtn : Edit) {
        when(editBtn){
            Edit.Title -> {binding.etJobTitle.requestFocus()}
            Edit.PositionDescription -> {binding.etPositionDescription.requestFocus()}
            Edit.HiringNumber -> {binding.etHiringNumber.requestFocus()}
            Edit.WageShiftDate -> {binding.etWageRange.requestFocus()}
        }
    }

    fun validate() : Boolean {
        return binding.etHiringNumber.text.isNullOrEmpty()
                || binding.etHour.text.isNullOrEmpty()
                || binding.etJobTitle.text.isNullOrEmpty()
                || binding.etPositionDescription.text.isNullOrEmpty()
                || binding.etPostingDate.text.isNullOrEmpty()
                || binding.etStartingDate.text.isNullOrEmpty()
                || binding.etWageRange.text.isNullOrEmpty()
    }

    fun publishJobPost(){
        if(validate()){
            AlertDialog.Builder(requireContext(), R.style.buup_employer_dialog_theme)
                .setTitle("Missing Field!")
                .setMessage("Please check all the fields are filled")
                .setPositiveButton("Confirm") { _, _ ->  }
                .create()
                .show()
            return
        }
        val jsonObject = JsonObject()
        val employerInfo = viewModel.employerInfo
        var wageLow = "$0"
        var wageHigh = "$25"
        val pattern2 = Regex("-")
        val ans : List<String> = pattern2.split(binding.etWageRange.text.toString())
        wageLow = ans[0].trim()
        wageHigh = wageLow

        var latitude = 0.0
        var longitude = 0.0
        val geoCoder = Geocoder(context)
        val resultAddressList = geoCoder.getFromLocationName(employerInfo.value!!.companyInfo.zipCode, 1)
        for(item in resultAddressList){
            latitude = item.latitude
            longitude = item.longitude
        }

        jsonObject.addProperty("postId", (System.currentTimeMillis().toString()+employerInfo.value!!.email).hashCode().toString())
        jsonObject.addProperty("employer", employerInfo.value!!.loginId)
        jsonObject.addProperty("jobTitle", binding.etJobTitle.text.toString())
        jsonObject.addProperty("companyName", employerInfo.value!!.companyInfo.name)
        jsonObject.addProperty("logo", employerInfo.value!!.companyInfo.logoUrl)
        jsonObject.addProperty("payRateLow", wageLow)
        jsonObject.addProperty("payRateHigh", wageHigh)
        jsonObject.addProperty("city", employerInfo.value!!.companyInfo.city)
        jsonObject.addProperty("liked", false)
        jsonObject.addProperty("latitude", latitude)
        jsonObject.addProperty("longitude", longitude)
        val jsonString = jsonObject.toString()
        val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
        CoroutineScope(Dispatchers.IO).launch {
            val result = buupRepo.employerAddJobPosting(requestBody)
            if(result.isSuccessful){
                CoroutineScope(Dispatchers.Main).launch {
                    AlertDialog.Builder(requireContext(), R.style.buup_employer_dialog_theme)
                        .setTitle("Success!")
                        .setMessage("Your hiring post has been successfully posted!")
                        .setPositiveButton("Confirm") { _, _ ->  requireActivity().onBackPressed()}
                        .create()
                        .show()
                }
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
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_employer_job_posting, container, false)
        binding.lifecycleOwner = this
        binding.fragment = this

        val calendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener{
                datePicker, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        }

        binding.etWageRange.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                return
            }

            override fun afterTextChanged(editable: Editable?) {
                when {
                    editable.isNullOrEmpty() -> return
                    Regex("\\$\\d+").matches(editable.toString()) -> return
                    editable.toString() == "$" -> editable.clear()
                    editable.startsWith("$").not() -> editable.insert(0, "$")
                }
            }
        })

        binding.etPostingDate.setOnClickListener {
            DatePickerDialog(requireContext(), date,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show()
            val format = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(format, Locale.US)
            binding.etPostingDate.setText(sdf.format(calendar.time))
        }
        binding.etStartingDate.setOnClickListener {
            DatePickerDialog(requireContext(), date,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                .show()
            val format = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(format, Locale.US)
            binding.etStartingDate.setText(sdf.format(calendar.time))
        }

//        var companyImageUrl = ParseUser.getCurrentUser().get("CompanyImageUrl").toString()
////            val uri = Uri.parse("https://svkoreans.com/img/svlogo1-1.jpg");
//        if("".equals(companyImageUrl)){
//            companyImageUrl = "https://svkoreans.com/img/svlogo1-1.jpg";
//        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerJobPosting.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerJobPosting().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}