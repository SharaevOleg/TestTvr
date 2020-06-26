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
import com.tvrtest.tverskoi2.adapter.ProfessionAdapter;

import java.util.ArrayList;

public class ProfessionFragment extends Fragment {

    private ArrayList<Profession> professions = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProfessionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profession, container, false);

        DatabaseManager databaseManager = new DatabaseManager(getContext());
        professions = databaseManager.getAllSpecialties();

        adapter = new ProfessionAdapter(getContext(), professions);
        recyclerView = view.findViewById(R.id.specializationsRecyclerView);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
