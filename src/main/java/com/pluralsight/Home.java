package com.pluralsight;
import com.pluralsight.model.Transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.pluralsight.AccountingLedgerApp.*;
import static com.pluralsight.dao.TransactionDao.addDeposit;

public class Home {

//**************************** HomeScreen Display Method **********************************************//
public static void homeScreen() {
    boolean input = true;
    while (input) {
        // Method to display the menu options
        System.out.println("-----------------------------------");
        System.out.println("Welcome to Accounting Ledger App!!!");
        System.out.println("----------- Home Screen -----------");
        System.out.println("-----------------------------------");

        System.out.print("What would you like to do?  Select D,P, L or X.\n");
        System.out.print("D)Add Deposit\n");
        System.out.print("P)Make Payment(Debit)\n");
        System.out.print("L)Ledger\n");
        System.out.print("X)Exit\n");

        //take the option choice from the menu store it in variable called userChoice.
        String actualChoice = myScanner.nextLine();

        //If user chooses (D)
        if (actualChoice.equalsIgnoreCase("D")) {
            //ask the deposit information by invoking the addDeposit function.
            addDeposit();
            //If user choose (P)
        } else if (actualChoice.equalsIgnoreCase("P")) {
            addPayment();
        } //If user choose (L) show Ledger
        else if (actualChoice.equalsIgnoreCase("L")) {
            //=========================== Ledger Screen =====================
            //Calling this method display the Ledger menu options
            Ledger.ledgerScreen();
        }
        //If user choose (X) exit the application
        else if (actualChoice.equalsIgnoreCase("X")) {
            System.out.println("Goodbye!");
            System.exit(0);;  //Properly exit the program
        } else {
            System.out.println("Invalid option. Try again.");
        }
        /* Display welcome message and options on the screen/console*/
    }
}
//***************************** End *********************************************************//

//******************************* Add Deposit Method ******************************************************//
/* public static void addDeposit() {
    //Method to get deposit information(date,time,description,vendor & amount) and write it to transaction.csv

    //wrap the question with answer return in a while loop because I want to ask each question again if it is not valid.
    try {
        //first create a file and write the header (only if file is new)
        String fileCsv = "src/main/resources/transaction.csv";//declare the file name we want with a path.

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileCsv, true));//write true so it doesn't overwrite the file.Keep old records.

        //Set condition to write the header => create only if the file is empty so that it won't repeat.
        File file = new File(fileCsv);// This I got from AI =>this doesn't write, just points to the file

        if (file.length() == 0) {  // checks if file is empty
            writer.write("Date | Time | Description | Vendor | Amount ");
        }
        writer.newLine();//to move to next line after header

        //prompt the user for the deposit information.
        System.out.println("------------------------------");
        System.out.print("Enter your Deposit information : \n");
        System.out.println("------------------------------\n");

        //passing the questions in the method validation that return the answer and validate it and store it a variable that describe it.

        String description = validation("Enter the Description: \n");
        String vendor = validation("Enter the Vendor: \n");
        String amount = validation("Enter the Amount: \n");//this a string so needed to be parsed to double.
        //amount is a double in the Transaction class but when we received it as answer it is a string.
        //The date and time stamp will be added to the file automatically
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // Write the collected information to a file in a format required
        // Instantiate an object from our class transaction
        Transaction collectDepositInfo = new Transaction(date,time,description, vendor, Double.parseDouble(amount));
        String displayDeposit= collectDepositInfo.getDate().format(formatterDate) + " | " + collectDepositInfo.getTime().format(formatterTime) + " | " + collectDepositInfo.getDescription() + " | " + collectDepositInfo.getVendor() + " | " + collectDepositInfo.getAmount();

        //write the deposit information to the transaction.csv in this format.
        writer.write(displayDeposit);
        writer.newLine();//write on new line=> format when it's written in the file.
        writer.close();//closing the buffer the writer.
        //Display this message if done correctly
        System.out.println("\nDeposit Added to the file Successfully!");
        System.out.println(displayDeposit);

    } catch (Exception e) {
        System.out.println("Error while adding deposit: " + e.getMessage());
    }
    /* Create csv file and activate our File/Buffered writer with the path of our file.
     * Prompt user to answer deposit info using validation method and store it in variable.
     * Get the Date and Time stamp with formatter better than using default
     * Convert the amount to double because it's string and store in variable.
     * Create an object to collect the csv files data  pass argument.
     * we write on the object with the format passed.
     * Then print success message once done  */

//***************************** End of addDeposit *********************************************************//

//******************************* Add Payment/Debit Method *****************************************************//
public static void addPayment() {
//Method to get payment information(date,time,description,vendor & amount) and write it to transaction.csv
    try {//first create a file and write the header (only if file is new)
        String fileCsv = "src/main/resources/transaction.csv";//declare the file name we want with a path.
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileCsv, true));//write true so it doesn't overwrite the file.Keep old records.

        //prompt the user for the payment information.
        System.out.println("------------------------------");
        System.out.print("Enter your Debit information : \n");
        System.out.println("------------------------------\n");

        //passing the questions in the method validation that return the answer and validate it and store it a variable that describe it.
        String description = validation("Enter the Description: \n");
        String vendor = validation("Enter the Vendor: \n");
        String amount = validation("Enter the Amount: \n");//this a string so needed to be parsed to double.
        //amount is a double in the Transaction class but when we received it as answer it is a string.
        // The date and time stamp will be added to the file automatically
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now(); // remove nanoseconds for cleaner output=>can also pass the formatter (time.format(formatter1)/date.format(formatter2)

        // Write the collected information to a file in a format required
        // Instantiate an object from our class transaction
        double amountDouble = Double.parseDouble(amount);
        amountDouble = -1 * Math.abs(amountDouble);
        Transaction collectDebitInfo = new Transaction(date, time, description, vendor, amountDouble);
        String displayPaymentInfo = collectDebitInfo.getDate().format(formatterDate) + " | " + collectDebitInfo.getTime().format(formatterTime) + " | " + collectDebitInfo.getDescription() + " | " + collectDebitInfo.getVendor() + " | " + collectDebitInfo.getAmount();

        //write the payment information to the transaction.csv in this format.
        writer.write(displayPaymentInfo);
        writer.newLine();//write on new line=> format when it's written in the file.
        writer.close();//closing the buffer the writer.
        //Display this message if done correctly
        System.out.println("\nPayment Added to the file Successfully!");
        System.out.println(displayPaymentInfo);
    } catch (Exception e) {
        System.out.println("Error while adding payment: " + e.getMessage());
    }
    /*Create csv file and activate our File/Buffered writer with the path of our file.
     * Prompt user to answer deposit info using validation method and store it in variable.
     * Get the Date and Time stamp with formatter better than using default
     * Convert the amount to double because it's string and store in variable.
     * Then do Math.abs to get the absolut value and times it with -1 to have negative because we are dealing with payments.
     * Create an object to collect the csv files data  pass argument.
     * we write on the object with the format we want  the information we want to get.
     * Then print success message once done  */
}
//***************************** End of Payment/Debit Method *********************************************************//

//****************************************** Validation Method ********************************************//
public static String validation(String ask) {
// method to validate if input to the questions is not valid for every question on the addDeposit method.
// return type string and parameter is the question I am asking at the addDeposit method.
    String answer = "";// answer to the questions start with nothing
    while (true) {
        System.out.print(ask);//ask question to the user
        answer = myScanner.nextLine().trim();//take the input from the users about the deposit information store it a variable.
        if (answer.isEmpty()) {
            //if the users pass empty input this will be display and continue asking the question.
            System.out.println("Input cannot be empty. Please enter a valid response.");
        } else {
            break;
        }
    }
    return answer;// it will return me the answer to the question after validating .
    /*method that have a parameter of string.
     * Assist on first ask questions and take user input
     * Based on the input evaluate the condition and return the answer.*/
}
//***************************** End of validation ************************************************//

}
