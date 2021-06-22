package org.rakulee.buup.fragments.common

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import org.rakulee.buup.R
import org.rakulee.buup.databinding.FragmentPaymentBinding
import sqip.CardEntry

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PaymentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PaymentFragment : Fragment() {

    lateinit var binding : FragmentPaymentBinding
    val DEFAULT_CARD_ENTRY_REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        binding.lifecycleOwner = this
        binding.vm = this
        return binding.root
    }


    fun doCheckOut(){
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
        activity?.let { CardEntry.startCardEntryActivity(it, true, DEFAULT_CARD_ENTRY_REQUEST_CODE) }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


    }
}