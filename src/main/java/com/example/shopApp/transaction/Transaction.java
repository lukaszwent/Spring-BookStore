package com.example.shopApp.transaction;

import com.example.shopApp.Audit;
import com.example.shopApp.books.Book;
import com.example.shopApp.user.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private User user;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book books;
    @Embedded
    private Audit audit = new Audit();
    private String street;
    private String country;
    private String state;
    private double paid;


    public Transaction(User user, Book books,String country,String state, String street,double paid) {
        this.user = user;
        this.books = books;
        this.country = country;
        this.state = state;
        this.street = street;
        this.paid = paid;
    }
}
