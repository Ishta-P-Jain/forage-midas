package com.jpmc.midascore.component;

import org.springframework.stereotype.Component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class DatabaseConduit {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public DatabaseConduit(UserRepository userRepository,
                           TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    // ✅ REQUIRED by test scaffolding (DO NOT REMOVE)
    public void save(UserRecord userRecord) {
        userRepository.save(userRecord);
    }

    // ✅ Task 3 logic
    public void process(Transaction transaction) {

        UserRecord sender =
                userRepository.findById(transaction.getSenderId());
        UserRecord recipient =
                userRepository.findById(transaction.getRecipientId());

        // validate users
        if (sender == null || recipient == null) {
            return;
        }

        double amount = transaction.getAmount();

        // validate balance
        if (sender.getBalance() < amount) {
            return;
        }

        // update balances (balance is float)
        sender.setBalance((float) (sender.getBalance() - amount));
        recipient.setBalance((float) (recipient.getBalance() + amount));

        userRepository.save(sender);
        userRepository.save(recipient);

        // record transaction
        TransactionRecord record =
                new TransactionRecord(amount, sender, recipient);

        transactionRepository.save(record);
        if ("waldorf".equals(sender.getName())) {
    System.out.println("WALDORF BALANCE = " + sender.getBalance());
}
if ("waldorf".equals(recipient.getName())) {
    System.out.println("WALDORF BALANCE = " + recipient.getBalance());
}

    }
}
