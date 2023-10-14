package com.casestudy.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vending_machine")
public class VendingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "inserted_money")
    private int insertedMoney;


    @Column(name = "temperature")
    private float temperature;

    @Column(name = "balance")
    private int balance;
}
