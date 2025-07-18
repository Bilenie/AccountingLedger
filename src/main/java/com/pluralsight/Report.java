package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.time.LocalDate;
import static com.pluralsight.AccountingLedgerApp.*;

public class Report {

//#################################Report Screen###############################//
public static void reportScreen() {
    try {
        boolean askAgain = true;
        while (askAgain) {
            System.out.println("-----------------------------------");
            System.out.println("Welcome to The Report Screen !!!");
            System.out.println("-----------------------------------");

            System.out.print("What would you like to do?  Select 1-6 & 0 (back).\n");
            System.out.print("1)Month To Date  \n");
            System.out.print("2)Previous Month\n");
            System.out.print("3)Year To Date \n");
            System.out.print("4)Previous Year\n");
            System.out.print("5)Search by Vendor \n");
            System.out.print("6)Custom Search\n");
            System.out.print("0)Back \n");//Ledger Screen
            String choice = myScanner.nextLine();

            switch (choice) {
                // If user chose "1"
                case "1":
                    monthDateReport();
                    break;
                // If user chose "2"
                case "2":
                    previousMonth();
                    break;
                // If user chose "3"
                case "3":
                    yearToDate();
                    break;
                // If user chose "4"
                case "4":
                    previousYear();
                    break;
                // If user chose "5"
                case "5":
                    searchVendor();
                    break;
                // If user chose "6"
                case "6":
                    CustomSearch.customScreen();
                    break;
                // If user chose "0"
                case "0":
                    Ledger.ledgerScreen();
                    break;
                // If user entered any invalid option
                default:
                    System.out.println("Invalid Number please try again!!!.");
                    break;
                /*Display an option for the user to choose from.
                 * Based on the choice display the screen and results*/
            }
        }
    } catch (Exception e) {
        System.out.println("error"+e.getMessage());
    }
}
//################################# End Report Screen ###############################//

//################################# Report Month to Date ###############################//
public static void monthDateReport() {
    Ledger.getTransaction();
    for (Transaction t : allTransaction) {
        if (t.getDate().getMonthValue() == currentMonth && t.getDate().getYear() == currentYear) {
            System.out.println(t);
        }
    }
    /*get the current month
     *loop through /iterate list of transaction
     *for each transaction comparing the month of the date of the transaction  to the current month
     *print if there is the match and skip if therenot a match*/
}
//################################# End Report Month to Date ###############################//

//################################# Report Previous Year ###############################//
public static void previousMonth() {
    Ledger.getTransaction();
    LocalDate firstDayOfLastMonth = today.minusMonths(1).withDayOfMonth(1);
    LocalDate lastDayOfLastMonth = today.withDayOfMonth(1).minusDays(1);
    for (Transaction t : allTransaction) {
        LocalDate date = t.getDate();
        if (!date.isBefore(firstDayOfLastMonth) && !date.isAfter(lastDayOfLastMonth)) {
            System.out.println(t);
        }
    }
    /*get the 1st day of last month and last day of last month
     *loop through /iterate list of transaction and get the dates
     *for each transaction date evaluate if our date is between previous 1-29
     *print if there is a previous month and everything in between.*/
}
//################################# End Report Previous Year ###############################//

//################################# Report Year To Date ###############################//
public static void yearToDate() {
    Ledger.getTransaction();
    LocalDate firstDayOfYear = today.withDayOfYear(1); //get this year firstJan 01/2025
    for (Transaction t : allTransaction) {
        LocalDate date = t.getDate();
        if ((date.isEqual(firstDayOfYear) || date.isAfter(firstDayOfYear)) &&
                date.isBefore(today.plusDays(1))) {
            System.out.println(t);
        }
    }
    /*get the first day of the year. Jan 01 of the current year
     *loop through /iterate list of transaction and store it an object t created from transaction class.
     * from the object access only the date
     *Then evaluate on the date if its Jan 01 of current year or after Jan 01 of current year but need to be equal with the before the exact current day it-self.
     *Condition met print the transaction*/
}
//################################# End Report Year To Date##############################//

//################################# Report Previous Year  ###############################//
public static void previousYear() {
    Ledger.getTransaction();
    LocalDate firstDayOfLastYear = today.minusYears(1).withDayOfYear(1);//(Jan 01/2024)get current date 05/01/2025 and minus -1 from the year now year is 2024 and to the 2024 get the first day that is Jan1.
    LocalDate lastDayOfLastYear = today.withDayOfYear(1).minusDays(1);//(Dec 31/2024)get current date 05/01/2025 and to the 2025 get the first day that is Jan1. minus -1 from the day so we get Dec31 of 2024
    for (Transaction t : allTransaction) {
        LocalDate date = t.getDate();
        if (!date.isBefore(firstDayOfLastYear) && !date.isAfter(lastDayOfLastYear)) {
            System.out.println(t);
        }
    }
    /*get the 1st day of last year and last day of last year
     *loop through /iterate list of transaction and get the dates
     *for each transaction date evaluate if our date is between previous year first day Jan1 & Dec 31
     *print transaction if the condition evaluate true.*/
}
//################################# End Report Previous Year###############################//

//################################# Report Vendor Search ###############################//
public static void searchVendor() {
    Ledger.getTransaction();
    System.out.println("Enter Vendor : ");
    String searchVendor = myScanner.nextLine().trim();
    for (Transaction t : allTransaction) {
        if (t.getVendor().equalsIgnoreCase(searchVendor)) {
            System.out.println(t);
        }
    }
}
//################################# End Report Vendor Search ###############################//

}