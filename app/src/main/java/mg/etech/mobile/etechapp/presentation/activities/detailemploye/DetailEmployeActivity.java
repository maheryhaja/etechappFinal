package mg.etech.mobile.etechapp.presentation.activities.detailemploye;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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


    @AfterViews
    void initAfterView() {

        Log.d("mahery-haja", "received employe Id " + employeId);

        DetailEmployeFragment detailEmployeFragment = DetailEmployeFragment_
                .builder()
                .employeId(employeId)
                .build();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentDetailEmploye, detailEmployeFragment)
                .commit()
        ;
    }
}
