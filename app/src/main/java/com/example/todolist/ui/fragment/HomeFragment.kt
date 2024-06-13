package com.example.todolist.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.todolist.R
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.model.TaskModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
//    private lateinit var adapter: TaskListAdapter
    private val db = Firebase.firestore
    private val taskCollection = db.collection("tasks")
    private val taskList = mutableListOf<TaskModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabAddTask.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTaskFragment())
        }
//        onRecyclerViewTodo()

        binding.rvDone.layoutManager = LinearLayoutManager(requireActivity())
    }

//    private fun onRecyclerViewTodo() {
//        binding.rvDone.layoutManager = LinearLayoutManager(requireContext())
//        val query: Query = db.collection("tasks").whereEqualTo("status", "Done")
//        query.get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    val task = document.toObject(TaskModel::class.java)
//                    taskList.add(task)
//                }
//                adapter = TaskListAdapter(taskList)
//                binding.rvDone.adapter = adapter
//            }
//    }

//    private onRecyclerViewProgress(){}
//    private onRecyclerViewDone(){}


}