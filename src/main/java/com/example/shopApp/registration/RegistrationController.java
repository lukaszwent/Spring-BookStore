package com.example.shopApp.registration;

import com.example.shopApp.shoppingCart.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(path = "registration")
@AllArgsConstructor
public class RegistrationController {

    private final ShoppingCartService cartService;
    private final RegistrationService registrationService;

    @PostMapping(consumes = {"application/x-www-form-urlencoded"} )
    public void register(RegistrationRequest request, HttpServletResponse response) throws IOException {
        registrationService.register(request);
        response.sendRedirect("/books");
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("User",new RegistrationRequest("","","",""));
        model.addAttribute("cart",cartService.getNumberOfItems());
        return "register";
    }

}
