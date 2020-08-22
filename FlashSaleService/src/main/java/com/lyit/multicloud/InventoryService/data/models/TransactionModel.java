package com.lyit.multicloud.InventoryService.data.models;

import com.lyit.multicloud.InventoryService.data.constants.TransactionStates;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table
public class TransactionModel {

    @Id
    @GeneratedValue
    private Long id;

    private TransactionStates status;

    private Long inventoryId;

    private Long startTime;

    private Long endTime;
}