package org.rakulee.buup.fragments.employer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentEmployerHomeBinding
import org.rakulee.buup.viewmodel.EmployerViewModel

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
class EmployerHome : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentEmployerHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    private val employerViewModel : EmployerViewModel by viewModels()



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

        val chimek = LatLng(37.338732, -121.994956)
        val elPolloLoco = LatLng(37.352901144915556, -121.97108295384102)
        val pokeatery = LatLng(37.3248567860572, -121.94719594156945)
        googleMap.addMarker(MarkerOptions().position(chimek).title("Chimek"))
        googleMap.addMarker(MarkerOptions().position(elPolloLoco).title("El Pollo Loco"))
        googleMap.addMarker(MarkerOptions().position(pokeatery).title("Pokeatery"))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f), 3000, null)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(chimek))
        googleMap.setMinZoomPreference(12.0f)
    }


    override fun onResume() {
        super.onResume()
        employerViewModel.fetchEmployerData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employer_home, container, false)
        binding.lifecycleOwner = this
        binding.vm = employerViewModel


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnChargePoints.id -> doChargePoint()
        }
    }

    private fun doChargePoint(){
        val directions : NavDirections = EmployerHomeDirections.actionMainEmpHomeToPaymentActivity()
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