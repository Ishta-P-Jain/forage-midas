package com.jpmc.midascore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    @ManyToOne
    private UserRecord sender;

    @ManyToOne
    private UserRecord recipient;

    public TransactionRecord() {}

    public TransactionRecord(double amount, UserRecord sender, UserRecord recipient) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
    }

    public double getAmount() {
        return amount;
    }

    public UserRecord getSender() {
        return sender;
    }

    public UserRecord getRecipient() {
        return recipient;
    }
}
