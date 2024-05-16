package com.example.todolist.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.example.todolist.R
import com.example.todolist.databinding.FragmentDetailTaskBinding
import com.example.todolist.databinding.FragmentLoginBinding
import com.example.todolist.databinding.FragmentProfileBinding
import com.example.todolist.ui.activity.AuthActivity
import com.example.todolist.ui.activity.HomeActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogout.setOnClickListener {
            deleteDialog()

        }

    }

    private fun deleteDialog() {
        MaterialAlertDialogBuilder(requireContext()).setTitle(resources.getString(R.string.text_logout))
            .setMessage(resources.getString(R.string.text_ask_logout))
            .setNeutralButton(resources.getString(R.string.text_cancel)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.text_logout)) { dialog, which ->
                logout()
            }
            .show()
    }

    private fun logout() {
        Firebase.auth.signOut()
        val intent = Intent(requireContext(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }

}