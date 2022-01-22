package com.example.shopApp.shoppingCart;

import com.example.shopApp.NotEnoughProductsInStockException;
import com.example.shopApp.books.BookRepository;
import com.example.shopApp.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartService cartService;
    private final BookRepository bookRepository;

    @GetMapping("/shoppingCart")
    public String shoppingCart(Model model) {
        model.addAttribute("cart",cartService.getNumberOfItems());
        model.addAttribute("products", shoppingCartService.getProductsInCart());
        model.addAttribute("total", String.valueOf(shoppingCartService.getTotal()));
        return "shoppingCart";
    }

    @GetMapping("/shoppingCart/addProduct/{productId}")
    public void addProductToCart(@PathVariable("productId") int productId, HttpServletResponse response) throws IOException {

        if(SecurityContextHolder.getContext().getAuthentication().getName() !="anonymousUser"){
            bookRepository.findById(productId).ifPresent(shoppingCartService::addProduct);
            response.sendRedirect("/shoppingCart");
        }else {
            response.sendRedirect("/books");
        }

    }

    @GetMapping("/shoppingCart/removeProduct/{productId}")
    public void removeProductFromCart(@PathVariable("productId") int productId,HttpServletResponse response) throws IOException {
        if(SecurityContextHolder.getContext().getAuthentication().getName() !="anonymousUser") {
            bookRepository.findById(productId).ifPresent(shoppingCartService::removeProduct);
            response.sendRedirect("/shoppingCart");
        }else {
            response.sendRedirect("/books");
        }
    }

    @PostMapping(path = "/shoppingCart/checkout", consumes = {"application/x-www-form-urlencoded"})
    public void checkout(ShoppingCart shoppingCart,HttpServletResponse response) throws IOException {
        if (SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser") {
            try {
                shoppingCartService.checkout(shoppingCart);
            } catch (NotEnoughProductsInStockException e) {
                response.sendRedirect("/books");
            }
            response.sendRedirect("/books");
        }
        else {
            response.sendRedirect("/books");
        }
    }
}