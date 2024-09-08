package com.example.foody.ui.fragments.recipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.viewmodel.MainViewModel
import com.example.foody.R
import com.example.foody.adaptars.RecipesAdapter
import com.example.foody.data.network.NetworkResult
import com.example.foody.databinding.FragmentRecipesBinding
import com.example.foody.utils.Constants.Companion.API_KEY
import com.example.foody.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var binding: FragmentRecipesBinding
    private val recipesAdapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false)
        setUpRecyclerView()
        requestApiData()
        return binding.root
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModel.getApplyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { recipesAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        }
    }

    private fun setUpRecyclerView() {
        binding.srvRecipe.adapter = recipesAdapter
        binding.srvRecipe.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun hideShimmerEffect() {
        binding.srvRecipe.hideShimmerAdapter()
    }

    private fun showShimmerEffect() {
        binding.srvRecipe.showShimmerAdapter()
    }

//    override fun onPause() {
//        super.onPause()
//        Log.d("rony", "recipes fragment is onPause....")
//        binding.srvRecipe.hideShimmerAdapter()
//    }
//    override fun onResume() {
//        super.onResume()
//        Log.d("rony", "recipes fragment is onResume....")
//        showShimmerEffect()
//    }
}