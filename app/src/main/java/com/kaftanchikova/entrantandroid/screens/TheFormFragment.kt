package com.kaftanchikova.entrantandroid.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kaftanchikova.entrantandroid.databinding.FragmentTheFormBinding
import com.kaftanchikova.entrantandroid.model.Entrant
import com.kaftanchikova.entrantandroid.view.TheFormModel
import com.kaftanchikova.entrantandroid.view.factory

class TheFormFragment : Fragment() {

    lateinit var binding: FragmentTheFormBinding
    private val viewModel: TheFormModel by viewModels{ factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheFormBinding.inflate(inflater)
        binding.btSend.setOnClickListener {
            var entrant = Entrant(0L, binding.edName.text.toString(), binding.edSurname.text.toString(),
                binding.edPatronymic.text.toString(), binding.edAge.text.toString().toInt(), binding.edEmail.text.toString(),
                binding.edPhone.text.toString(), binding.edInfo.text.toString(),false)
                viewModel.addEntrant(entrant)
        }
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = TheFormFragment()
    }
}