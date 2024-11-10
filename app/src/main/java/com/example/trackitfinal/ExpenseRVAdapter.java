package com.example.trackitfinal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseRVAdapter extends RecyclerView.Adapter<ExpenseRVAdapter.ViewHolder> {

    // variable for our array list and context
    private ArrayList<ExpenseModal> expenseModalArrayList;
    private Context context;

    // constructor
    public ExpenseRVAdapter(ArrayList<ExpenseModal> expenseModalArrayList, Context context) {
        this.expenseModalArrayList = expenseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        ExpenseModal modal = expenseModalArrayList.get(position);
        holder.expenseDateTV.setText(modal.getExpenseDate());
        holder.expenseCategoryTV.setText(modal.getExpenseCategory());
        holder.expenseDescriptionTV.setText(modal.getExpenseDescription());
        holder.expenseAmountTV.setText(modal.getExpenseAmount());
        holder.loguserTV.setText(modal.getLoguser());
        holder.oidTV.setText(modal.getId());

        // below line is to add on click listener for our recycler view item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, UpdateExpenses.class);

                // below we are passing all our values.
                i.putExtra("date", modal.getExpenseDate());
                i.putExtra("category", modal.getExpenseCategory());
                i.putExtra("description", modal.getExpenseDescription());
                i.putExtra("amount", modal.getExpenseAmount());
                i.putExtra("loguser", modal.getLoguser());
                i.putExtra("id", modal.getId());

                // starting our activity.
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return expenseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView expenseDateTV, expenseDescriptionTV, expenseCategoryTV, expenseAmountTV, loguserTV, oidTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            expenseDateTV = itemView.findViewById(R.id.idTVExpenseDate);
            expenseCategoryTV = itemView.findViewById(R.id.idTVExpenseCategory);
            expenseDescriptionTV = itemView.findViewById(R.id.idTVExpenseDescription);
            expenseAmountTV = itemView.findViewById(R.id.idTVExpenseAmount);
            loguserTV = itemView.findViewById(R.id.idTVloguser);
            oidTV = itemView.findViewById((R.id.idTVoid));
        }
    }
}