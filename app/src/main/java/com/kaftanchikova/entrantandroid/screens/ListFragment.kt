package com.kaftanchikova.entrantandroid.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaftanchikova.entrantandroid.App
import com.kaftanchikova.entrantandroid.databinding.FragmentListBinding
import com.kaftanchikova.entrantandroid.model.Entrant
import com.kaftanchikova.entrantandroid.repository.EntrantsListener
import com.kaftanchikova.entrantandroid.repository.Repository
import com.kaftanchikova.entrantandroid.view.ListViewModel
import com.kaftanchikova.entrantandroid.view.factory
import com.kaftanchikova.entrantandroid.view.navigator

class ListFragment : Fragment() {

    lateinit var binding: FragmentListBinding
    lateinit var adapter: EntrantAdapter
    private val viewModel: ListViewModel by viewModels{ factory() }
    private val repository: Repository
        get() = (activity?.applicationContext as App).repository

    private val entrantListener: EntrantsListener = {
        adapter.entrants = it
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        viewModel.getEntrants()
        adapter = EntrantAdapter(object : EntrantActionListener {
            override fun onEntrantDelete(entrant: Entrant) {
                viewModel.deleteEntrant(entrant)
            }
            override fun onEntrantDetails(entrant: Entrant) {
                navigator().showInfo(entrant)
            }
            override fun onEntrantEnroll(entrant: Entrant) {
                viewModel.enrollEntrant(entrant)
            }
        })

        viewModel.entrants.observe(viewLifecycleOwner, Observer{
            adapter.entrants = it
        })


        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter


        val itemAnimator = binding.recyclerView.itemAnimator
        if (itemAnimator is DefaultItemAnimator){
            itemAnimator.supportsChangeAnimations = false
        }
        repository.addListener(entrantListener)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.removeListener(entrantListener)
    }

    companion object {
        fun newInstance() = ListFragment()
    }


}