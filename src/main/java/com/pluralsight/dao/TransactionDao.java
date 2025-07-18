package com.pluralsight.dao;

import com.pluralsight.model.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import static com.pluralsight.AccountingLedgerApp.*;
import static com.pluralsight.Home.validation;

public class TransactionDao {
    // JDBC connection setup
    static String url = "jdbc:mysql://localhost:3306/accountingledger";
    static String username = "root";
    static String password = "yearup";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
   //Set attribute
    private DataSource dataSource;

    //Generate constructor
    public TransactionDao(DataSource dataSource) {
        this.dataSource = dataSource;

    }

//custom method to save to the file
public static void addDeposit() {
    // JDBC version: Write deposit information to a database instead of a CSV file
    try {
        // Prompt user for deposit details
        System.out.println("------------------------------");
        System.out.print("Enter your Deposit information : \n");
        System.out.println("------------------------------\n");

        String description = validation("Enter the Description: \n");
        String vendor = validation("Enter the Vendor: \n");
        String amountStr = validation("Enter the Amount: \n");

        double amount = Double.parseDouble(amountStr);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // Create a Transaction object
        Transaction deposit = new Transaction(date, time, description, vendor, amount);

        // Prepare SQL insert statement
        String sql = "INSERT INTO transactions (date, time, vendor,description, amount) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = getConnection().prepareStatement(sql);

        pstmt.setDate(1, java.sql.Date.valueOf(deposit.getDate()));
        pstmt.setTime(2, java.sql.Time.valueOf(deposit.getTime()));
        pstmt.setString(3, deposit.getVendor());
        pstmt.setString(4, deposit.getDescription());
        pstmt.setDouble(5, deposit.getAmount());

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            System.out.println("\n✅ Deposit added to the database successfully!");
            System.out.println(deposit.getDate().format(formatterDate) + " | " + deposit.getTime().format(formatterTime)
                    + " | " + deposit.getDescription() + " | " + deposit.getVendor() + " | " + deposit.getAmount());
        }

        // Close connection
        pstmt.close();
        getConnection().close();

    } catch (Exception e) {
        System.out.println("❌ Error while adding deposit: " + e.getMessage());
    }
}

    public static void addPayment() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("------------------------------");
            System.out.println("Enter your Debit information : ");
            System.out.println("------------------------------");

            // Use your existing validation() method to prompt for values
            String description = validation("Enter the Description: ");
            String vendor = validation("Enter the Vendor: ");
            String amountStr = validation("Enter the Amount: ");

            // Convert amount to negative double
            double amount = -1 * Math.abs(Double.parseDouble(amountStr));

            // Current date and time
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().withNano(0); // clean output, remove nanoseconds

            // Prepare SQL insert
            String sql = "INSERT INTO transactions (date, time, description, vendor, amount) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = getConnection().prepareStatement(sql)) {
                pstmt.setDate(1, Date.valueOf(date));
                pstmt.setTime(2, Time.valueOf(time));
                pstmt.setString(3, description);
                pstmt.setString(4, vendor);
                pstmt.setDouble(5, amount);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("\n✅ Payment added to the database successfully!");
                    System.out.println(date + " | " + time + " | " + description + " | " + vendor + " | " + amount);
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error while adding payment to database: " + e.getMessage());
        }
    }

}

