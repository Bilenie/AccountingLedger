package com.pluralsight;
import com.pluralsight.model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

import static com.pluralsight.AccountingLedgerApp.*;
public class Ledger {
// ============================ LEDGER SCREEN ============================== //
public static void ledgerScreen() {
    // Method to display the menu options
    boolean askAgain = true;
    while (askAgain) {
        System.out.println("-----------------------------------");
        System.out.println("Welcome to The Ledger Screen !!!");
        System.out.println("-----------------------------------");

        System.out.print("What would you like to do?  Select A,D, P,R or H.\n");
        System.out.print("A)All Entries \n");
        System.out.print("D)Deposit Only\n");
        System.out.print("P)Payments Only \n");
        System.out.print("R)Reports\n");
        System.out.print("H)Home-Screen \n");

        //take the option choice from the menu store it in variable called userChoice.
        String actualChoice1 = myScanner.nextLine();
        //If user chooses (A)
        switch (actualChoice1.toUpperCase()) {
            // If user chose "A"
            case "A":
                //Call method to read and display all transactions (deposits and payments)
                Ledger.showTransaction();
                break;
            // If user chose "D"
            case "D":// Call method to read and display only deposit (positive amount) transactions
                Ledger.readDeposit();
                break;
            // If user chose "P"
            case "P":
                // Call method to read and display only payment (negative amount) transactions
                Ledger.readPayment();
                break;
            // If user chose "R" or "r"
            case "R":
                // Call method to display the Reports menu screen
                Report.reportScreen();
                break;
            // If user chose "H" or "h"
            case "H":
                // Return to the Home screen
                Home.homeScreen();
                break;
            // If user entered any invalid option
            default:
                System.out.println("Invalid option. Try again.");
                break;
            /* Display on the console the option for LedgerScreen*/
        }
    }
}
// ============================ End LEDGER SCREEN ============================== //

// ============================ LEDGER All Entries(A) ============================== //
public static ArrayList<Transaction> getTransaction() {
//Method that Return ArrayList that have all the transaction when called.

/*I added allTransactions.clear() at the top of my getTransactions() method to prevent duplicate data from being shown in my reports.
Since allTransactions is declared outside the method, it holds its values between method calls. Without clearing it, every time
I ran a report, the same transactions were being added on top of the old ones, causing duplicates.
By calling .clear(), I make sure the list is emptied before reading from the csv file again. This ensures that each report
always reflects only the current contents of the file — not a buildup from previous runs.*/

    allTransaction.clear();

    try {
//Read from the csv file with a booster buffer.
        BufferedReader transactionRead = new BufferedReader(new FileReader("src/main/resources/transaction.csv"));//reading the file
//Reading by Looping/Iterate through the entire transaction data in the csv file line by line if it's not empty.
        String theLine;
        while ((theLine = transactionRead.readLine()) != null) {

            //split what it read into parts using ("\\|")as a delimiter and store it in an array of string.
            String[] transactionInfo = theLine.split("\\|");

            //If the length not 5 or line start with "Date" skip to read and continue to next line.
            if (transactionInfo.length != 5 || transactionInfo[0].startsWith("Date")) {
                continue;
            }

//Access the Array called transactionInfo using (.[index]) then parse it to store in a variable.
//Use.trim() on each element csv file to remove extra spaces.
            LocalDate dateStamp = LocalDate.parse(transactionInfo[0].trim());
            LocalTime timeStamp = LocalTime.parse(transactionInfo[1].trim());
            String description = transactionInfo[2].trim();
            String vendor = transactionInfo[3].trim();
            double amount = Double.parseDouble(transactionInfo[4].trim());

//instantiate a new object called it "theTransaction" from transaction class and pass in as an arguments the items from the array.
            Transaction theTransaction = new Transaction(dateStamp, timeStamp,
                    description, vendor, amount);

//Add the transactions to the allTransaction array list using allTransaction.add
            allTransaction.add(theTransaction);
        }
//Sorting
        Collections.reverse(allTransaction);
//Close the buffer.
        transactionRead.close();
    } catch (Exception e) {
        System.out.print("Error reading Transaction file." + e.getMessage() + e.getStackTrace());
    }
    return allTransaction;

    /*FileReader opens the file.
     *BufferedReader reads bigger chunks of text for better performance.
     *While loop keeps reading each line until there are no more lines (null).
     *Put what we read in an array of string by splitting it.
     * Parse it when access and store it a variable.
     *Create object to hold our list of items.
     *Add our object to the ArrayList.
     */
}
public static void showTransaction() {
//Method for Showing all the transaction to display on the console

    getTransaction();// Capture the returned Arraylist.
    System.out.println("-----------------------------------");
    System.out.println("This is all the transactions");//Display message
    System.out.println("-----------------------------------");

//loop through it to  display and show :-
    for (Transaction t : allTransaction) {
//display all transaction with its Date,Time,Description,Vendor, & amount.
        System.out.println(t);
    }
    /* We call the getTransaction method that read the input data saved from csv file and add it to our ArrayList.
     * Display message will show.
     * We sort it in descending order to get the latest first before the print/display.
     * Loop through the arrayList to get all element in the list
     * Display it on our console we are calling the toString for the format we want. */
}
// ============================ End All Entries(A) ==============================//

// ============================ LEDGER Deposit Only(D) ==============================//
public static void readDeposit() {
//call this method for reading from the files
    getTransaction();
//Display message
    System.out.println("--------Your Deposit Information-------\n\n");
    for (Transaction t : allTransaction) {
        if (t.getAmount() > 0) {
            System.out.println(t);
        }
        /* We call the getTransaction method that read the input data saved from csv file and add it to our ArrayList.
         * Display message will show
         * We sort it in descending order to get the latest first before the print/display.
         * Loop through the arrayList to get every element/deposit in the list.
         * Display it on our console */
    }
}
// ============================ End  Deposit Only ==============================//

//============================ LEDGER Payment Only(P) ==============================//
public static void readPayment() {
//call this method for reading from the files
    getTransaction();
//Display message
    System.out.println("--------Your Payment Information-------\n\n");

    for (Transaction t : allTransaction) {
        if (t.getAmount() < 0) {
            System.out.println(t);
        }

            /* We call the getTransaction method that read the input data saved from csv file and add it to our ArrayList.
             * Display message will show
             * We sort it in descending order to get the latest first before the print/display.
             * Loop through the arrayList to get every element/Payment in the list.
             * Display it on our console */
        }
    }
}
//============================ End Payment Only ==============================//

