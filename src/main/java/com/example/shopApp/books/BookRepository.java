package com.example.shopApp.books;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;


import java.util.List;


public interface BookRepository extends PagingAndSortingRepository<Book,Integer> {


    Page <Book> findAll(Pageable pageable);

    Book findByName(String Name);

    Page<Book> findAllByPercentOfPromotionIsNot(double percentOfPromotion, Pageable page);

    Page <Book> findAllByCategory(String category,Pageable pageable);

    Page<Book> findAllBySoldTimesIsGreaterThan(int grate,Pageable pageable);

    List <Book> findAll();

}
