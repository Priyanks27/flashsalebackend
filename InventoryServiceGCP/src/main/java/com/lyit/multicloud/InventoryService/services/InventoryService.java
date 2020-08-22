package com.lyit.multicloud.InventoryService.services;

import com.lyit.multicloud.InventoryService.data.models.InventoryModel;

import java.util.List;
import java.util.UUID;

public interface InventoryService {

    InventoryModel addInventory(InventoryModel inventoryModel);

    InventoryModel updateInventory(InventoryModel inventoryModel);

    InventoryModel removeInventory(InventoryModel inventoryModel);

    InventoryModel getInventoryItemById(Long id);

    List<InventoryModel> getInventoryItemsByCategory(String category);

    List<InventoryModel> getInventoryItemsByInventoryName(String inventoryName);

    List<InventoryModel> getAllInventoryItems();
}
