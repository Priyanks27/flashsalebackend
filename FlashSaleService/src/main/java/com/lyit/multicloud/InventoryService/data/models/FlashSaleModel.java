package com.lyit.multicloud.InventoryService.data.models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FlashSaleModel {
    private InventoryModel inventoryModel;

    private Integer amountToBuy;
}