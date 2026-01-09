package com.jpmc.midascore.model;

public class Transaction {

    private String sender;
    private String recipient;
    private double amount;
    private double incentive;

    public Transaction() {
    }

    public Transaction(String sender, String recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.incentive = 0.0;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getIncentive() {
        return incentive;
    }

    public void setIncentive(double incentive) {
        this.incentive = incentive;
    }
}
