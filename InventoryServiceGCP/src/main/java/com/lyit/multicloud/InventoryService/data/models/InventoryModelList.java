package com.lyit.multicloud.InventoryService.data.models;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InventoryModelList {

    List<InventoryModel> inventoryModelList;
}