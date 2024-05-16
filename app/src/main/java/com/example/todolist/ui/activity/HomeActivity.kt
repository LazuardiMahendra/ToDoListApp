package com.example.todolist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todolist.R
import com.example.todolist.databinding.ActivityHomeBinding
import com.example.todolist.ui.fragment.LoginFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerHome)
        binding.bottomNavigation.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment
                || destination.id == R.id.taskFragment
                || destination.id == R.id.galleryFragment
                || destination.id == R.id.profileFragment
            ) {

                binding.bottomNavigation.visibility = View.VISIBLE
            } else {

                binding.bottomNavigation.visibility = View.GONE
            }
        }


    }

}