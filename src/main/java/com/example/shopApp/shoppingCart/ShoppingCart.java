package com.example.shopApp.shoppingCart;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ShoppingCart {
    private String street;
    private String country;
    private String state;
}