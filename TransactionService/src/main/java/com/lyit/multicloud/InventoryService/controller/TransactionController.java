package com.lyit.multicloud.InventoryService.controller;

import com.lyit.multicloud.InventoryService.data.models.TransactionModel;
import com.lyit.multicloud.InventoryService.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public TransactionModel createTransaction(@RequestBody TransactionModel transactionModel) {
        return transactionService.createTransaction(transactionModel);
    }

    @PutMapping("/transaction")
    public TransactionModel updateTransaction(@RequestBody TransactionModel transactionModel) {
        return transactionService.updateTransaction(transactionModel);
    }

    @GetMapping("/transaction/{id}")
    public TransactionModel getTransactionById(@PathVariable Long id) {
     return transactionService.getTransactionById(id);
    }

    @GetMapping("/transaction")
    public List<TransactionModel> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}
