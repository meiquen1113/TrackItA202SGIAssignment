package com.example.trackitfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class UpdateExpenses extends AppCompatActivity {

    // creating variables for our edittext, button and dbhandler
    private EditText expenseDateEdt, expenseDescriptionEdt, expenseCategoryEdt, expenseAmountEdt;
    private Button addItemBtn, updateBtn, deleteBtn;
    private DatabaseExpenses dbExpenses;
    private DatabaseLogin dbLogin;

    String[] items = new String[]{"Income", "Dining", "Entertainment", "Transport", "Shopping", "Grocery", "Others"};
    String expenseDate, expenseDescription, expenseCategory, expenseAmount, expenseLoguser, oid;
    CalendarView calendarView;
    Calendar calendar;
    TextView opencal, logUsername, logFullname;

    // creating variables for our array list, adapter and recycler view.
    private ArrayList<ExpenseModal> expenseModalArrayList;
    private ExpenseRVAdapter expenseRVAdapter;
    private RecyclerView expenseRV;


    // display the data in recycler view.
    public void displayExpenseData(String logUsername){
        // initializing our all variables.
        expenseModalArrayList = new ArrayList<>();
        dbExpenses = new DatabaseExpenses(UpdateExpenses.this);
        // getting our expense arraylist from dbExpenses class.
        expenseModalArrayList = dbExpenses.readExpenses(logUsername);
        // on below line passing our array list to our adapter class.
        expenseRVAdapter = new ExpenseRVAdapter(expenseModalArrayList, UpdateExpenses.this);
        expenseRV = findViewById(R.id.idRVExpenses2);
        // setting layout manager for our recycler view.
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UpdateExpenses.this, RecyclerView.VERTICAL, false);
        expenseRV.setLayoutManager(linearLayoutManager);
        // setting our adapter to recycler view.
        expenseRV.setAdapter(expenseRVAdapter);
    }

    // populate the data in the EditText from the selected (click) from the recycler view display list.
    public void updateExpenseEditText(){
        // on below line we are initializing our dbExpenses class.
        dbExpenses = new DatabaseExpenses(UpdateExpenses.this);

        // on below lines we are getting data which we passed in our adapter class.
        expenseDate = getIntent().getStringExtra("date");
        expenseDescription = getIntent().getStringExtra("description");
        expenseCategory = getIntent().getStringExtra("category");
        expenseAmount = getIntent().getStringExtra("amount");
        expenseLoguser = getIntent().getStringExtra("loguser");
        oid = getIntent().getStringExtra("id");

        // setting data to edit text of our update activity.
        expenseDateEdt.setText(expenseDate);
        expenseDescriptionEdt.setText(expenseDescription);
        expenseCategoryEdt.setText(expenseCategory);
        expenseAmountEdt.setText(expenseAmount);
        // if no selection made from the recycler view click, use the login username data.
        if (expenseLoguser != null) {
            logUsername.setText(expenseLoguser);
        }
        // retrieve the fullname data from sql dbase and display it.
        dbLogin = new DatabaseLogin(this);
        logFullname.setText(dbLogin.getFullname(logUsername.getText().toString()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_expenses);

        // initializing all our variables.
        expenseDateEdt = findViewById(R.id.idEdtDate);
        expenseDescriptionEdt = findViewById(R.id.idEdtDescription);
        expenseAmountEdt = findViewById(R.id.idEdtAmount);
        expenseCategoryEdt = findViewById(R.id.idEdtCategory);
        addItemBtn = findViewById(R.id.idBtnAddItem);
        updateBtn = findViewById(R.id.idBtnUpdate);
        deleteBtn = findViewById(R.id.idBtnDelete);
        Spinner spinner = findViewById(R.id.spinner_main);
        // Initialize the calendar view selection variable.
        calendarView = findViewById(R.id.calendarView);
        opencal = findViewById(R.id.openCalWin);
        logUsername = findViewById(R.id.idTVlogUser1);
        logFullname = findViewById(R.id.idTVLogUserName1);
        LinearLayout calenderPage = findViewById(R.id.calenderPage);
        //hide the calender selection pop up window.
        calendar = Calendar.getInstance();
        calenderPage.setVisibility(View.GONE);
        //Get the bundle
        Bundle bundle = getIntent().getExtras();
        //Extract the data…
        String logInUsername = bundle.getString("logUser");
        // display to the textview
        logUsername.setText(logInUsername);
        // creating a new dbExpenses class and passing our context to it.
        dbExpenses = new DatabaseExpenses(UpdateExpenses.this);


        // display select record in the expense data entry field.
        updateExpenseEditText();
        // display the expense record in the RV view.
        displayExpenseData(logUsername.getText().toString());


        // calender data selection using calendarView widget
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month++;
                String formattedMonth = String.format("%02d", month);
                String formattedDay = String.format("%02d", day);
                String date1 = year + "-" + formattedMonth + "-" + formattedDay;
                // set the selected date on the textview and close the calender widget
                expenseDateEdt.setText(date1);
                calenderPage.setVisibility(View.GONE);
            }
        });

        // pop-up the calender widget to visible to allow date selection.
        opencal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calenderPage.setVisibility(View.VISIBLE);
            }
        });


        // on click listener for our add item button.
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // below line is to get data from all edit text fields.
                String expenseDate = expenseDateEdt.getText().toString();
                String expenseCategory = expenseCategoryEdt.getText().toString();
                String expenseDescription = expenseDescriptionEdt.getText().toString();
                String expenseAmount = expenseAmountEdt.getText().toString();
                String expenseLoguser = logUsername.getText().toString();

                // validating if the text fields are empty or not.
                if (expenseDate.isEmpty() || expenseCategory.isEmpty() || expenseDescription.isEmpty() || expenseAmount.isEmpty()) {
                    Toast.makeText(UpdateExpenses.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
                    return;
                }
                // format the amount to 2 decimal place.
                float number = Float.parseFloat(expenseAmount);
                String formattedAmount = String.format("%.2f", number);
                // Add new expense record to sqlite data and pass all our values to it.
                dbExpenses.addNewExpense(expenseDate, expenseCategory, expenseDescription, formattedAmount, expenseLoguser);

                // after adding the data we are displaying a toast message.
                Toast.makeText(UpdateExpenses.this, "Expense has been added.", Toast.LENGTH_SHORT).show();
                expenseDateEdt.setText("");
                expenseCategoryEdt.setText("");
                expenseDescriptionEdt.setText("");
                expenseAmountEdt.setText("");
                //update the recycler view to display updated transaction data.
                displayExpenseData(expenseLoguser);
            }
        });


        // adding on click listener to our update expense button.
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // validating if the text fields are empty or not.
                if (expenseAmountEdt.getText().toString().isEmpty() || expenseCategoryEdt.getText().toString().isEmpty() ||
                        expenseDescriptionEdt.getText().toString().isEmpty() || expenseDateEdt.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateExpenses.this, "Please enter all the data to update record.", Toast.LENGTH_SHORT).show();
                    return;
                }
                float number = Float.parseFloat(expenseAmountEdt.getText().toString());
                String formattedAmount = String.format("%.2f", number);
                // passing all our edit text values to update the record in SQL dbase.
                dbExpenses.updateExpense(oid, expenseDateEdt.getText().toString(), expenseCategoryEdt.getText().toString(), expenseDescriptionEdt.getText().toString(), formattedAmount);
                // displaying a toast message that selected expense record has been updated.
                Toast.makeText(UpdateExpenses.this, "Item Updated..", Toast.LENGTH_SHORT).show();
                expenseDateEdt.setText("");
                expenseCategoryEdt.setText("");
                expenseDescriptionEdt.setText("");
                expenseAmountEdt.setText("");
                //update the recycler view to display updated transaction data.
                displayExpenseData(logUsername.getText().toString());
            }
        });

        // adding on click listener for delete button to delete our course.
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating if the text fields are empty or not.
                // validating if the text fields are empty or not.
                if (expenseAmountEdt.getText().toString().isEmpty() || expenseCategoryEdt.getText().toString().isEmpty() ||
                        expenseDescriptionEdt.getText().toString().isEmpty() || expenseDateEdt.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateExpenses.this, "Please select a record from list to delete.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to delete record.
                dbExpenses.deleteExpense(oid);
                // displaying a toast message that selected expense record has been deleted.
                Toast.makeText(UpdateExpenses.this, "Item Deleted!", Toast.LENGTH_SHORT).show();
                expenseDateEdt.setText("");
                expenseCategoryEdt.setText("");
                expenseDescriptionEdt.setText("");
                expenseAmountEdt.setText("");
                //update the recycler view to display updated transaction data.
                displayExpenseData(logUsername.getText().toString());
            }
        });

        // drop down menu for category selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String item = adapterView.getItemAtPosition(position).toString();
                //Toast.makeText(MainActivity.this, "Selected " + item, Toast.LENGTH_SHORT).show();
                expenseCategoryEdt.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // for the expense category drop down menu selection setup
        ArrayList<String> arrayList = new ArrayList<>();
        // Populate the data for the drop down selection
        //arrayList.add(0, "Select Category");
        for (int i = 0; i < items.length; i++) {
            arrayList.add(items[i]);
        }
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // set category from the record selection.
        String expCategory = expenseCategoryEdt.getText().toString(); // read the string from the textview.
        int spos = adapter.getPosition(expCategory); // getting the position of the item by the item name in our adapter.
        spinner.setSelection(spos); //setting selection for our spinner to spinner position.
    }

    public void gotoDashboard2 (View view) {
        Intent intent = new Intent(UpdateExpenses.this, ExpenseDashboard.class);
        startActivity(intent);
    }
}