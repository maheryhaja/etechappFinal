package mg.etech.mobile.etechapp.presentation.activities.employe.updateEmploye;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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
    protected UpdateEmployeFragment updateEmployeFragment;

    @Extra("itemId")
    int itemId;

    @Bean(CentralEmployeSynchroSAImpl.class)
    protected CentralEmployeSynchroSA centralEmployeSynchroSA;

    @AfterViews
    protected void initAfterViews() {
        EmployeDto employeDto = centralEmployeSynchroSA.findByitemId(itemId);
        updateEmployeFragment.initEmployeDto(employeDto);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager()
                .beginTransaction()
                .detach(updateEmployeFragment)
                .remove(updateEmployeFragment)
                .commit();
        updateEmployeFragment = null;
        finish();
    }
}
