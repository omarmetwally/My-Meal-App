package com.omarInc.mymeal.recommendedMeals.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

public class ViewPagerStack implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        final float scaleFactor = 0.85f;
        final float elevationFactor = 8f;

        if (position < 0) {
            page.setScaleX(scaleFactor + (1 - scaleFactor) * (1 + position));
            page.setScaleY(scaleFactor + (1 - scaleFactor) * (1 + position));
            page.setTranslationX(-page.getWidth() * position);
            page.setTranslationZ(elevationFactor * position);
        } else {
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
            page.setTranslationX(-page.getWidth() * position);
            page.setTranslationZ(-elevationFactor * position);
        }
    }
}