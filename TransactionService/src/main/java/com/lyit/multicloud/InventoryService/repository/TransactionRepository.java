package com.lyit.multicloud.InventoryService.repository;

import com.lyit.multicloud.InventoryService.data.models.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long> {
}
