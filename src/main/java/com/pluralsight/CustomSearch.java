package com.pluralsight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.pluralsight.AccountingLedgerApp.*;

public class CustomSearch {
    //format date
    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Custom Search @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@//
    public static void customScreen() {
        Ledger.getTransaction();

        //Display message
        System.out.println("-----------------------------------");
        System.out.println("----------- Custom Search ---------");
        System.out.println("-----------------------------------");

        //prompt the user to input search type
        System.out.println("Enter the earliest date you want to filter for (MM/dd/yyyy): \n");
        String startDate = myScanner.nextLine().trim();

        System.out.println("Enter the end date for your filter (MM/dd/yyyy): \n");
        String endDate = myScanner.nextLine().trim();

        System.out.println("Enter the Vendor: \n");
        String vendor = myScanner.nextLine().trim();

        System.out.println("For the amount filter you will enter the minimum and maximum amounts: \n");
        System.out.print("Enter minimum amount: \n");
        String minAmount = myScanner.nextLine().trim();

        System.out.print("Enter maximum amount: \n");
        String maxAmount = myScanner.nextLine().trim();

        //convert input to the appropriate data type if not empty.
        LocalDate beginningDate = startDate.isEmpty() ? null : LocalDate.parse(startDate, dateFormat);
        LocalDate lastDate = endDate.isEmpty() ? null : LocalDate.parse(endDate, dateFormat);
        Double actualMin = minAmount.isEmpty() ? null : Double.parseDouble(minAmount);
        Double actualMax = maxAmount.isEmpty() ? null : Double.parseDouble(maxAmount);

        //Based on the input do the logic
        boolean found = false;

        for (Transaction t : AccountingLedgerApp.allTransaction) {
            // REMOVED: System.out.println(t); - This was printing every transaction

            // ================= Date Matching ====================
            LocalDate tDate = t.getDate();

            if (beginningDate != null && lastDate != null) {
                if (tDate.isBefore(beginningDate) || tDate.isAfter(lastDate)) {
                    continue;
                }
            } else if (beginningDate != null) {
                if (tDate.isBefore(beginningDate)) {
                    continue;
                }
            } else if (lastDate != null) {
                if (tDate.isAfter(lastDate)) {
                    continue;
                }
            }

            // ================ Vendor Matching =====================
            if (!vendor.isBlank() && !t.getVendor().toLowerCase().contains(vendor.toLowerCase())) {
                // FIXED: Made both sides lowercase for proper case-insensitive matching
                continue;
            }

            // ================ Amount Matching =====================
            // FIXED: Corrected the logic - should be < for min and > for max
            if (actualMin != null && t.getAmount() < actualMin) continue;
            if (actualMax != null && t.getAmount() > actualMax) continue;

            // Only print transactions that match ALL criteria
            System.out.println(t);
            found = true;
        }

        if (!found) {
            System.out.println("No transactions matched your custom search.");
        }
    }
}