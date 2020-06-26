package com.tvrtest.tverskoi2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tvrtest.tverskoi2.R;
import com.tvrtest.tverskoi2.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Employee> employees;

//    private OnWorkersListFragmentListener fragmentListener;

    public EmployeeAdapter(Context context, ArrayList<Employee> employees) {
        this.employees = employees;
        this.inflater = LayoutInflater.from(context);
//        this.fragmentListener = (OnWorkersListFragmentListener) context;
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        final TextView employeeTextView;
        final TextView employeeAgeTextView;
        final CardView employeeCard;
        final ImageView avatar;
        final TextView specialtyTextView;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeTextView = itemView.findViewById(R.id.workerItemNameTextView);
            employeeAgeTextView = itemView.findViewById(R.id.workerItemAgeTextView);
            employeeCard = itemView.findViewById(R.id.workerCard);
            specialtyTextView = itemView.findViewById(R.id.workerSpecialtyTextView);
            avatar = itemView.findViewById(R.id.workerAvatarImageView);
        }
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.employees_item, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        final Employee employee = employees.get(position);
        holder.employeeTextView.setText(employee.getFirstName() + " " + employee.getLastName());
        if (employee.getAge() != null) {
            holder.employeeAgeTextView.setText(String.valueOf(employee.getAge()));
        } else {
            holder.employeeAgeTextView.setText("-");
        }

        holder.specialtyTextView.setText(employee.getProfession().getName());
//ставим картинки
        try {
            Picasso.get().load(employee.getAvatarUrl()).into(holder.avatar);
        } catch (Exception e) {
            holder.avatar.setImageResource(R.drawable.no_photo_img);
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }
}
