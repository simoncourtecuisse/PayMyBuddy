package com.payMyBuddy.controllers;

import com.payMyBuddy.models.ERole;
import com.payMyBuddy.models.Role;
import com.payMyBuddy.models.User;
import com.payMyBuddy.payload.request.LoginRequest;
import com.payMyBuddy.payload.request.SignupRequest;
import com.payMyBuddy.payload.response.JwtResponse;
import com.payMyBuddy.payload.response.MessageResponse;
import com.payMyBuddy.repositories.RoleRepository;
import com.payMyBuddy.repositories.UserRepository;
import com.payMyBuddy.security.jwt.JwtUtils;
import com.payMyBuddy.security.services.UserDetailsImpl;
import com.payMyBuddy.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    Logger LOGGER = LogManager.getLogger(AuthController.class);

    //@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
       // var rawPassword =  loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already used!"));
        }
        // Create new user's account
        User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role)) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

//    @PostMapping
//    public ResponseEntity<?> createUser(@RequestBody User user) {
//        if (userService.getUserByEmail(user.getEmail()).isEmpty()) {
//            userService.createUser(user);
//            LOGGER.info("User {} created successfully", user.getEmail());
//            return new ResponseEntity<>("User Created", HttpStatus.OK);
//        }
//        return ResponseEntity.badRequest().build();
//    }
//
//    @PostMapping("/authenticate")
//    public ResponseEntity<?> authenticate(@RequestBody User user) {
//        if(userService.getUserByEmail(user.getEmail()).isPresent()) {
//            User authenticatedUser = userService.getUserByEmail(user.getEmail()).get();
//            LOGGER.info("Authentication success");
//            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
//        } else {
//            LOGGER.error("Authentication failed");
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }
}
