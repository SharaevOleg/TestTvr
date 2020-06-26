package com.tvrtest.tverskoi2.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.tvrtest.tverskoi2.R;
import com.tvrtest.tverskoi2.adapter.DataLoader;
import com.tvrtest.tverskoi2.model.Profession;
import com.tvrtest.tverskoi2.model.ProfessionFragment;
import com.tvrtest.tverskoi2.model.EmployeeListFragment;
import com.tvrtest.tverskoi2.api.OnProfessionFragmentListener;

public class MainActivity extends AppCompatActivity implements OnProfessionFragmentListener {

    private ProfessionFragment professionFragment;
    private FragmentManager fragmentManager;
    private EmployeeListFragment employeeListFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        actionBar = getSupportActionBar();

        if (savedInstanceState == null) {
            DataLoader dataLoader = new DataLoader(this);
            dataLoader.execute();
            try {
                dataLoader.get();
                professionFragment = new ProfessionFragment();
                actionBar.setTitle(R.string.title_specialties);
                fragmentManager.beginTransaction().add(R.id.fragmentContainer, professionFragment).commit();
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    @Override
    public void openEmployeeListFragment(Profession profession) {
        employeeListFragment = new EmployeeListFragment(profession.getName());
        switch (profession.getId()) {
            case 101:
                actionBar.setTitle(R.string.title_managers);
                break;
            case 102:
                actionBar.setTitle(R.string.title_developers);
                break;
            default:
                actionBar.setTitle(R.string.title_employee);
                break;
        }
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, employeeListFragment).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int check = fragmentManager.getBackStackEntryCount();
        switch (check) {
            case 0:
                actionBar.setTitle(R.string.title_specialties);
                break;
            case 1:
                actionBar.show();
                break;
        }
    }
}
