package com.omarInc.mymeal.Home.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.omarInc.mymeal.model.Meal;

import java.util.List;

public class SectionPagerAdapter extends FragmentStatePagerAdapter {
    private List<Meal> meals;

    public SectionPagerAdapter(@NonNull FragmentManager fm, List<Meal> meals) {
        super(fm);
        this.meals = meals;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Meal meal = meals.get(position);
        return MealDetailFragment.newInstance(meal.getStrMealThumb(), meal.getStrMeal());
    }

    @Override
    public int getCount() {
        return meals.size();
    }
}
