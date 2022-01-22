package com.example.shopApp.registration;

import com.example.shopApp.user.User;
import com.example.shopApp.user.UserRole;
import com.example.shopApp.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public void register(RegistrationRequest request) {

        userService.signUp(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.USER
                ));

    }
}