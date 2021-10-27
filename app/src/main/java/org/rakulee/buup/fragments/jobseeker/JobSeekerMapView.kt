package org.rakulee.buup.fragments.jobseeker

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentJobSeekerMapViewBinding
import org.rakulee.buup.util.Util
import org.rakulee.buup.viewmodel.EmployerViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JobSeekerMapView.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class JobSeekerMapView : Fragment(), GoogleMap.OnMarkerClickListener, OnMapReadyCallback {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding : FragmentJobSeekerMapViewBinding

    var mapFragment : SupportMapFragment? = SupportMapFragment()
    val addressLists = ArrayList<Pair<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        buildDummy("Chimek", "3597 Homestead Rd, Santa Clara, CA 95051")
        buildDummy("Chungdam", "3180 El Camino Real, Santa Clara, CA 95051")
        buildDummy("10 Butchers", "595 E El Camino Real, Sunnyvale, CA 94087")
        buildDummy("SVKoreans", "3167 Impala Dr, #4, San Jose, CA 95117")
        buildDummy("Rakulee", "155 E. Campbell Ave, #111, Campbell, CA 95008")

        buildDummy("Chimek2", "94087")
        buildDummy("Chungdam2", "94087")
        buildDummy("10 Butchers2", "94087")
        buildDummy("SVKoreans2", "95117")
        buildDummy("Rakulee", "95008")



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
        binding.lifecycleOwner = this
        binding.fragment = this

        binding.selectedMarker = this.selectedMarker;
//        binding.containerJobDetails.setOnClickListener { showDetail() }


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

    override fun onMarkerClick(p0: Marker): Boolean {
        binding.selectedMarker = true
        return true
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.uiSettings.isMapToolbarEnabled = false
//        val chimek = LatLng(37.338732, -121.994956)
//        val elPolloLoco = LatLng(37.352901144915556, -121.97108295384102)
//        val pokeatery = LatLng(37.3248567860572, -121.94719594156945)

        val geoCoder = Geocoder(context)

        // need to fetch store name & address info from the Bu-up server in the future

        var latitude : Double = 0.0
        var longitude : Double = 0.0


        val width = Util.dpToPx( 32, requireContext()).toInt()
        val height = Util.dpToPx(38, requireContext()).toInt()


        for(address in addressLists){
            val resultAddressList = geoCoder.getFromLocationName(address.second, 5)
            for(item in resultAddressList){
                latitude = item.latitude
                longitude = item.longitude
                val latLng = LatLng(latitude, longitude)
                val buupPurpleIcon = getDrawable(requireContext(), R.drawable.ic_bu_up_purple)!!.toBitmap(width, height)
                val buupMarkerOptions = MarkerOptions().position(latLng).icon(
                    BitmapDescriptorFactory.fromBitmap(buupPurpleIcon))
                googleMap.addMarker(buupMarkerOptions)
                googleMap.setOnMarkerClickListener(this@JobSeekerMapView)
            }
        }

        GlobalScope.launch(Dispatchers.Main) {
            googleMap.setMinZoomPreference(13.0f)
            launch {
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(latitude, longitude)))
            }
        }

//        googleMap.addMarker(MarkerOptions().position(chimek).title("Chimek"))
//        googleMap.addMarker(MarkerOptions().position(elPolloLoco).title("El Pollo Loco"))
//        googleMap.addMarker(MarkerOptions().position(pokeatery).title("Pokeatery"))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f), 3000, null)
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

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }


}