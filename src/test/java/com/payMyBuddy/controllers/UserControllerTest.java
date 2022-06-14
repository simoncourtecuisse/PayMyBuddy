package com.payMyBuddy.controllers;

import com.google.gson.JsonObject;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import com.payMyBuddy.security.jwt.AuthEntryPointJwt;
import com.payMyBuddy.security.jwt.JwtUtils;
import com.payMyBuddy.security.services.UserDetailsServiceImpl;
import com.payMyBuddy.services.BankAccountService;
import com.payMyBuddy.services.TransactionService;
import com.payMyBuddy.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;
    @MockBean
    private BankAccountService mockBankAccountService;
    @MockBean
    private TransactionService mockTransactionService;
    @MockBean
    private BankAccountRepository mockBankAccountRepository;
    @MockBean
    private UserDetailsServiceImpl mockUserDetailsServiceImpl;
    @MockBean
    private AuthEntryPointJwt mockAuthEntryPointJwt;
    @MockBean
    private JwtUtils mockJwtUtils;

    @Test
    void testCreateUser() throws Exception {
        // Setup
        // Configure UserService.getUserByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", "John");
        jsonObject.addProperty("lastName", "Smith");
        jsonObject.addProperty("email", "john.smith@example.com");
        jsonObject.addProperty("password", "df1s6d21");
        final MockHttpServletResponse response = mockMvc.perform(post("/user")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("User Created", response.getContentAsString());
        verify(mockUserService).createUser(any(User.class));
    }

    @Test
    void testCreateUser_UserServiceGetUserByEmailReturnsBadRequest() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", "email");
        final MockHttpServletResponse response = mockMvc.perform(post("/user")
                        //.param("email", "email")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetUserByEmail() throws Exception {
        // Setup
        // Configure UserService.getUserByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user")
                        .param("email", "email")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("User found", response.getContentAsString());
    }

    @Test
    void testGetUserByEmail_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user")
                        .param("email", "email")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetUserById() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"userId\":null,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\",\"password\":\"password\",\"walletBalance\":0,\"bankAccountList\":[],\"bankTransactionsList\":[],\"roles\":[]}", response.getContentAsString());
    }

    @Test
    void testGetUserById_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetWalletBalanceUserById() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/{userId}/walletBalance", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("0", response.getContentAsString());
    }

    @Test
    void testGetWalletBalanceUserById_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/{userId}/walletBalance", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetUserInfoByEmail() throws Exception {
        // Setup
        // Configure UserService.getUserByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/userInfo/{email}", "email")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("{\"userId\":null,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\",\"password\":\"password\",\"walletBalance\":0,\"bankAccountList\":[],\"bankTransactionsList\":[],\"roles\":[]}", response.getContentAsString());
    }

    @Test
    void testGetAll() throws Exception {
        // Setup
        // Configure UserService.getAllUsers(...).
        final List<User> users = List.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getAllUsers()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"userId\":null,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\",\"password\":\"password\",\"walletBalance\":0,\"bankAccountList\":[],\"bankTransactionsList\":[],\"roles\":[]}]", response.getContentAsString());
    }

    @Test
    void testGetAllFriendsByUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/contacts/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetAllFriendsByUser_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/contacts/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetAllUsersForContactSearch() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure UserService.getAllUsers(...).
        final List<User> users = List.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getAllUsers()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/contacts/{userId}/add", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"userId\":null,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\",\"password\":\"password\",\"walletBalance\":0,\"bankAccountList\":[],\"bankTransactionsList\":[],\"roles\":[]}]", response.getContentAsString());
    }

    @Test
    void testGetAllUsersForContactSearch_UserServiceGetUserByIdReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Configure UserService.getAllUsers(...).
        final List<User> users = List.of(
                new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getAllUsers()).thenReturn(users);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/contacts/{userId}/add", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testUpdateUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(1L)).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", "John");
        jsonObject.addProperty("lastName", "Smith");
        jsonObject.addProperty("email", "john.smith@example.com");
        final MockHttpServletResponse response = mockMvc.perform(put("/user/{userId}", 1)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("User updated", response.getContentAsString());
        verify(mockUserService).updateUser(any(User.class));
    }

    @Test
    void testUpdateUser_UserServiceGetUserByIdReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        JsonObject jsonObject = new JsonObject();
        final MockHttpServletResponse response = mockMvc.perform(put("/user/{userId}", 0)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testDeleteUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(1L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Successful Operation", response.getContentAsString());
        verify(mockUserService).deleteUser(any(User.class));
    }

    @Test
    void testDeleteUser_UserServiceGetUserByIdReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testAddFriend() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure UserService.getUserByEmail(...).
        final Optional<User> user1 = Optional.of(new User("John", "Smith", "JohnEmail", "password"));
        when(mockUserService.getUserByEmail("JohnEmail")).thenReturn(user1);

        final List<User> users = List.of(user.get(), user1.get());
        when(mockUserService.getAllUsers()).thenReturn(users);

        user.get().setFriendList(Collections.emptyList());
        System.out.println(user.get().getFriendList());

        mockUserService.addFriend(user.get(), user1.get());
        mockUserService.addFriend(user1.get(), user.get());
        user.get().setFriendList(user1.stream().collect(Collectors.toList()));
        user1.get().setFriendList(user.stream().collect(Collectors.toList()));


        // Run the test
        JsonObject jsonObject = new JsonObject();

//        jsonObject.addProperty("friendList", userFriendList.toString());
        var userFriendList = user.get().getFriendList();
        System.out.println(userFriendList);
        jsonObject.getAsJsonArray(userFriendList.toString());

        final MockHttpServletResponse response = mockMvc.perform(put("/user/contacts/{userId}/addFriend", 0)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).addFriend(any(User.class), any(User.class));
    }

    @Test
    void testAddFriend_UserServiceGetAllUsersReturnsNotFound() throws Exception {
        // Setup
        when(mockUserService.getAllUsers()).thenReturn(Collections.emptyList());
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        JsonObject jsonObject = new JsonObject();
        final MockHttpServletResponse response = mockMvc.perform(put("/user/contacts/{userId}/addFriend", 0)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User doesn't exist in DB", response.getContentAsString());
    }

    @Test
    void testAddFriend_UserServiceGetUserByIdReturnsBadRequest() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure UserService.getUserByEmail(...).
        final Optional<User> user1 = Optional.of(new User("John", "Smith", "JohnEmail", "password"));
        when(mockUserService.getUserByEmail("JohnEmail")).thenReturn(user1);
        user.get().setFriendList(user1.stream().collect(Collectors.toList()));

        // Run the test
        JsonObject jsonObject = new JsonObject();
        final MockHttpServletResponse response = mockMvc.perform(put("/user/contacts/{userId}/addFriend", 0)
                        .content(user1.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
        //verify(mockUserService).addFriend(any(User.class), any(User.class));
    }

    @Test
    void testRemoveFriend() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure UserService.getUserById(...).
        final Optional<User> user1 = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(1L)).thenReturn(user1);

        user.get().setFriendList(user1.stream().collect(Collectors.toList()));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        delete("/user/contacts/{userId}/removeFriend/{friendUserId}", 0, 1)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"userId\":null,\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\",\"password\":\"password\",\"walletBalance\":0,\"bankAccountList\":[],\"bankTransactionsList\":[],\"roles\":[]}]", response.getContentAsString());
        verify(mockUserService).removeFriend(user.get(), user1.get());
    }

    @Test
    void testRemoveFriend_UserServiceGetUserByIdReturnsNotFound() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        delete("/user/contacts/{userId}/removeFriend/{friendUserId}", 0, 0)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User doesn't exist in DB", response.getContentAsString());
    }

    @Test
    void testRemoveFriend_UserServiceGetUserByIdReturnsBadRequest() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure UserService.getUserById(...).
        final Optional<User> user1 = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(1L)).thenReturn(user1);

        user.get().setFriendList(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        delete("/user/contacts/{userId}/removeFriend/{friendUserId}", 0, 0)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }
}
