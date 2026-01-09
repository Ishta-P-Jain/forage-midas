package com.jpmc.midascore.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.model.Incentive;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class TransactionListener {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void processTransaction(Transaction transaction) {

        // Fetch users using IDs from foundation.Transaction
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        // Validate users
        if (sender == null || recipient == null) {
            return;
        }

        // Validate balance
        if (sender.getBalance() < transaction.getAmount()) {
            return;
        }

        // Call Incentive API
        Incentive incentive = restTemplate.postForObject(
                "http://localhost:8080/incentive",
                transaction,
                Incentive.class
        );

        double incentiveAmount =
                incentive != null ? incentive.getAmount() : 0.0;

        // Update balances (balance is float in UserRecord)
        sender.setBalance(
                sender.getBalance() - (float) transaction.getAmount()
        );

        recipient.setBalance(
                recipient.getBalance()
                        + (float) transaction.getAmount()
                        + (float) incentiveAmount
        );

        // Persist users
        userRepository.save(sender);
        userRepository.save(recipient);

        // Persist transaction record (NO incentive stored)
        TransactionRecord record = new TransactionRecord(
                transaction.getAmount(),
                sender,
                recipient
        );

        transactionRepository.save(record);
        System.out.println(
    "USER " + recipient.getId() + " BALANCE = " + recipient.getBalance()
);
       if (transaction.getRecipientId() == 1L) {  // wilbur's ID = 1
    System.out.println(
        "WILBUR FINAL BALANCE = " + recipient.getBalance()
    );
}

    }
}
