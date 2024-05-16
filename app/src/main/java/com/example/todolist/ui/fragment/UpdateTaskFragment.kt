package com.example.todolist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTaskBinding
import com.example.todolist.databinding.FragmentUpdateTaskBinding


class UpdateTaskFragment : Fragment() {

    private var _binding: FragmentUpdateTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(
            findNavController(),
            AppBarConfiguration(findNavController().graph)
        )
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}