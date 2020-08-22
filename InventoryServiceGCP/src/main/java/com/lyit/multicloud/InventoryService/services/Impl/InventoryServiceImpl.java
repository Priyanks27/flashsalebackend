package com.lyit.multicloud.InventoryService.services.Impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lyit.multicloud.InventoryService.data.models.InventoryModel;
import com.lyit.multicloud.InventoryService.repository.InventoryRepository;
import com.lyit.multicloud.InventoryService.services.CacheService;
import com.lyit.multicloud.InventoryService.services.InventoryService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

@Log
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CacheService cacheService;

    @Override
    public InventoryModel addInventory(InventoryModel inventoryModel) {
        log.log(Level.INFO, "adding inventory");
        InventoryModel inventoryModel1 = inventoryRepository.save(inventoryModel);
        String json = new Gson().toJson(inventoryModel1);
        cacheService.putCache("inventory_" + inventoryModel1.getId(), json);
        log.log(Level.INFO, "adding inventory to cache");
        return inventoryModel1;
    }

    @Override
    public InventoryModel updateInventory(InventoryModel inventoryModel) {
        log.log(Level.INFO, "updating inventory : " + inventoryModel);
        //removing old value from cache
        cacheService.removeCacheByKey("inventory_" + inventoryModel.getId());

        if(inventoryModel.getId() == null)
            return new InventoryModel();
        Optional<InventoryModel> optionalInventoryModel = inventoryRepository.findById(inventoryModel.getId());
        if(!optionalInventoryModel.isPresent()) return new InventoryModel();
        InventoryModel inventoryModel1 = optionalInventoryModel.get();
        inventoryModel1.setCategory(inventoryModel.getCategory());
        inventoryModel1.setInventoryCount(inventoryModel.getInventoryCount());
        inventoryModel1.setInventoryName(inventoryModel.getInventoryName());
        inventoryModel1.setPriceModel(inventoryModel.getPriceModel());

        //updating new value to cache
        cacheService.putCache("inventory_" + inventoryModel.getId(), new Gson().toJson(inventoryModel1));
        log.log(Level.INFO, "updated inventory to cache");
        return inventoryRepository.save(inventoryModel1);
    }

    @Override
    public InventoryModel removeInventory(InventoryModel inventoryModel) {
        log.log(Level.INFO, "Remove inventory model : " + inventoryModel);
        inventoryRepository.deleteById(inventoryModel.getId());
        return inventoryModel;
    }

    @Override
    public InventoryModel getInventoryItemById(Long id) {
        log.log(Level.INFO, "Get inventory item by Id : " + id);
        Object obj = cacheService.getCache("inventory_" + id);
        String json = null;
        if(obj != null){
            json = obj.toString();
            Type type = new TypeToken<InventoryModel>() {
            }.getType();
            log.log(Level.INFO, "Get inventory item by Id from cache: " + id);
            return new Gson().fromJson(json, type);
        }
        Optional<InventoryModel> optionalInventoryModel = inventoryRepository.findById(id);
        if(optionalInventoryModel.isPresent()) return optionalInventoryModel.get();
        else return new InventoryModel();
    }

    @Override
    public List<InventoryModel> getInventoryItemsByCategory(String category) {
        log.log(Level.INFO, "Get inventory item by category : " + category);
        Optional<List<InventoryModel>> optionalInventoryModelList = inventoryRepository.findAllByCategory(category);
        if(optionalInventoryModelList.isPresent())
        {
            List<InventoryModel> inventoryModels = optionalInventoryModelList.get();
            if(inventoryModels != null || inventoryModels.size() != 0)
                return inventoryModels;
        }
        return new ArrayList<>();
    }

    @Override
    public List<InventoryModel> getInventoryItemsByInventoryName(String inventoryName) {
        log.log(Level.INFO, "Get inventory item by inventory name : " + inventoryName);
        Optional<List<InventoryModel>> optionalInventoryModelList =
                inventoryRepository.findAllByInventoryName(inventoryName);
        if(optionalInventoryModelList.isPresent())
        {
            List<InventoryModel> inventoryModels = optionalInventoryModelList.get();
            if(inventoryModels != null || inventoryModels.size() != 0)
                return inventoryModels;
        }
        return new ArrayList<>();
    }

    @Override
    public List<InventoryModel> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }
}
