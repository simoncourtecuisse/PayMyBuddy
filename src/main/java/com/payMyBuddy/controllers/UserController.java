package com.payMyBuddy.controllers;


import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import com.payMyBuddy.services.BankAccountService;
import com.payMyBuddy.services.TransactionService;
import com.payMyBuddy.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200/", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    Logger LOGGER = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.getUserByEmail(user.getEmail()).isEmpty()) {
            userService.createUser(user);
            LOGGER.info("User {} created successfully", user.getEmail());
            return new ResponseEntity<>("User Created", HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }


    @GetMapping
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isEmpty()) {
            LOGGER.error("Can't find the user based on this email");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        LOGGER.info("Success find user by email");
        return new ResponseEntity<>("User found", HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            LOGGER.info("Success find user by id");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        LOGGER.error("Can't find the user based on this id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{userId}/walletBalance")
    public ResponseEntity<?> getWalletBalanceUserById(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            System.out.println(user.getWalletBalance());
            LOGGER.info("Success find user's walletBalance");
            return new ResponseEntity<>(user.getWalletBalance(), HttpStatus.OK);
        }
        LOGGER.error("Can't find the user based on this id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/userInfo/{email}")
    public ResponseEntity<?> getUserInfoByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email).get();
        System.out.println(user);
        LOGGER.info("Success find user by email");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAll() {
        LOGGER.info("Success find all users");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/contacts/{userId}")
    public ResponseEntity<?> getAllFriendsByUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            LOGGER.info("Success find user's friend");
            return new ResponseEntity<>(user.getFriendList(), HttpStatus.OK);
        }
        LOGGER.error("Can't find the user based on this id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/contacts/{userId}/add")
    public ResponseEntity<?> getAllUsersForContactSearch(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            List<User> forContactSearch = userService.getAllUsers()
                    .stream()
                    .filter(i -> !i.equals(user))
                    .collect(Collectors.toList());

            //return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
            LOGGER.info("Friend successfully added");
            return new ResponseEntity<>(forContactSearch, HttpStatus.OK);
        }
        LOGGER.error("Can't find the user based on this id");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Long id, @RequestBody User user) {
        if (userService.getUserById(id).isPresent()) {
            user.setUserId(id);
            userService.updateUser(user);
            LOGGER.info("User updated successfully");
            return new ResponseEntity<>("User updated", HttpStatus.OK);
        }
        LOGGER.error("Failed to update user because the user was not found");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long id) {
        if (userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            userService.deleteUser(user);
            LOGGER.info("User deleted successfully");
            return new ResponseEntity<>("Successful Operation", HttpStatus.OK);
        }
        LOGGER.error("Failed to delete user because of a BAD REQUEST");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/contacts/{userId}/addFriend")
    public ResponseEntity<?> addFriend(@PathVariable("userId") Long fromUser, @RequestBody User friend) {
        System.out.println(userService.getAllUsers().contains(friend));
        var uer = userService.getUserById(fromUser);
        var g = userService.getAllUsers();
        if (userService.getUserById(fromUser).isEmpty() || userService.getAllUsers()
                .stream().noneMatch(i -> i.getEmail().equals(friend.getEmail()))) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserByEmail(friend.getEmail()).get();
        if (!user.getFriendList().contains(friendUser)) {
            userService.addFriend(user, friendUser);
            userService.addFriend(friendUser, user);
            LOGGER.info("Add friend success");
            System.out.println(user.getFriendList());
            return new ResponseEntity<>(user.getFriendList(), HttpStatus.CREATED);
        }
        LOGGER.error("Add friend failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/contacts/{userId}/removeFriend/{friendUserId}")
    public ResponseEntity<?> removeFriend(@PathVariable("userId") Long fromUser, @PathVariable("friendUserId") Long toUser) {
        if (userService.getUserById(fromUser).isEmpty() || userService.getUserById(toUser).isEmpty()) {
            LOGGER.error("User doesn't exist in DB");
            return new ResponseEntity<>("User doesn't exist in DB", HttpStatus.NOT_FOUND);
        }
        User user = userService.getUserById(fromUser).get();
        User friendUser = userService.getUserById(toUser).get();
        if (user.getFriendList().contains(friendUser)) {
            userService.removeFriend(user, friendUser);
            userService.removeFriend(friendUser, user);
            LOGGER.info("Remove friend success");
            System.out.println(user.getFriendList());
            return new ResponseEntity<>(user.getFriendList(), HttpStatus.OK);
        }
        LOGGER.error("Remove friend failed because of a bad request");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}