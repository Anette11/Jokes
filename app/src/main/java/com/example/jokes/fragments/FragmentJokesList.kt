package com.example.jokes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokes.R
import com.example.jokes.adapters.JokeAdapter
import com.example.jokes.databinding.FragmentJokesListBinding
import com.example.jokes.ui.JokeFactory
import com.example.jokes.ui.JokeViewModel

class FragmentJokesList : Fragment() {
    private var _binding: FragmentJokesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var jokeViewModel: JokeViewModel
    private lateinit var jokeAdapter: JokeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJokesListBinding
            .inflate(inflater, container, false)
        val view = binding.root
        setViewModel()
        changeActionBarName()
        changeProgressBarVisibility()
        updateRecyclerViewList()
        setRecyclerView()
        setOnClickListenerButtonReload()
        return view
    }

    private fun setViewModel() {
        jokeViewModel = ViewModelProvider(requireActivity(), JokeFactory())
            .get(JokeViewModel::class.java)
    }

    private fun changeActionBarName() {
        jokeViewModel.actionBarName.postValue(getString(R.string.app_name))
    }

    private fun changeProgressBarVisibility() {
        jokeViewModel.progressBarJokesListVisibility.observe(viewLifecycleOwner, {
            if (it != null) when (it) {
                1 -> {
                    _binding?.progressBar?.visibility = View.VISIBLE
                }
                else -> {
                    _binding?.progressBar?.visibility = View.GONE
                }
            }
        })
    }

    private fun setOnClickListenerButtonReload() {
        _binding?.button?.setOnClickListener {
            if (_binding?.editText?.text?.isEmpty() == true) {
                showToastMessage(getString(R.string.count_field_is_empty))
            } else {
                when (val number = _binding?.editText?.text.toString().toInt()) {
                    0 -> {
                        showToastMessage(getString(R.string.number_should_be_not_zero))
                    }
                    else -> {
                        jokeViewModel.getValue(number)
                    }
                }
            }
        }
    }

    private fun updateRecyclerViewList() {
        jokeViewModel.value.observe(viewLifecycleOwner, { valueList ->
            if (valueList != null) {
                jokeAdapter.updateList(valueList)
            }
        })
    }

    private fun setRecyclerView() {
        with(binding.recyclerView) {
            val linearLayoutManager = LinearLayoutManager(activity)
            layoutManager = linearLayoutManager
            jokeAdapter = JokeAdapter()
            adapter = jokeAdapter
            this.adapter!!.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}