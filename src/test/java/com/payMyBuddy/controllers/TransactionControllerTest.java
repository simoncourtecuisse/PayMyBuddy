package com.payMyBuddy.controllers;

import com.google.gson.JsonObject;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.security.jwt.AuthEntryPointJwt;
import com.payMyBuddy.security.jwt.JwtUtils;
import com.payMyBuddy.security.services.UserDetailsServiceImpl;
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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService mockTransactionService;
    @MockBean
    private UserService mockUserService;
    @MockBean
    private UserDetailsServiceImpl mockUserDetailsServiceImpl;
    @MockBean
    private AuthEntryPointJwt mockAuthEntryPointJwt;
    @MockBean
    private JwtUtils mockJwtUtils;

    @Test
    void testCreateTransaction() throws Exception {
        // Setup
        final Optional<Transaction> transaction = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", 0.0);
        jsonObject.addProperty("description", "description");
        final MockHttpServletResponse response = mockMvc.perform(post("/transaction")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Transaction Created", response.getContentAsString());
        verify(mockTransactionService).createTransaction(any(Transaction.class));
    }

    @Test
    void testGetTransactionById() throws Exception {
        // Setup
        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Transaction found", response.getContentAsString());
    }

    @Test
    void testGetTransactionById_TransactionServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockTransactionService.getTransactionById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetAllTransactions() throws Exception {
        // Setup
        // Configure TransactionService.getAllTransactions(...).
        final Iterable<Transaction> transactions = List.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getAllTransactions()).thenReturn(transactions);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction/transfers")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"transactionId\":null,\"amount\":0.0,\"date\":\"01/01/2020\",\"commission\":0.0,\"description\":\"description\",\"creditor\":null,\"debtor\":null}]", response.getContentAsString());
    }

    @Test
    void testGetAllTransactionsByUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TransactionService.getAllTransactionsByUser(...).
        final List<Transaction> transactions = List.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getAllTransactionsByUser(any(User.class))).thenReturn(transactions);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction/transfers/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"transactionId\":null,\"amount\":0.0,\"date\":\"01/01/2020\",\"commission\":0.0,\"description\":\"description\",\"creditor\":null,\"debtor\":null}]", response.getContentAsString());
    }

    @Test
    void testGetAllTransactionsByUser_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Configure TransactionService.getAllTransactionsByUser(...).
        final List<Transaction> transactions = List.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getAllTransactionsByUser(any(User.class))).thenReturn(transactions);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction/transfers/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testUpdateTransaction() throws Exception {
        // Setup
        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", 1.0);
        jsonObject.addProperty("description", "description");
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction/{transactionId}", 0)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Transaction updated", response.getContentAsString());
        verify(mockTransactionService).updateTransaction(any(Transaction.class));
    }

    @Test
    void testUpdateTransaction_TransactionServiceGetTransactionByIdReturnsBadRequest() throws Exception {
        // Setup
        when(mockTransactionService.getTransactionById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction/{transactionId}", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testDeleteTransaction() throws Exception {
        // Setup
        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/transaction/{transactionId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Successful Operation", response.getContentAsString());
        verify(mockTransactionService).deleteTransaction(any(Transaction.class));
    }

    @Test
    void testDeleteTransaction_TransactionServiceGetTransactionByIdReturnsBadRequest() throws Exception {
        // Setup
        when(mockTransactionService.getTransactionById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/transaction/{transactionId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testMakeTransaction() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TransactionService.createTransaction(...).
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionService.createTransaction(any(User.class), any(User.class), eq(0.0),
                eq("description"))).thenReturn(transaction);

//        when(mockTransactionService.processUserTransaction(any(Transaction.class))).thenReturn(true);
        when(mockTransactionService.processUserTransaction(transaction)).thenReturn(true);
        mockTransactionService.saveTransaction(transaction);

        // Configure TransactionService.getAllTransactionsByUser(...).
        final List<Transaction> transactions = List.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getAllTransactionsByUser(any(User.class))).thenReturn(transactions);

        // Run the test

        final MockHttpServletResponse response = mockMvc.perform(post("/transaction/transfers/{userId}/payment", 0)
                        .content(String.valueOf(transactions)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockTransactionService).saveTransaction(any(Transaction.class));
    }

    @Test
    void testMakeTransaction_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Configure TransactionService.createTransaction(...).
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionService.createTransaction(any(User.class), any(User.class), eq(0.0),
                eq("description"))).thenReturn(transaction);

        when(mockTransactionService.processUserTransaction(any(Transaction.class))).thenReturn(false);

        // Configure TransactionService.getAllTransactionsByUser(...).
        final List<Transaction> transactions = List.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getAllTransactionsByUser(any(User.class))).thenReturn(transactions);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/transaction/transfers/{userId}/payment", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testMakeTransaction_TransactionServiceGetAllTransactionsByUserReturnsNotFound() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure TransactionService.createTransaction(...).
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionService.createTransaction(any(User.class), any(User.class), eq(0.0),
                eq("description"))).thenReturn(transaction);

        when(mockTransactionService.processUserTransaction(any(Transaction.class))).thenReturn(false);
        when(mockTransactionService.getAllTransactionsByUser(any(User.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/transaction/transfers/{userId}/payment", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }
}
