package com.tvrtest.tverskoi2.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tvrtest.tverskoi2.R;
import com.tvrtest.tverskoi2.adapter.DatabaseManager;
import com.tvrtest.tverskoi2.adapter.EmployeeAdapter;

import java.util.ArrayList;

public class EmployeeListFragment extends Fragment {

    private ArrayList<Employee> employees = new ArrayList<>();
    private String specialty;
    private RecyclerView recyclerView;
    private EmployeeAdapter adapter;

    public EmployeeListFragment(String specialtyName) {
        this.specialty = specialtyName;
        this.setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employees_list, container, false);

        DatabaseManager databaseManager = new DatabaseManager(getContext());
        employees = databaseManager.getEmployeeBySpecialty(specialty);

        adapter = new EmployeeAdapter(getContext(), employees);
        recyclerView = view.findViewById(R.id.workersRecyclerView);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
}
