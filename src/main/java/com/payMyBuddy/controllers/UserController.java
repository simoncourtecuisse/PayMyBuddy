package com.payMyBuddy.controllers;


import com.payMyBuddy.models.User;
import com.payMyBuddy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>("User Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>("User found", HttpStatus.OK);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        if(userService.getUserByEmail(user.getEmail()).isPresent()) {
            userService.updateUser(user);
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        userService.deleteUser(user);
        return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
    }

    @PutMapping(value = "/addFriend")
    public ResponseEntity<?> addFriend(@RequestParam int fromUser, @RequestParam int toUser) {
        if (userService.getUserById(fromUser).isEmpty() || userService.getUserById(toUser).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserById(toUser).get();
        if (!user.getFriendList().contains(friendUser)) {
            userService.addFriend(user, friendUser);
            return new ResponseEntity<>("Friend Added", HttpStatus.CREATED);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping(value = "/removeFriend")
    public ResponseEntity<?> removeFriend(@RequestParam int fromUser, @RequestParam int toUser) {
        if (userService.getUserById(fromUser).isEmpty() || userService.getUserById(toUser).isEmpty()) {
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.BAD_REQUEST);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserById(toUser).get();
        if (user.getFriendList().contains(friendUser)) {
            userService.removeFriend(user, friendUser);
            return new ResponseEntity<>("Friend Removed", HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


}
