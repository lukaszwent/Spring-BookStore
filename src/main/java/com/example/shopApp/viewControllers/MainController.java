package com.example.shopApp.viewControllers;


import com.example.shopApp.registration.RegistrationRequest;
import com.example.shopApp.shoppingCart.ShoppingCartService;
import com.example.shopApp.transaction.TransactionService;
import com.example.shopApp.user.User;

import com.example.shopApp.user.UserRepository;
import com.example.shopApp.user.UserService;
import lombok.AllArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class MainController {

    private final ShoppingCartService cartService;
    private final TransactionService transactionService;
    private final UserService userService;
    @RequestMapping("/")
    @GetMapping
    public void home(HttpServletResponse response) throws IOException {
        response.sendRedirect("/books");
    }


    @RequestMapping("/profile")
    @GetMapping
    public String userMenu(Model model){
        User userDetails =
                (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("Transactions",transactionService.getAllUserTransactions());
        model.addAttribute("User",userDetails);
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "profile";
    }


}
