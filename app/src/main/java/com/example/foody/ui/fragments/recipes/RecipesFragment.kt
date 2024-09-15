package com.example.foody.ui.fragments.recipes

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foody.viewmodel.MainViewModel
import com.example.foody.R
import com.example.foody.adaptars.RecipesAdapter
import com.example.foody.data.network.NetworkResult
import com.example.foody.databinding.FragmentRecipesBinding
import com.example.foody.utils.observeOnce
import com.example.foody.utils.observeOnceAfterInitial
import com.example.foody.utils.observeTrueOnce
import com.example.foody.viewmodel.ConnectivityViewModel
import com.example.foody.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private val TAG = "RecipesFragment"
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private lateinit var binding: FragmentRecipesBinding
    private val recipesAdapter by lazy { RecipesAdapter() }
    private val connectivityViewModel: ConnectivityViewModel by viewModels()
    private var isFirstCall: Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() is called!!!")
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView() is called!!!")

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setUpRecyclerView()
        readDatabase()
        isFirstCall = true

        connectivityViewModel.isOnline.observeTrueOnce(viewLifecycleOwner) { isOnline ->
            Log.d(TAG, "internet connection is changed. isOnline: $isOnline")
            if (isOnline && !isFirstCall) {
                if (recipesAdapter.itemCount == 0) {
                    Log.d(TAG, "internet connection is changed. readDatabase is called...")
                    readDatabase()
                }
            }
            isFirstCall = false
        }

        binding.fabRecipes.setOnClickListener {
            val recipesBottomSheet = RecipesBottomSheet()
            recipesBottomSheet.show(parentFragmentManager, recipesBottomSheet.tag)

            binding.fabRecipes.isEnabled = false

            Handler(Looper.getMainLooper())
                .postDelayed({
                    binding.fabRecipes.isEnabled = true
                }, 500)
        }

        recipesViewModel.readMealAndDietType.asLiveData().observeOnceAfterInitial(viewLifecycleOwner) { value ->
            Log.d(TAG, "bottom sheet data is changed!")
            requestApiData()
        }

        return binding.root
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d(TAG, "readDatabase is called!!!")
                    if (!recipesAdapter.recipes.equals(database[0].foodRecipe)) {
                        Log.d(TAG, "adapter data is reloaded!!!")
                        recipesAdapter.setData(database[0].foodRecipe)
                    }
                    hideShimmerEffect()
                } else {
                    Log.d(TAG, "readDatabase() -> request api data is called!!!")
                    requestApiData()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    recipesAdapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d(TAG, "requestApiData is called!!!")
        mainViewModel.getRecipes(recipesViewModel.getApplyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { recipesAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
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
}