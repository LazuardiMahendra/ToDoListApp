package com.example.todolist.ui.fragment

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
import com.example.todolist.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth

        binding.etEmail.addTextChangedListener(watcher)
        binding.etPassword.addTextChangedListener(watcher)
        binding.etCPassword.addTextChangedListener(watcher)

        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val cPassword = binding.etCPassword.text.toString()
            if (!isRegisterFieldCorrect(email, password, cPassword)) {
                binding.progress.visibility = View.GONE
            } else {
                signUp(email, password)
            }
        }

    }

    private fun signUp(email: String, password: String) {
        auth = Firebase.auth
        val curentUser = auth.currentUser
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Registration successful.", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                } else {
                    Toast.makeText(
                        context,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
    }

    //text change watcher
    private val watcher: TextWatcher = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val cPassword = binding.etCPassword.text.toString()

            if (email.isNotBlank()) binding.etEmail.error = null
            if (password.isNotBlank()) binding.etPassword.error = null
            if (cPassword.isNotBlank()) binding.etCPassword.error = null
        }

        override fun afterTextChanged(s: Editable) {}
    }

    //check is field empty
    private fun isRegisterFieldCorrect(
        email: String,
        pass: String,
        confirmPass: String
    ): Boolean {
        val empty = resources.getString(R.string.text_error_empty_field)
        val invalid = resources.getString(R.string.text_error_invalid_email)
        val notMatch = resources.getString(R.string.text_error_pass_not_match)
        val below = resources.getString(R.string.text_error_short_pass)
        val regexMatch = resources.getString(R.string.text_error_pass_regex_not_match)

        return when {
            email.isBlank() -> {
                binding.tilEmail.error = empty
                false
            }

            !Pattern.compile(pass).matcher(pass).matches() -> {
                binding.tilPassword.error = regexMatch
                false
            }

            pass.isBlank() -> {
                binding.tilPassword.error = empty
                false
            }

            pass.length < 8 -> {
                binding.tilPassword.error = below
                false
            }

            confirmPass != pass -> {
                binding.tilConfirmPassword.error = notMatch
                false
            }

            else -> {
                binding.tilEmail.error = null
                binding.tilPassword.error = null
                binding.tilConfirmPassword.error = null
                binding.tilEmail.isErrorEnabled
                binding.tilPassword.isErrorEnabled
                binding.tilConfirmPassword.isErrorEnabled

                true
            }
        }
    }


}