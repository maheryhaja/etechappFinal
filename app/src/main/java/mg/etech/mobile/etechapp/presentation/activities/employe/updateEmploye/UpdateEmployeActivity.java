package mg.etech.mobile.etechapp.presentation.activities.employe.updateEmploye;

import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.update.UpdateEmployeFragment;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSAImpl;

@EActivity(R.layout.activity_update_employe)
public class UpdateEmployeActivity extends AppCompatActivity {

    @FragmentById(R.id.fragmentUpdateEmploye)
    UpdateEmployeFragment updateEmployeFragment;

    @Extra("itemId")
    int itemId;

    @Bean(CentralEmployeSynchroSAImpl.class)
    CentralEmployeSynchroSA centralEmployeSynchroSA;

    @AfterViews
    void initAfterViews() {
        EmployeDto employeDto = centralEmployeSynchroSA.findByitemId(itemId);
        updateEmployeFragment.initEmployeDto(employeDto);
    }

}
