package com.example.shopApp.transaction;

import com.example.shopApp.books.Book;
import com.example.shopApp.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

    List<Transaction> findAllById(int id, Pageable page);
    
    List<Transaction> findAllByUser(User user);

    List<Transaction> findAllByBooks(Book book);
}
