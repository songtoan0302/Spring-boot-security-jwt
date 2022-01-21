package org.ptit.service;

import java.util.ArrayList;
import java.util.Objects;

import org.ptit.model.User;
import org.ptit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceIml implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        if (Objects.nonNull(user)) {
            BCryptPasswordEncoder encoderPassword = new BCryptPasswordEncoder(10);
            User userCreate = new User();
            userCreate.setUsername(user.getUsername());
            userCreate.setPassword(encoderPassword.encode(user.getPassword()));
            User response= userRepository.save(userCreate);
    return response;

        }else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user= userRepository.findById(username).orElseThrow(()->{throw new UsernameNotFoundException("Username Not Found Exception");});
    return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),new ArrayList<>());
        }
}