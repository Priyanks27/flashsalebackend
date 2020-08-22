package com.lyit.multicloud.InventoryService.repository;

import com.lyit.multicloud.InventoryService.data.models.InventoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryModel, Long> {
    Optional<List<InventoryModel>> findAllByCategory(String category);
    Optional<List<InventoryModel>> findAllByInventoryName(String inventoryName);

}
