package com.pluralsight;

import com.pluralsight.model.Transaction;
import org.apache.commons.dbcp2.BasicDataSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingLedgerApp {

//declare the scanner,datetime formatter, ArrayList as a static to be able to use it throughout the class.
static Scanner myScanner = new Scanner(System.in);
static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
static ArrayList<Transaction> allTransaction = new ArrayList<>();
static LocalDate today = LocalDate.now();
static int currentYear = today.getYear();
static int currentMonth = today.getMonthValue();

//Static LocalDateTime dateTimeConvert = LocalDateTime.parse( "date time", formatter);
public static void main(String[] args) {
        //Display the menu page and everything linked to it.

        //did the user pass in the right number of arguments at runtime
        if (args.length != 2) {
                System.out.println("Application needs two arguments to run: <username> <password>");
                System.exit(1);
        }

        // Get the username and password
        String username = args[0];
        String password = args[1];

        // Create the datasource
        BasicDataSource dataSource = new BasicDataSource();
        // Configure the datasource
        dataSource.setUrl("jdbc:mysql://localhost:3306/Northwind");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        Home.homeScreen();
}

}






