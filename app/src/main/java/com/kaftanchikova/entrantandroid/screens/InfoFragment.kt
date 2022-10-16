package com.kaftanchikova.entrantandroid.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.kaftanchikova.entrantandroid.R
import com.kaftanchikova.entrantandroid.databinding.FragmentInfoBinding
import com.kaftanchikova.entrantandroid.repository.Repository
import com.kaftanchikova.entrantandroid.view.*
import kotlinx.coroutines.delay

class InfoFragment : Fragment() {

    lateinit var binding: FragmentInfoBinding
    private val viewModel: InfoViewModel by viewModels{ factory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadEntrant(requireArguments().getLong(ARG_ENTRANT_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoBinding.inflate(inflater)

        viewModel.infoDetails.observe(viewLifecycleOwner, Observer {
            binding.nameTextView.text = "${it.name} ${it.surname} ${it.patronymic}, ${it.age} years"
            Glide.with(this)
                .load(R.drawable.ic_user)
                .into(binding.photeImageView)
            binding.emailTextView.text = it.email
            binding.phoneTextView.text = it.phone
            binding.infoTextView.text = it.info
            binding.statusTextView.text =
                if(it.status) context?.getString(R.string.enroll)
                else context?.getString(R.string.do_not_enroll)
        })

        binding.btDelete.setOnClickListener {
            viewModel.deleteEntrant()
            navigator().toast(R.string.entrant_has_been_deleted)
            navigator().goBack()
        }

        binding.btEnroll.setOnClickListener {
            viewModel.enrollEntrant()
            navigator().toast(R.string.entrant_has_been_enroll)
            binding.statusTextView.text = context?.getString(R.string.enroll)
        }

        return binding.root
    }

    companion object {

        private const val ARG_ENTRANT_ID = "ARG_ENTRANT_ID"

        fun newInstance(entrantId: Long): InfoFragment{
            val fragment = InfoFragment()
            fragment.arguments = bundleOf ( ARG_ENTRANT_ID to entrantId )
            return fragment
        }
    }
}