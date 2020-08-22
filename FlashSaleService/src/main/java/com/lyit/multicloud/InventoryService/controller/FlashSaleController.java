package com.lyit.multicloud.InventoryService.controller;

import com.lyit.multicloud.InventoryService.data.constants.TransactionStates;
import com.lyit.multicloud.InventoryService.data.dto.FlashSaleDTO;
import com.lyit.multicloud.InventoryService.data.dto.ResponseDTO;
import com.lyit.multicloud.InventoryService.data.models.FlashSaleModel;
import com.lyit.multicloud.InventoryService.data.models.InventoryModel;
import com.lyit.multicloud.InventoryService.data.models.InventoryModelList;
import com.lyit.multicloud.InventoryService.data.models.TransactionModel;
import com.lyit.multicloud.InventoryService.helper.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
public class FlashSaleController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/flashsale")
    public InventoryModelList getAllInventory() {
        RestTemplate restTemplate = new RestTemplate();
        InventoryModelList response = restTemplate.getForObject(
                "http://localhost:8181/inventory/",
                InventoryModelList.class);
        //Get the inventory list
        return response;
    }

    @PostMapping("/flashsale")
    public ResponseDTO flashSale(@RequestBody FlashSaleModel flashSaleModel) {
        RestTemplate restTemplate = new RestTemplate();
        InventoryModel inventoryModel = flashSaleModel.getInventoryModel();
        InventoryModel response = restTemplate.getForObject(
                "http://localhost:8181/inventory/" + inventoryModel.getId(),
                InventoryModel.class);

        //Get the inventory list from the response and check if there are sufficient items
        //in the inventory
        if(response.getInventoryCount() < + flashSaleModel.getAmountToBuy())
            return new ResponseDTO("Item count is not sufficient!", true, inventoryModel);

        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setStartTime(System.currentTimeMillis());
        transactionModel.setInventoryId(inventoryModel.getId());
        transactionModel.setStatus(TransactionStates.INPROGRESS);

        TransactionModel transaction = restTemplate.postForObject(
                "http://localhost:8383/transaction/", transactionModel, TransactionModel.class);

        //Process payment , it is a mock service and does not do anything
        Boolean paymentStatus = paymentService.processPayment();
        if(!paymentStatus)
            return new ResponseDTO("Payment failed!", true, inventoryModel);

        response.setInventoryCount(response.getInventoryCount() - flashSaleModel.getAmountToBuy());
        InventoryModel inventoryModelUpdated = updateInventoryModel(restTemplate, response);

        transaction.setStatus(TransactionStates.SUCCESS);
        transaction.setEndTime(System.currentTimeMillis());
        TransactionModel transactionModelUpdated = updateTransactionModel(restTemplate, transaction);
        FlashSaleDTO flashSaleDTO = new FlashSaleDTO(transactionModelUpdated, inventoryModelUpdated);
        return new ResponseDTO("Success", false, flashSaleDTO);
    }

    private TransactionModel updateTransactionModel(RestTemplate restTemplate, TransactionModel transactionModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<TransactionModel> entity = new HttpEntity<TransactionModel>(transactionModel,headers);
        TransactionModel transactionModelUpdated = restTemplate.exchange(
                "http://localhost:8383/transaction/", HttpMethod.PUT, entity,
                TransactionModel.class).getBody();
        return transactionModelUpdated;
    }

    private InventoryModel updateInventoryModel(RestTemplate restTemplate, InventoryModel inventoryModel) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<InventoryModel> entity = new HttpEntity<InventoryModel>(inventoryModel,headers);
        InventoryModel inventoryModelUpdated = restTemplate.exchange(
                "http://localhost:8181/inventory/", HttpMethod.PUT, entity,
                InventoryModel.class).getBody();
        return inventoryModelUpdated;
    }
}
