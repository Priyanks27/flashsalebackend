package com.lyit.multicloud.InventoryService.data.dto;

import com.lyit.multicloud.InventoryService.data.models.InventoryModel;
import com.lyit.multicloud.InventoryService.data.models.TransactionModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlashSaleDTO {

    private TransactionModel transactionModel;
    private InventoryModel inventoryModel;
}
