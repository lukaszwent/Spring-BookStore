package com.example.shopApp.shoppingCart;

import com.example.shopApp.NotEnoughProductsInStockException;
import com.example.shopApp.books.Book;

import com.example.shopApp.books.BookService;

import com.example.shopApp.transaction.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;

import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class ShoppingCartService{

    private final TransactionService transactionService;
    private final BookService bookService;
    private Map<Book, Integer> products = new HashMap<>();


    public void addProduct(Book book) {
        if (products.containsKey(book)) {
            products.replace(book, products.get(book) + 1);
        } else {
            products.put(book, 1);
        }
    }


    public void removeProduct(Book book) {
        if (products.containsKey(book)) {
            if (products.get(book) > 1)
                products.replace(book, products.get(book) - 1);
            else if (products.get(book) == 1) {
                products.remove(book);
            }
        }
    }


    public Map<Book, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }

    public int getNumberOfItems(){
        int sum=0;
        for (Map.Entry<Book, Integer> entry : products.entrySet()) {
            for(int i=0;i<entry.getValue();i++){
                sum++;
            }
        }
        return sum;
    }

    public void checkout(ShoppingCart shoppingCart) throws NotEnoughProductsInStockException {


        try{
            List<Integer> ids = new ArrayList<>();

            for (Map.Entry<Book, Integer> entry : products.entrySet()) {

                if(bookService.getBook(entry.getKey().getId()).getQuantity() < entry.getValue()){
                    throw new NotEnoughProductsInStockException();
                }
                for(int i=0;i<entry.getValue();i++){
                    ids.add(entry.getKey().getId());
                }
            }

            transactionService.purchaseBooks(ids,shoppingCart);
            products.clear();

        }catch (Exception e){

        }

    }

    public double getTotal() {
        double sum=0;
        for(Map.Entry<Book, Integer> entry : products.entrySet()) {
            Book key = entry.getKey();
            Integer value = entry.getValue();
            sum +=value* key.getPrice();
        }
            return sum;
    }
}