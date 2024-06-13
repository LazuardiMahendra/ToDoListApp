package com.example.todolist.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddTaskBinding
import com.example.todolist.model.TaskModel
import com.example.todolist.viewmodel.TaskViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class AddTaskFragment : Fragment() {
    private var _binding: FragmentAddTaskBinding? = null
    private val binding get() = _binding!!
    private val storage = Firebase.storage
    private val storageRef = storage.reference
    private val firestore = Firebase.firestore

    private var dateTime: String? = null
    private val itemsStatus = listOf("OnProgress", "ToDo", "Done")
    private val viewModel: TaskViewModel by viewModels()

    private val PICK_FILE_REQUEST = 1
    private val MAX_FILE_SIZE_MB = 5 * 1024 * 1024  // Max 5 MB
    private var fileUri: Uri? = null
    private var fileName: String = "default"
    private var uriUploaded: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddTaskBinding.inflate(inflater, container, false)
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


        //dropdown
        val adapterStatus = ArrayAdapter(requireContext(), R.layout.item_list_dropdown, itemsStatus)
        (binding.tilStatus.editText as? AutoCompleteTextView)?.setAdapter(adapterStatus)

        binding.npReminder.setMinValue(0);
        binding.npReminder.setMaxValue(90);

        binding.etDate.setOnClickListener {
            showDatePicker()
        }

        binding.tvFile.setOnClickListener {
            openFileChooser()
        }

        binding.btnUpload.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            fileUri?.let {
                checkFileSizeAndUpload(it)
            } ?: Toast.makeText(requireContext(), "No file selected", Toast.LENGTH_SHORT).show()
        }


        binding.btnSave.setOnClickListener {
            binding.progress.visibility = View.VISIBLE
            val title = binding.etTitle.text.toString()
            val desc = binding.etDesc.text.toString()
            val date = binding.etDate.text.toString()
            val reminder = binding.npReminder.value.toString()
            val status = binding.tilStatus.editText?.text.toString()
            val file = binding.tvFile.text.toString()
            Log.d("TES", "FILENAMEVALUE: $file")
            val taskModel = TaskModel(title, desc, date, reminder, status, file)
            firestore.collection("tasks")
                .add(taskModel)
                .addOnSuccessListener { documentReference ->
                    binding.progress.visibility = View.GONE
                    Toast.makeText(context, "Task added successfully", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to add task $e", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun showDatePicker() {
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selection))
            binding.etDate.setText(selectedDate)
            dateTime = selectedDate
        }
        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                fileUri = it
                val cursor = requireContext().contentResolver.query(it, null, null, null, null)
                cursor?.use { c ->
                    if (c.moveToFirst()) {
                        val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                        val fileName = c.getString(nameIndex)
                        binding.tvFile.text = fileName
                    }
                }
            }
        }

    private fun openFileChooser() {
        getContent.launch("*/*")
    }


    private fun checkFileSizeAndUpload(uri: Uri) {
        val context = requireContext()
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val sizeIndex = it.getColumnIndex(OpenableColumns.SIZE)
                val size = it.getLong(sizeIndex)
                if (size <= MAX_FILE_SIZE_MB) {
                    uploadFile(uri)
                } else {
                    Toast.makeText(
                        context,
                        "File is too large. Maximum size is 5MB.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progress.visibility = View.GONE

                }
            }
        }
    }

    private fun uploadFile(uri: Uri) {
        val storageRef: StorageReference = storage.reference.child("uploads/${System.currentTimeMillis()}")
        storageRef.putFile(uri).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                    val url = downloadUri.toString()
                    uriUploaded = url
                    binding.tvFile.text = uriUploaded
                    binding.progress.visibility = View.GONE
                    Toast.makeText(context, "File successfully upload", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    context,
                    "Upload failed: " + task.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
                binding.progress.visibility = View.GONE
            }
        }
    }

}
