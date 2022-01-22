package com.example.shopApp.viewControllers;

import com.example.shopApp.shoppingCart.ShoppingCartService;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
public class LoginController {

    private final ShoppingCartService cartService;

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("cart",cartService.getNumberOfItems());
        model.addAttribute("loginError", true);
        return "login";
    }


}