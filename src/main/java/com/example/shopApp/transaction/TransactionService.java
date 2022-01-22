package com.example.shopApp.transaction;

import com.example.shopApp.books.Book;
import com.example.shopApp.books.BookRepository;
import com.example.shopApp.shoppingCart.ShoppingCart;
import com.example.shopApp.user.User;
import com.example.shopApp.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    List<Transaction> getUserTransactions(int id,Pageable page){
        return transactionRepository.findAllById(id,page);
    }

    public void purchaseBooks(List<Integer> bookList, ShoppingCart shoppingCart){

        String email = (String) SecurityContextHolder.getContext().getAuthentication().getName();

        if(email != "anonymousUser"){
            User user = userRepository.findByEmail(email).get();

            for(int i=0;i< bookList.size(); i++){

                Book b = bookRepository.findById(bookList.get(i)).get();
                b.setSoldTimes(b.getSoldTimes()+1);
                Transaction transaction = new Transaction(user,b,shoppingCart.getCountry(),shoppingCart.getState(),shoppingCart.getStreet(),b.getPrice() - ((b.getPrice()*(b.getPercentOfPromotion()))/100));
                transactionRepository.save(transaction);
                b.setQuantity(b.getQuantity()-1);
                bookRepository.save(b);
            }

        }

    }

    public List<Transaction> getAllUserTransactions(){
        return transactionRepository.findAllByUser((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
