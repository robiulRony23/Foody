package com.example.foody.utils

class Constants {
    companion object {
        const val API_KEY = "1231d703d8eb420d9e729d54a2813f45"
        const val BASE_URL = "https://api.spoonacular.com"

        //API query keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"


        //ROOM datavase
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
    }
}