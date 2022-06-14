package com.payMyBuddy.controllers;

import com.google.gson.JsonObject;
import com.payMyBuddy.models.ERole;
import com.payMyBuddy.models.Role;
import com.payMyBuddy.models.User;
import com.payMyBuddy.payload.request.LoginRequest;
import com.payMyBuddy.payload.request.SignupRequest;
import com.payMyBuddy.payload.response.JwtResponse;
import com.payMyBuddy.repositories.RoleRepository;
import com.payMyBuddy.repositories.UserRepository;
import com.payMyBuddy.security.jwt.AuthEntryPointJwt;
import com.payMyBuddy.security.jwt.JwtUtils;
import com.payMyBuddy.security.services.UserDetailsImpl;
import com.payMyBuddy.security.services.UserDetailsServiceImpl;
import com.payMyBuddy.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager mockAuthenticationManager;
    @MockBean
    private UserService mockUserService;
    @MockBean
    private UserRepository mockUserRepository;
    @MockBean
    private RoleRepository mockRoleRepository;
    @MockBean
    private PasswordEncoder mockEncoder;
    @MockBean
    private UserDetailsServiceImpl mockUserDetailsServiceImpl;
    @MockBean
    private AuthEntryPointJwt mockAuthEntryPointJwt;
    @MockBean
    private JwtUtils mockJwtUtils;
    @MockBean
    private UserDetailsImpl mockUserDetailsImpl;

    @Test
    void testAuthenticateUser() throws Exception {
        // Setup
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("email");
        loginRequest.setPassword("password");


        Optional<UserDetailsImpl> userDetails = Optional.of(new UserDetailsImpl(0L, "email", "password", List.of()));
        Authentication authentication = mockAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        //when(mockAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()))).thenReturn(authentif);
        System.out.println(authentication);
        when(mockJwtUtils.generateJwtToken(authentication)).thenReturn("accessToken");


        var roles = userDetails.get().getAuthorities();
        final Optional<JwtResponse> jwtResponse = Optional.of(new JwtResponse("jwt", 0L, "email", Collections.singletonList("roles")));

        // Run the test

        final MockHttpServletResponse response = mockMvc.perform(post("/auth/signin")
                        .content(jwtResponse.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testAuthenticateUser_AuthenticationManagerThrowsAuthenticationException() throws Exception {
        // Setup
        when(mockAuthenticationManager.authenticate(null)).thenThrow(AuthenticationException.class);
        when(mockJwtUtils.generateJwtToken(null)).thenReturn("accessToken");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/auth/signin")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testRegisterUser() throws Exception {
        // Setup
        when(mockUserRepository.existsByEmail("email")).thenReturn(false);
        when(mockEncoder.encode("password")).thenReturn("password");
        when(mockRoleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(
                ERole.ROLE_USER)));

        // Configure UserRepository.save(...).
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", "John");
        jsonObject.addProperty("lastName", "Smith");
        jsonObject.addProperty("email", "john.smith@example.com");
        final MockHttpServletResponse response = mockMvc.perform(post("/auth/signup")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"message\":\"User registered successfully!\"}", response.getContentAsString());
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testRegisterUserReturnsRunTimeException() throws Exception {
        // Setup
        when(mockUserRepository.existsByEmail("email")).thenReturn(false);
        when(mockEncoder.encode("password")).thenReturn("password");
        when(mockRoleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(new Role(
                ERole.ROLE_ADMIN)));

        // Configure UserRepository.save(...).
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);


        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", "John");
        jsonObject.addProperty("lastName", "Smith");
        jsonObject.addProperty("email", "john.smith@example.com");
        final MockHttpServletResponse response = mockMvc.perform(post("/auth/signup")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"message\":\"User registered successfully!\"}", response.getContentAsString());
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testRegisterUser_RepositoryReturnsBadRequest() throws Exception {
        // Setup
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("email");
        when(mockUserRepository.existsByEmail("email")).thenReturn(true);
        when(mockEncoder.encode("password")).thenReturn("password");
        when(mockRoleRepository.findByName(ERole.ROLE_USER)).thenReturn(Optional.empty());

        // Configure UserRepository.save(...).
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", "email");
        final MockHttpServletResponse response = mockMvc.perform(post("/auth/signup")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"message\":\"Error: Email is already used!\"}", response.getContentAsString());
    }
}
