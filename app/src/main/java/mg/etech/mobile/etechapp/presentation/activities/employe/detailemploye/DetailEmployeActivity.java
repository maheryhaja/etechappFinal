package mg.etech.mobile.etechapp.presentation.activities.employe.detailemploye;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.presentation.fragments.employe.detail.DetailEmployeFragment;
import mg.etech.mobile.etechapp.presentation.fragments.employe.detail.DetailEmployeFragment_;

@EActivity(R.layout.activity_detail_employe)
public class DetailEmployeActivity extends AppCompatActivity {

    @Extra("employeId")
    Long employeId;

    @Extra("itemId")
    int itemId;
    private DetailEmployeFragment detailEmployeFragment;

    @AfterViews
    void initAfterView() {

        Log.d("mahery-haja", "received employe Id " + employeId);

        detailEmployeFragment = DetailEmployeFragment_
                .builder()
                .employeId(employeId)
                .itemId(itemId)
                .build();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentDetailEmploye, detailEmployeFragment)
                .commit()
        ;
    }

    @Override
    public void onBackPressed() {
        if (detailEmployeFragment.processBack()) {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }
}
