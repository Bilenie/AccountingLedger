package com.pluralsight;

import com.pluralsight.model.Transaction;

import java.time.LocalDate;

import static com.pluralsight.AccountingLedgerApp.myScanner;

public class CustomSearch {

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Custom Search @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//
public static void customScreen() {
    //Display message
    System.out.println("-----------------------------------");
    System.out.println("----------- Custom Search ---------");
    System.out.println("-----------------------------------");

    //prompt the user to input search type
    System.out.println("Enter the Start Date (MM/dd/yyyy): \n");
    String startDate = myScanner.nextLine().trim();
    System.out.println("Enter the End Date (MM/dd/yyyy): \n");
    String endDate = myScanner.nextLine().trim();
    System.out.println("Enter the Description: \n");
    String description= myScanner.nextLine().trim();
    System.out.println("Enter the Vendor: \n");
    String vendor = myScanner.nextLine().trim();
    System.out.println("Enter the Amount: \n");
    String amount = myScanner.nextLine().trim();

    //convert input to the appropriate data type if not empty.
    LocalDate beginningDate = startDate.isEmpty() ? null : LocalDate.parse(startDate);
    LocalDate lastDate = endDate.isEmpty() ? null : LocalDate.parse(endDate);
    Double actualAmount = amount.isEmpty() ? null : Double.parseDouble(amount);


    //Based on the input do the logic
    boolean found = false;

    for (Transaction t : AccountingLedgerApp.allTransaction) {

        boolean matches = true;

        // ================= Date Matching ====================
        //if user input last date , get the date from all the transaction before the lastDate including itself.
        //if user input beginning date, get the date from all the transaction everything after the beginning date including itself.
        //No input skip it.
        //Date should be in the transaction and match it, if not print "no transaction found with this date".

        LocalDate tDate = t.getDate();

        if (beginningDate != null && lastDate != null) {
            if (tDate.isBefore(beginningDate) || tDate.isAfter(lastDate)) {
                matches = false;
            }
        } else if (beginningDate != null) {
            if (tDate.isBefore(beginningDate)) {
                matches = false;
            }
        } else if (lastDate != null) {
            if (tDate.isAfter(lastDate)) {
                matches = false;
            }
        }

        // ================ Description Matching ================

        if (!description.isBlank() && !t.getDescription().toLowerCase().contains(description.toLowerCase())) {
            //if user input description we get all the transaction matching the description input.
            //if the description input not in the transaction display no transaction found.
            //if user left it empty skip it.
            matches = false;
        }

        // ================ Vendor Matching =====================
        if (!vendor.isBlank() && !t.getVendor().toLowerCase().contains(vendor)) {
            //if user input vendor we will get all the transaction matching the vendor input.
            //if the vendor input not in the transaction display no transaction found.
            //if user left it empty skip it.
            matches = false;
        }

        // ================ Amount Matching =====================
        if (actualAmount != null && t.getAmount() != actualAmount) {
            //if user input amount get all the transaction matching the amount input.
            //if the amount input not in the transaction display no transaction found.
            //if user left it empty skip it.
            matches = false;
        }

        if (matches) {
            System.out.println(t);
            found = true;
        }
    }

    if (!found) {
        System.out.println("No transactions matched your custom search.");
    }
}
}
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Custom Search @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//

