package org.rakulee.buup.fragments.jobseeker

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location.distanceBetween
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentJobSeekerMapViewBinding
import org.rakulee.buup.model.BuupGetJobPostingByDistanceResponse
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.repo.BuupAPIRepo
import org.rakulee.buup.util.Util
import org.rakulee.buup.viewmodel.EmployerViewModel
import org.rakulee.buup.viewmodel.JobSeekerViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject
import android.content.DialogInterface




// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerMapView.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class JobSeekerMapView : Fragment(R.layout.fragment_job_seeker_map_view), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentJobSeekerMapViewBinding

    var mapFragment : SupportMapFragment? = SupportMapFragment()
    lateinit var map : GoogleMap
    val addressLists = ArrayList<Pair<String, String>>()

    var addrList = ArrayList<BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem>()

    val jobSeekerViewModel by activityViewModels<JobSeekerViewModel>()
//    val jobSeekerViewModel : JobSeekerViewModel by navGraphViewModels(R.id.nav_graph_job_seeker)
//    val jobSeekerViewModel : JobSeekerViewModel by navGraphViewModels(R.id.nav_graph_job_seeker)

    var jobseekerInfo : BuupJobSeekerProfile? = null


    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    var currentLatLng = LatLng(0.0, 0.0)

    @Inject lateinit var buupRepo : BuupAPIRepo
//    @Inject lateinit var jobseekerViewModel : JobSeekerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private val employerViewModel : EmployerViewModel by viewModels()
    val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    var selectedMarker : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_seeker_map_view, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        binding.isSelectedMarker = false

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // loading data
        jobSeekerViewModel.buupJobSeekerProfile.observe(viewLifecycleOwner, Observer { item ->

            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                val jsonObject = JsonObject()
                jobseekerInfo = item
                jsonObject.addProperty("latitude", jobseekerInfo?.latitude?.toDouble())
                jsonObject.addProperty("longitude", jobseekerInfo?.longitude?.toDouble())
                jsonObject.addProperty("distance", 50)
                if (jobseekerInfo != null) {
                    val jsonString = jsonObject.toString()
                    val requestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
                    addrList = buupRepo.getJobByDistance( jobseekerInfo?.latitude!!, jobseekerInfo?.longitude!!, 50).body()!!
                    jobSeekerViewModel.updateCurrentJobListByDistance(addrList)
                }
            }

        })






        binding.fab.setOnClickListener{
            val direction : NavDirections = JobSeekerMapViewDirections.actionMainSeekerHomeToMainSeekerListview3()
            findNavController().navigate(direction)
        }
        binding.btnFilter.setOnClickListener{
            val directions : NavDirections = JobSeekerMapViewDirections.actionMainSeekerHomeToMainSeekerFilter()
            findNavController().navigate(directions);
        }


        return binding.root

    }

    fun getLastKnownLocation(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if(location != null){
                    currentLatLng = LatLng(location.latitude, location.longitude)
                }
            }
    }

    fun goJobDetail(){
        val directions : NavDirections = JobSeekerMapViewDirections.actionMainSeekerHomeToJobSeekerJobDetail()
        findNavController().navigate(directions)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JobSeekerMapView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JobSeekerMapView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    lateinit var currentMarker : Marker
    lateinit var oldMarker : Marker

    override fun onMarkerClick(marker: Marker): Boolean {
        binding.isSelectedMarker = true
        val currentItem : BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem =
            marker.tag as BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem
        binding.currentMarkerItem = currentItem
        val results = FloatArray(1)
        val currentLatLng = LatLng(currentItem.latitude.toDouble(), currentItem.longitude.toDouble())
        val currentZipCode = LatLng(0.0,0.0)

        distanceBetween(
            currentItem.latitude.toDouble()-0.001, currentItem.longitude.toDouble()-0.001,
            currentItem.latitude.toDouble(), currentItem.longitude.toDouble(),
            results
        )
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING

        binding.currentMarkerItemDistanceFromYourZipcode = df.format(results[0]/1.6) + "Miles"
        map.animateCamera(CameraUpdateFactory.newLatLng(currentLatLng))
        return true
    }


    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog.Builder(requireContext())
                    .setTitle("Location Permission Needed")
                    .setMessage("This app needs the Location permission, please accept to use location functionality")
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialogInterface, i -> //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(
                                this@JobSeekerMapView.requireActivity(),
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                MY_PERMISSIONS_REQUEST_LOCATION
                            )
                        })
                    .create()
                    .show()
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                googleMap.isMyLocationEnabled = true
            } else {
                //Request Location Permission
                checkLocationPermission()
            }
        }
        else {
            googleMap.isMyLocationEnabled = true
        }


        googleMap.setOnMapClickListener {
            binding.isSelectedMarker = false
        }
        googleMap.uiSettings.isMapToolbarEnabled = false
        googleMap.uiSettings.isTiltGesturesEnabled = false
        map = googleMap

//        val chimek = LatLng(37.338732, -121.994956)
//        val elPolloLoco = LatLng(37.352901144915556, -121.97108295384102)
//        val pokeatery = LatLng(37.3248567860572, -121.94719594156945)

        val geoCoder = Geocoder(context)

        // need to fetch store name & address info from the Bu-up server in the future

        var latitude : Double = 0.0
        var longitude : Double = 0.0


        val width = Util.dpToPx( 32, requireContext()).toInt()
        val height = Util.dpToPx(38, requireContext()).toInt()




//        for(address in addressLists){
//            val resultAddressList = geoCoder.getFromLocationName(address.second, 5)
//            for(item in resultAddressList){
//                latitude = item.latitude
//                longitude = item.longitude
//                val latLng = LatLng(latitude, longitude)
//                val buupPurpleIcon = getDrawable(requireContext(), R.drawable.ic_bu_up_purple)!!.toBitmap(width, height)
//                val buupMarkerOptions = MarkerOptions().position(latLng).icon(
//                    BitmapDescriptorFactory.fromBitmap(buupPurpleIcon))
//                googleMap.addMarker(buupMarkerOptions)
//                googleMap.setOnMarkerClickListener(this@JobSeekerMapView)
//            }
//        }

        /* Google Map run on Main Thread for */
        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        scope.launch {
            jobSeekerViewModel.currentJobListByDistance.observe(viewLifecycleOwner, {list ->
                Log.d("JobSeekerMapView", "onCreateView: ${list.size}")
                addrList = list
                for(item in addrList){
                    latitude = item.latitude.toDouble()
                    longitude = item.longitude.toDouble()
                    val latLng = LatLng(item.latitude.toDouble(), item.longitude.toDouble())
                    val buupPurpleIcon = getDrawable(requireContext(), R.drawable.ic_bu_up_purple)!!.toBitmap(width, height)
                    val buupMarkerOptions = MarkerOptions().position(latLng).icon(
                        BitmapDescriptorFactory.fromBitmap(buupPurpleIcon))
                    googleMap.addMarker(buupMarkerOptions)?.tag = item
                    googleMap.setOnMarkerClickListener(this@JobSeekerMapView)

                }

//                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude),15.0f))
            })
        }
    }


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */



    }

    fun buildDummy(storeName : String, address : String){
        addressLists.add(Pair(storeName, address))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this@JobSeekerMapView)
    }

    override fun onPause() {
        mapFragment?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapFragment?.onDestroy()
        super.onDestroy()
        scope.cancel()
    }

    override fun onDestroyView() {
        mapFragment?.onDestroyView()
        super.onDestroyView()
    }

    //    fun setupMap(){
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return
//        }
//        map.setMyLocationEnabled(true)
//    }


}