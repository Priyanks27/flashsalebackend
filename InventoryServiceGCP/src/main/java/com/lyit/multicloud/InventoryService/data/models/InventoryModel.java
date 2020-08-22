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
@Table(name="InventoryModel")
public class InventoryModel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "inventoryName")
    private String inventoryName;

    @Column(name = "inventoryCount")
    private Integer inventoryCount;

    @Column(name = "priceModel")
    private String priceModel;
}