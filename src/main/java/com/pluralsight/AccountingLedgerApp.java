package com.pluralsight;

import com.pluralsight.model.Transaction;

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
        Home.homeScreen();
}

}






