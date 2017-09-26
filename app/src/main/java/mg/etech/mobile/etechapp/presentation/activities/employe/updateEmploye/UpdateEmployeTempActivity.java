package mg.etech.mobile.etechapp.presentation.activities.employe.updateEmploye;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.donnee.dto.OperationDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.ListEmployeItemTemp;
import mg.etech.mobile.etechapp.presentation.fragments.employe.update.UpdateEmployeTempFragment;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSA;
import mg.etech.mobile.etechapp.service.applicatif.synchro.central.CentralEmployeSynchroSAImpl;

@EActivity(R.layout.activity_update_employe_temp)
public class UpdateEmployeTempActivity extends AppCompatActivity {

    @FragmentById(R.id.fragmentUpdateEmployeTemp)
    UpdateEmployeTempFragment updateEmployeTempFragment;


    @Extra("itemId")
    protected int itemId;

    @Bean(CentralEmployeSynchroSAImpl.class)
    CentralEmployeSynchroSA centralEmployeSynchroSA;

    @AfterViews
    void initAfterViews() {
        EmployeDto employeDto = centralEmployeSynchroSA.findByitemId(itemId);
        updateEmployeTempFragment.initEmployeDto(employeDto);
        updateEmployeTempFragment.setOperationName(centralEmployeSynchroSA.findOperationNameById(itemId));
    }


}
