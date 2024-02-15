package com.omarInc.mymeal.profile.presenter;

public interface ProfilePresenter {
    void backupMealsToFirebase();

    void backupScheduleMeals();

    void clearDB();

    void FetchEmail();
}
