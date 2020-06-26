package com.tvrtest.tverskoi2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tvrtest.tverskoi2.R;
import com.tvrtest.tverskoi2.model.Profession;
import com.tvrtest.tverskoi2.api.OnProfessionFragmentListener;

import java.util.ArrayList;

public class ProfessionAdapter extends RecyclerView.Adapter<ProfessionAdapter.SpecializationsViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Profession> professions;

    private OnProfessionFragmentListener fragmentListener;

    public ProfessionAdapter(Context context, ArrayList<Profession> professions) {
        this.professions = professions;
        this.inflater = LayoutInflater.from(context);
        this.fragmentListener = (OnProfessionFragmentListener) context;
    }

    public class SpecializationsViewHolder extends RecyclerView.ViewHolder{
        final TextView specialtyNameTextView;
        final CardView specialtyCard;

        public SpecializationsViewHolder(@NonNull View itemView) {
            super(itemView);
            specialtyNameTextView = itemView.findViewById(R.id.specialtyNameTextView);
            specialtyCard = itemView.findViewById(R.id.specialtyCard);
        }
    }

    @NonNull
    @Override
    public SpecializationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.professions_item, parent, false);
        return new SpecializationsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpecializationsViewHolder holder, final int position) {
        final Profession profession = professions.get(position);
        switch (profession.getId()) {
            case 101:
                holder.specialtyNameTextView.setText(R.string.title_managers);
                break;
            case 102:
                holder.specialtyNameTextView.setText(R.string.title_developers);
                break;
            default:
                holder.specialtyNameTextView.setText(R.string.title_employee);
                break;
        }
        //Обрабатываем нажатие по карточке со специальностью
        holder.specialtyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.openEmployeeListFragment(profession);
            }
        });
    }

    @Override
    public int getItemCount() {
        return professions.size();
    }
}
