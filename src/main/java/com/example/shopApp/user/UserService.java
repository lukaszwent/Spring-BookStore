package com.example.shopApp.user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format("com.example.shopApp.user with email %s not found", email)));
    }

    public String signUp(User users){
        boolean userExists = userRepository.findByEmail(users.getEmail()).isPresent();
        if(userExists){
            throw new IllegalStateException("email already taken");
        }

        String hashedPassword =  bCryptPasswordEncoder.encode(users.getPassword());

        users.setPassword(hashedPassword);

        userRepository.save(users);

        return "It WORKKS";
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}

