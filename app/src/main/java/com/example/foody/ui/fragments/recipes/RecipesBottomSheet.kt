package com.example.foody.ui.fragments.recipes

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.foody.R
import com.example.foody.databinding.RecipesBottomSheetBinding
import com.example.foody.utils.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.foody.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.foody.viewmodel.RecipesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class RecipesBottomSheet : BottomSheetDialogFragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    private lateinit var binding: RecipesBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity())[RecipesViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipes_bottom_sheet, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup, binding.mealTypeScrollView)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup, binding.dietTypeScrollView)
        }

        binding.mealTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds.first())
            val selectedMealType = chip.text.toString().lowercase()
            mealTypeChip = selectedMealType
            mealTypeChipId = checkedIds.first()
        }

        binding.dietTypeChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            val chip = group.findViewById<Chip>(checkedIds.first())
            val selectedDietType = chip.text.toString().lowercase()
            dietTypeChip = selectedDietType
            dietTypeChipId = checkedIds.first()
        }

        binding.applyBtn.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietTypeChip,
                dietTypeChipId
            )

            // Dismiss the bottom sheet
            dismiss()
        }

        return binding.root
    }

    private fun updateChip(
        chipId: Int,
        chipGroup: ChipGroup,
        horizontalScrollView: HorizontalScrollView
    ) {
        if (chipId != 0) {
            try {
                val chip = chipGroup.findViewById<Chip>(chipId)
                chip.isChecked = true
                scrollToChip(chipGroup, chip, horizontalScrollView)
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    private fun scrollToChip(
        chipGroup: ChipGroup,
        selectedChip: Chip,
        horizontalScrollView: HorizontalScrollView
    ) {
        selectedChip.post{
            val chipRect = Rect()
            selectedChip.getGlobalVisibleRect(chipRect)

            val chipGroupRect = Rect()
            chipGroup.getGlobalVisibleRect(chipGroupRect)

            val distanceToScroll = chipRect.left - chipGroupRect.left
            horizontalScrollView.scrollTo(distanceToScroll, 0)
        }
    }
}