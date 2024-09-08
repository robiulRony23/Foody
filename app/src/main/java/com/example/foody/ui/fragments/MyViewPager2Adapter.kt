package com.example.foody.ui.fragments

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.foody.ui.fragments.favorites.FavoriteFragment
import com.example.foody.ui.fragments.foodjoke.FoodJokesFragment
import com.example.foody.ui.fragments.recipes.RecipesFragment

class MyViewPager2Adapter(fa: FragmentActivity, private val fragmentList: Array<Fragment>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int {
        return fragmentList.size;
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("rony", "rony -> pagerAdapter: position: $position")
        return fragmentList[position]
    }
}