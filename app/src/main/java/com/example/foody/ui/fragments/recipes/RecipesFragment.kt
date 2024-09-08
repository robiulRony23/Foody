package com.example.foody.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.foody.R
import com.example.foody.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {

    private lateinit var binding: FragmentRecipesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false)
        binding.srvRecipe.showShimmerAdapter()

        return binding.root;
    }

    override fun onResume() {
        super.onResume()
        Log.d("rony", "recipes fragment is onResume....")
        binding.srvRecipe.showShimmerAdapter()

    }

    override fun onPause() {
        super.onPause()
        Log.d("rony", "recipes fragment is onPause....")
        binding.srvRecipe.hideShimmerAdapter()

    }
}