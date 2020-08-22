package com.lyit.multicloud.InventoryService.services.Impl;


import com.lyit.multicloud.InventoryService.data.models.TransactionModel;
import com.lyit.multicloud.InventoryService.repository.TransactionRepository;
import com.lyit.multicloud.InventoryService.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public TransactionModel createTransaction(TransactionModel transactionModel) {
        return transactionRepository.save(transactionModel);
    }

    @Override
    public TransactionModel updateTransaction(TransactionModel transactionModel) {
        if(transactionModel.getId() == null)
            return new TransactionModel();
        Optional<TransactionModel> optionalInventoryModel = transactionRepository.findById(transactionModel.getId());
        if(!optionalInventoryModel.isPresent()) return new TransactionModel();
        TransactionModel transactionModel1 = optionalInventoryModel.get();
        transactionModel1.setInventoryId(transactionModel.getInventoryId());
        transactionModel1.setStartTime(transactionModel.getStartTime());
        transactionModel1.setEndTime(transactionModel.getEndTime());
        transactionModel1.setStatus(transactionModel.getStatus());
        return transactionRepository.save(transactionModel1);
    }

    @Override
    public TransactionModel getTransactionById(Long id) {
        Optional<TransactionModel> optionalInventoryModel = transactionRepository.findById(id);
        if(optionalInventoryModel.isPresent()) return optionalInventoryModel.get();
        else return new TransactionModel();
    }

    @Override
    public List<TransactionModel> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
