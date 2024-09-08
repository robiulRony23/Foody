package com.example.foody.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.app.TaskStackBuilder
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.foody.R
import com.example.foody.databinding.ActivityMainBinding
import com.example.foody.ui.fragments.MyViewPager2Adapter
import com.example.foody.ui.fragments.favorites.FavoriteFragment
import com.example.foody.ui.fragments.foodjoke.FoodJokesFragment
import com.example.foody.ui.fragments.recipes.RecipesFragment
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO:: transaction in a fragment
//        supportFragmentManager.beginTransaction().add(R.id.navHostFragment, RecipesFragment::class.java, null).commit()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val fragmentList: Array<Fragment> =
            arrayOf(RecipesFragment(), FavoriteFragment(), FoodJokesFragment());

        binding.pager.adapter = MyViewPager2Adapter(this, fragmentList)

        registerOnPageChangeListener()
        registerOnItemChangeListener()
//        setUpNavHostController()
    }

    private fun registerOnItemChangeListener() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->

            when (item.itemId) {
                R.id.recipesFragment -> {
                    binding.pager.currentItem = 0
//                    supportFragmentManager.beginTransaction().add(R.id.navHostFragment, RecipesFragment::class.java, null).commit()
                }
                R.id.favoriteFragment -> {
                    binding.pager.currentItem = 1
//                    supportFragmentManager.beginTransaction().add(R.id.navHostFragment, FavoriteFragment::class.java, null).commit()
                }
                R.id.foodJokeFragment -> {
                    binding.pager.currentItem = 2
//                    supportFragmentManager.beginTransaction().add(R.id.navHostFragment, FoodJokesFragment::class.java, null).commit()
                }
            }
            true;
        }
    }

    private fun registerOnPageChangeListener() {
        binding.pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.bottomNavigationView.selectedItemId = R.id.recipesFragment
                    1 -> binding.bottomNavigationView.selectedItemId = R.id.favoriteFragment
                    2 -> binding.bottomNavigationView.selectedItemId = R.id.foodJokeFragment
                }
            }
        })
    }

    //TODO:: Navhost controller not used..will remove later

//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }
//
//    private fun setUpNavHostController() {
//        navController = findNavController(R.id.navHostFragment)
//        viewPager = findViewById(R.id.pager)
//
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.recipesFragment,
//                R.id.favoriteFragment,
//                R.id.foodJokeFragment
//            )
//        )
//        binding.bottomNavigationView.setupWithNavController(navController)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//    }
}
