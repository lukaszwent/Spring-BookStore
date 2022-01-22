package com.example.shopApp.books;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String image;
    @Column(length = 3000)
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private double percentOfPromotion=0;
    private String author;
    private int numOfPages;
    private int soldTimes=0;
    private String category;


    public Book(String name, double price, int quantity, String image, String description, double percentOfPromotion, String category,String author,int numOfPages,int soldTimes,LocalDate date) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.description = description;
        this.percentOfPromotion = percentOfPromotion;
        this.category = category;
        this.author = author;
        this.numOfPages = numOfPages;
        this.soldTimes = soldTimes;
        this.date = date;
    }


}
