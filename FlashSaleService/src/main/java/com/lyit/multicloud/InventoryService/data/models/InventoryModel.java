package com.lyit.multicloud.InventoryService.data.models;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class InventoryModel {

    @Id
    @GeneratedValue
    private Long id;

    private String category;

    private String inventoryName;

    private Integer inventoryCount;

    private String priceModel;
}