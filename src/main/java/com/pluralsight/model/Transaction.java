package com.pluralsight.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
//attributes/feature what our application should have.
private final LocalDate date;
private final LocalTime time;
private final String description;
private final String vendor;
private final double amount;

//Constructor: for setting the requirement of our app / initializing the values.
public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
    this.date = date;
    this.time = time;
    this.description = description;
    this.vendor = vendor;
    this.amount = amount;
}
//Custom Method to covert to string.
@Override //got this method from my peers
public String toString() {
    String type = (amount< 0 ) ? "Payment" : "Deposit";//this condition declare the type after evaluating the amount against the condition.
    return String.format("%-12s | %-12s | %-18s | %-18s | %18.2f, %s", date.toString(), time.toString(), description, vendor, amount, type);
}
//getter and setter for accessing the attributes

public LocalDate getDate() {
    return this.date;
}
public LocalTime getTime() {
    return this.time;
}
public String getDescription() {
    return this.description;
}
public String getVendor() {
    return this.vendor;
}
public double getAmount() {
    return this.amount;
}

}