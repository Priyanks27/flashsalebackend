package com.lyit.multicloud.InventoryService.services;

import com.lyit.multicloud.InventoryService.data.models.TransactionModel;

import java.util.List;

public interface TransactionService {

    TransactionModel createTransaction(TransactionModel transactionModel);

    TransactionModel updateTransaction(TransactionModel transactionModel);

    TransactionModel getTransactionById(Long id);

    List<TransactionModel> getAllTransactions();
}
