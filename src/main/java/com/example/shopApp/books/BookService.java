package com.example.shopApp.books;


import com.example.shopApp.transaction.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final TransactionRepository transactionRepository;
    public Book insertBook(Book addedBook) {
        Book book = bookRepository.findByName(addedBook.getName());

        if(book == null){
            return bookRepository.save(addedBook);
        }

        return null;
    }

    public Book getBook(int id) {
        Book book = bookRepository.findById(id).get();
        if(book.getName().isEmpty()){
            return null;
        }
        return book;
    }

    public Page<Book> findSomeBooks(Pageable page) {
        return bookRepository.findAll(page);
    }

    public Page<Book> findPromotionBooks(Pageable page) {
        return bookRepository.findAllByPercentOfPromotionIsNot(0,page);
    }

    public Page<Book> findBestsellers(Pageable page) {
        return bookRepository.findAllBySoldTimesIsGreaterThan(0,page);
    }

    public Page<Book> findNewBooks(Pageable page) {
        return bookRepository.findAll(page);
    }

    public Page<Book> findBookByCategory(String category,Pageable page) {
        if(Objects.isNull(category)){
            return bookRepository.findAll(page);
        }
        return  bookRepository.findAllByCategory(category,page);
    }

    public void removeBook(int id) {
        try {
            Book b = bookRepository.findById(id).get();
            transactionRepository.deleteAll(transactionRepository.findAllByBooks(b));
             bookRepository.deleteById(id);
        }catch (Exception e){

        }

    }

    public void update(int id, Book updatedBook) {
        try{
            int sold = bookRepository.findById(id).get().getSoldTimes();
            updatedBook.setSoldTimes(sold);
            updatedBook.setId(id);
            bookRepository.save(updatedBook);
        }catch (Exception e){

        }

    }

    public List<String> getAllCategories(){
        List <Book> books = bookRepository.findAll();
        Set<String> list = new HashSet<String>();

        for(Book b: books){
            list.add(b.getCategory());
        }
        List<String> mainList = new ArrayList<String>();
        mainList.addAll(list);

        return mainList;
    }

}
