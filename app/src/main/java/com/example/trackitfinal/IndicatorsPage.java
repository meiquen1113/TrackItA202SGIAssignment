package com.example.trackitfinal;
// Import the required libraries
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class IndicatorsPage extends AppCompatActivity {
    // Create the object of TextView
    // and PieChart class
    String[] items = new String[]{"Income", "Dining", "Entertainment", "Transport", "Shopping", "Grocery", "Others"};
    TextView tvPieSlice1A, tvPieSlice2A, tvPieSlice3A, tvPieSlice4A, tvPieSlice5A, tvPieSlice6A;
    TextView tvPieSlice1P, tvPieSlice2P, tvPieSlice3P, tvPieSlice4P, tvPieSlice5P, tvPieSlice6P;
    TextView incometxt, expensetxt, cashflowtxt, logUsername, logFullname;
    PieChart pieChart;
    private DatabaseExpenses dbExpenses;
    private DatabaseLogin dbLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_page);

        // Link those objects with their respective id's in .XML file
        incometxt = findViewById(R.id.tvIncome);
        expensetxt = findViewById(R.id.tvExpenses);
        cashflowtxt = findViewById(R.id.tvCashflow);
        logUsername = findViewById(R.id.idTVlogUser);
        logFullname = findViewById(R.id.idTVLogUserName);
        // Expenses Amount
        tvPieSlice1A = findViewById(R.id.tvDiningAmt);
        tvPieSlice2A = findViewById(R.id.tvEntertainmentAmt);
        tvPieSlice3A = findViewById(R.id.tvTransportAmt);
        tvPieSlice4A = findViewById(R.id.tvShoppingAmt);
        tvPieSlice5A = findViewById(R.id.tvGroceryAmt);
        tvPieSlice6A = findViewById(R.id.tvOthersAmt);
        // Percentage data
        tvPieSlice1P = findViewById(R.id.tvDiningPct);
        tvPieSlice2P = findViewById(R.id.tvEntertainmentPct);
        tvPieSlice3P = findViewById(R.id.tvTransportPct);
        tvPieSlice4P = findViewById(R.id.tvShoppingPct);
        tvPieSlice5P = findViewById(R.id.tvGroceryPct);
        tvPieSlice6P = findViewById(R.id.tvOthersPct);
        // link the piechart id
        pieChart = findViewById(R.id.piechart);
        // Creating a method setData()
        // to set the text in text view and pie chart.
        setData();
    }

    // set the text in text view and pie chart
    private void setData()
    {
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String logInUsername = bundle.getString("logUser1");
        // display to the textview
        logUsername.setText(logInUsername);
        // retrieve the fullname data from sql dbase and display it.
        dbLogin = new DatabaseLogin(this);
        logFullname.setText(dbLogin.getFullname(logInUsername));

        dbExpenses = new DatabaseExpenses(this);
        //display the dashboard information
        float income = dbExpenses.sumOfIncome(logInUsername);
        float expense = dbExpenses.sumOfExpenses(logInUsername);
        float cashflow = income - expense;
        //format to 2 decimal place
        String formattedIncome = String.format("RM %.2f", income);
        String formattedExpense = String.format("RM %.2f", expense);
        String formattedCashflow = String.format("RM %.2f", cashflow);
        incometxt.setText(formattedIncome);
        expensetxt.setText(formattedExpense);
        cashflowtxt.setText(formattedCashflow);

        // retrieve each category data from sql dbase.
        float cat1val = dbExpenses.sumOfCategory(items[1],logInUsername);
        float cat2val = dbExpenses.sumOfCategory(items[2],logInUsername);
        float cat3val = dbExpenses.sumOfCategory(items[3],logInUsername);
        float cat4val = dbExpenses.sumOfCategory(items[4],logInUsername);
        float cat5val = dbExpenses.sumOfCategory(items[5],logInUsername);
        float cat6val = dbExpenses.sumOfCategory(items[6],logInUsername);
        float totalExp = cat1val + cat2val + cat3val + cat4val + cat5val + cat6val;
        // format the amount to 2 decimal place
        String formattedStr1A = String.format("RM %.2f", cat1val);
        String formattedStr2A = String.format("RM %.2f", cat2val);
        String formattedStr3A = String.format("RM %.2f", cat3val);
        String formattedStr4A = String.format("RM %.2f", cat4val);
        String formattedStr5A = String.format("RM %.2f", cat5val);
        String formattedStr6A = String.format("RM %.2f", cat6val);
        // calculate the percentage data & format to integer
        String formattedStr1 = String.format("%.0f", cat1val/totalExp*100);
        String formattedStr2 = String.format("%.0f", cat2val/totalExp*100);
        String formattedStr3 = String.format("%.0f", cat3val/totalExp*100);
        String formattedStr4 = String.format("%.0f", cat4val/totalExp*100);
        String formattedStr5 = String.format("%.0f", cat5val/totalExp*100);
        String formattedStr6 = String.format("%.0f", cat6val/totalExp*100);

        //Display the data on the activity
        tvPieSlice1A.setText(formattedStr1A);
        tvPieSlice1P.setText(formattedStr1);
        tvPieSlice2A.setText(formattedStr2A);
        tvPieSlice2P.setText(formattedStr2);
        tvPieSlice3A.setText(formattedStr3A);
        tvPieSlice3P.setText(formattedStr3);
        tvPieSlice4A.setText(formattedStr4A);
        tvPieSlice4P.setText(formattedStr4);
        tvPieSlice5A.setText(formattedStr5A);
        tvPieSlice5P.setText(formattedStr5);
        tvPieSlice6A.setText(formattedStr6A);
        tvPieSlice6P.setText(formattedStr6);

        // Set the data and color to the pie chart
        pieChart.addPieSlice(
                new PieModel(
                        "Dining",
                        Integer.parseInt(tvPieSlice1P.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Entertainment",
                        Integer.parseInt(tvPieSlice2P.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Transport",
                        Integer.parseInt(tvPieSlice3P.getText().toString()),
                        Color.parseColor("#EF5350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Shopping",
                        Integer.parseInt(tvPieSlice4P.getText().toString()),
                        Color.parseColor("#29B6F6")));
        pieChart.addPieSlice(
                new PieModel(
                        "Grocery",
                        Integer.parseInt(tvPieSlice5P.getText().toString()),
                        Color.parseColor("#F9B6e6")));
        pieChart.addPieSlice(
                new PieModel(
                        "Others",
                        Integer.parseInt(tvPieSlice6P.getText().toString()),
                        Color.parseColor("#29F616")));

        // To animate the pie chart
        pieChart.startAnimation();
    }

    public void gotoDashboard5 (View view) {
        Intent intent = new Intent(IndicatorsPage.this, ExpenseDashboard.class);
        startActivity(intent);
    }
}