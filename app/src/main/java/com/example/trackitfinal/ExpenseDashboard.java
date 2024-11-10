package com.example.trackitfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseDashboard extends AppCompatActivity {

    private TextView welcome, incometxt, expensetxt, cashflowtxt;
    //Bundle bundle = new Bundle();
    private DatabaseExpenses dbExpenses;
    private DatabaseLogin dbLogin;
    private SmartHandlerAI getAImessage;

    // private Bundle bundle = new Bundle();

    // creating variables for our array list, adapter and recycler view.
    private ArrayList<ExpenseModal> expenseModalArrayList;
    private ExpenseRVAdapter expenseRVAdapter;
    private RecyclerView expenseRV;

    // display the data in recycler view.
    public void displayData(String logUsername){
        // initializing our all variables.
        expenseModalArrayList = new ArrayList<>();
        dbExpenses = new DatabaseExpenses(ExpenseDashboard.this);
        // getting our course array
        // list from db handler class.
        expenseModalArrayList = dbExpenses.readExpenses(logUsername);
        // on below line passing our array list to our adapter class.
        expenseRVAdapter = new ExpenseRVAdapter(expenseModalArrayList, ExpenseDashboard.this);
        expenseRV = findViewById(R.id.idRVExpenses1);
        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExpenseDashboard.this, RecyclerView.VERTICAL, false);
        expenseRV.setLayoutManager(linearLayoutManager);
        // setting our adapter to recycler view.
        expenseRV.setAdapter(expenseRVAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expense_dashboard);

        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String logInUsername = bundle.getString("USERNAME");
        // display to the textview
        welcome = findViewById(R.id.TVdashboardMessage);
        dbLogin = new DatabaseLogin(ExpenseDashboard.this);
        getAImessage = new SmartHandlerAI();
        welcome.setText(dbLogin.getFullname(logInUsername));

        displayData(logInUsername);

        //display the dashboard information
        incometxt = findViewById(R.id.TVincome);
        expensetxt = findViewById(R.id.TVexpense);
        cashflowtxt = findViewById(R.id.TVcashflow);
        float income = dbExpenses.sumOfIncome(logInUsername);
        float expense = dbExpenses.sumOfExpenses(logInUsername);
        float cashflow = income - expense;

        String formattedIncome = String.format("$%.2f", income);
        String formattedExpense = String.format("$%.2f", expense);
        String formattedCashflow = String.format("$%.2f", cashflow);
        incometxt.setText(formattedIncome);
        expensetxt.setText(formattedExpense);
        cashflowtxt.setText(formattedCashflow);
        // display the AI message for cashflow.
        String messageAI = getAImessage.getCashFlowMessage(cashflow, income);
        Toast.makeText(ExpenseDashboard.this, messageAI, Toast.LENGTH_LONG).show();
    }

    public void gotoWelcomePage (View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void gotoUpdateExpenses (View view) {
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String fname = bundle.getString("USERNAME");
        Intent intent = new Intent(this, UpdateExpenses.class);
        //Add data to bundle to parse data to next activity.
        bundle.putString("logUser", fname);
        //Add the bundle to the intent
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goToSettingsPage (View view) {
        Intent intent = new Intent(this, SettingsLoginPage.class);
        startActivity(intent);
    }

    public void gotoIndicatorsPage (View view) {
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String fname = bundle.getString("USERNAME");
        Intent intent = new Intent(this, IndicatorsPage.class);
        //Add data to bundle to parse data to next activity.
        bundle.putString("logUser1", fname);
        //Add the bundle to the intent
        intent.putExtras(bundle);
        startActivity(intent);
        startActivity(intent);
    }

}