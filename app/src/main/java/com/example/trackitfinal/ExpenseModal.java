package com.example.trackitfinal;

public class ExpenseModal {

    // variables for our expenses name,
    // date, description, category, amount and id.
    private String expenseDate;
    private String expenseDescription;
    private String expenseCategory;
    private String expenseAmount;
    private String loguser;
    private String id;

    // creating getter and setter methods
    public String getExpenseDate() { return expenseDate; }

    public void setExpenseDate(String expenseDate)
    {
        this.expenseDate = expenseDate;
    }

    public String getExpenseDescription()
    {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription)
    {
        this.expenseDescription = expenseDescription;
    }

    public String getExpenseCategory() { return expenseCategory; }

    public void setExpenseCategory(String expenseCategory)
    {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseAmount()
    {
        return expenseAmount;
    }

    public void
    setExpenseAmount(String expenseAmount)
    {
        this.expenseAmount = expenseAmount;
    }

    public String getLoguser()
    {
        return loguser;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    // constructor
    public ExpenseModal (String expenseDate,
                         String expenseCategory,
                         String expenseDescription,
                         String expenseAmount,
                         String logUser,
                         String id)
    {
        this.expenseDate = expenseDate;
        this.expenseCategory = expenseCategory;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
        this.loguser = logUser;
        this.id = id;
    }
}