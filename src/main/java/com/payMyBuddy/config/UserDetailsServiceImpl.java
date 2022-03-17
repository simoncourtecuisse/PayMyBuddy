//package com.payMyBuddy.config;
//
//import com.payMyBuddy.models.User;
//import com.payMyBuddy.repositories.UserRepository;
//import com.payMyBuddy.services.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email).get();
//        if (user == null) {
//            throw new UsernameNotFoundException("No User with this email: " + email);
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
//    }
//
//    public User save(User user) {
//        User user1 = new User();
//        user1.setEmail(user.getEmail());
//        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        return userRepository.save(user1);
//    }
//}
