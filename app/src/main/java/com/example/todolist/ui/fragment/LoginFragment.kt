package com.example.todolist.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.todolist.R
import com.example.todolist.databinding.FragmentLoginBinding
import com.example.todolist.ui.activity.HomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        binding.tvSignUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.btnSignIn.setOnClickListener {
            val email = binding.tvEmail.text.toString()
            val pass = binding.tvPass.text.toString()
            if (!isLoginFieldCorrect(email, pass)) {
                binding.progress.visibility = View.GONE
            } else {
                signIn(email, pass)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                } else {
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(context, "Login Successfull", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        } else {
            Toast.makeText(context, "Login failed.", Toast.LENGTH_SHORT).show()
        }
    }

    //text change watcher
    private val watcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val email = binding.tvEmail.text.toString()
            val pass = binding.tvPass.text.toString()

            if (email.isNotBlank()) binding.tvEmail.error = null
            if (pass.isNotBlank()) binding.tvPass.error = null
        }

        override fun afterTextChanged(s: Editable) {}
    }

    // handle error in text field
    private fun isLoginFieldCorrect(email: String, pass: String): Boolean {
        val empty = resources.getString(R.string.text_error_empty_field)
        val invalid = resources.getString(R.string.text_error_invalid_email)
        val below = resources.getString(R.string.text_error_short_pass)

        return when {
            email.isBlank() && pass.isBlank() -> {
                binding.tvEmail.error = empty
                binding.tvPass.error = empty
                false
            }

            email.isBlank() -> {
                binding.tvEmail.error = empty
                false
            }

            pass.isBlank() -> {
                binding.tvPass.error = empty
                false
            }

            pass.length < 8 -> {
                binding.tvPass.error = below
                false
            }

            else -> {
                binding.tvEmail.error = null
                binding.tvPass.error = null
                binding.tilEmail.isErrorEnabled
                binding.tilPass.isErrorEnabled
                true
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }
    }

}