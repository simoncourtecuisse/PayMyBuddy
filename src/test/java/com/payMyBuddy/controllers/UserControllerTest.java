package com.payMyBuddy.controllers;

import com.google.gson.JsonObject;
import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void testCreateUser() throws Exception {
        // Setup
        // Configure UserService.getUserByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", "Simon");
        jsonObject.addProperty("lastName", "Courtecuisse");
        jsonObject.addProperty("email", "simon.courtecuisse@example.com");
        final MockHttpServletResponse response = mockMvc.perform(post("/user")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("User Created");
        verify(mockUserService).createUser(any(User.class));
    }

    @Test
    void testCreateUser_ReturnBadRequest() throws Exception {
        // Setup
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user")
                        .param("email", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testGetUserByEmail() throws Exception {
        // Setup
        // Configure UserService.getUserByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user")
                        .param("email", "email")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("User found");
    }

    @Test
    void testGetUserByEmail_BadRequest() throws Exception {
        // Setup
        // Configure UserService.getUserByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserByEmail("email")).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user")
                        .param("email", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        //assertThat(response.getContentAsString()).isEqualTo("bad request");
    }

    @Test
    void testUpdateUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(1L)).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", "Igor");
        jsonObject.addProperty("lastName", "Courtecuisse");
        jsonObject.addProperty("email", "igor.courtecuisse@example.com");
        final MockHttpServletResponse response = mockMvc.perform(put("/user/{userId}", 1)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("User updated");
        verify(mockUserService).updateUser(any(User.class));
    }

    @Test
    void testUpdateUser_BadRequest() throws Exception {
        // Setup
        // Configure UserService.getUserByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(1L)).thenReturn(Optional.empty());

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("firstName", "Igor");
        jsonObject.addProperty("lastName", "Courtecuisse");
        jsonObject.addProperty("email", "igor.courtecuisse@example.com");
        final MockHttpServletResponse response = mockMvc.perform(put("/user/{userId}", 1)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        //assertThat(response.getContentAsString()).isEqualTo("bad request");
    }

    @Test
    void testDeleteUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(1L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/{userId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Successful Operation");
        verify(mockUserService).deleteUser(any(User.class));
    }

    @Test
    void testAddFriend() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/addFriend")
                        .param("fromUser", "0")
                        .param("toUser", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Friend Added");
        verify(mockUserService).addFriend(any(User.class), any(User.class));
    }

    @Test
    void testAddFriend_ReturnNotFound() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/addFriend")
                        .param("fromUser", "0")
                        .param("toUser", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("User doesn't exist in DB");
    }

    @Test
    void testRemoveFriend() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/removeFriend")
                        .param("fromUser", "0")
                        .param("toUser", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).removeFriend(any(User.class), any(User.class));
    }

    @Test
    void testRemoveFriend_ReturnNotFound() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/removeFriend")
                        .param("fromUser", "0")
                        .param("toUser", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("User doesn't exist in DB");
    }

    @Test
    void testAddBankAccount() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.of(new BankAccount(0, "bankName")));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/addBankAccount")
                        .param("userId", "0")
                        .param("bankAccountId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Bank Account Added");
        verify(mockUserService).addBankAccount(any(User.class), any(BankAccount.class));
    }

    @Test
    void testAddBankAccount_ReturnNotFound() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.of(new BankAccount(0, "bankName")));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/addBankAccount")
                        .param("userId", "0")
                        .param("bankAccountId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("User doesn't exist in DB");
    }

    @Test
    void testRemoveBankAccount() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.of(new BankAccount(0, "bankName")));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/removeBankAccount")
                        .param("userId", "0")
                        .param("bankAccountId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).removeBankAccount(any(User.class), any(BankAccount.class));
    }

    @Test
    void testAddCreditorToTransaction() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/addCreditorToTransaction")
                        .param("creditorId", "0")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Creditor Added to Transaction");
        verify(mockUserService).addCreditorToTransaction(any(User.class), any(Transaction.class));
    }

    @Test
    void testRemoveCreditorToTransaction() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/removeCreditorToTransaction")
                        .param("creditorId", "0")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).removeCreditorToTransaction(any(User.class), any(Transaction.class));
    }

    @Test
    void testAddDebtorToTransaction() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/addDebtorToTransaction")
                        .param("debtorId", "0")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Debtor Added to Transaction");
        verify(mockUserService).addDebtorToTransaction(any(User.class), any(Transaction.class));
    }

    @Test
    void testRemoveDebtorToTransaction() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/user/removeDebtorToTransaction")
                        .param("debtorId", "0")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).removeDebtorToTransaction(any(User.class), any(Transaction.class));
    }
}
