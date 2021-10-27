package org.rakulee.buup.fragments.employer.message

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.rakulee.buup.R
import org.rakulee.buup.adapters.EmployerMessageGroupAdapter
import org.rakulee.buup.databinding.FragmentEmployerMessageGroupBinding
import org.rakulee.buup.model.EmployerMessageGroupItem

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EmployerMessageGroup.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployerMessageGroup : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val messageArgs : EmployerMessageGroupArgs by navArgs()

    lateinit var binding : FragmentEmployerMessageGroupBinding

    class Decoration : RecyclerView.ItemDecoration(){
        private val padding = 8
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = padding
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employer_message_group, container, false)

        val adapter = EmployerMessageGroupAdapter()
        val messageGroupList = ArrayList<EmployerMessageGroupItem>()

        messageGroupList.add(EmployerMessageGroupItem("Warehouse Part-Time worker", "Posted on: Oct 25, 2021", 4))
        messageGroupList.add(EmployerMessageGroupItem("Cashier", "Posted on: Oct 21, 2021", 8))
        messageGroupList.add(EmployerMessageGroupItem("Developer", "Posted on: Oct 19, 2021", 2))
        binding.rvMessageCard.addItemDecoration(Decoration())
        binding.rvMessageCard.adapter = adapter
        adapter.update(messageGroupList)

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EmployerMessageGroup.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EmployerMessageGroup().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}