package com.example.foody.models


import com.google.gson.annotations.SerializedName

data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)



//https://api.spoonacular.com/recipes/complexSearch?number=1&apiKey=1231d703d8eb420d9e729d54a2813f45&type=snack&diet=vegan&addRecipeInformation=true&fillIngredients=true