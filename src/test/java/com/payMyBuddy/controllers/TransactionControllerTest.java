package com.payMyBuddy.controllers;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
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
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void testCreateTransaction() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/transaction")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
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
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetTransactionById_TransactionServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockTransactionService.getTransactionById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllTransactionsByUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(
                new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
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
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllTransactionsByUser_UserServiceReturnsAbsent() throws Exception {
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
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testGetAllTransactionsByUser_TransactionServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(
                new User("firstName", "lastName", "email", "password", new BigDecimal("0.00"), List.of()));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        when(mockTransactionService.getAllTransactionsByUser(any(User.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction/transfers/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdateTransaction() throws Exception {
        // Setup
        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction/{transactionId}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockTransactionService).updateTransaction(any(Transaction.class));
    }

    @Test
    void testUpdateTransaction_TransactionServiceGetTransactionByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockTransactionService.getTransactionById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction/{transactionId}", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockTransactionService).updateTransaction(any(Transaction.class));
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
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockTransactionService).deleteTransaction(any(Transaction.class));
    }

    @Test
    void testDeleteTransaction_TransactionServiceGetTransactionByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockTransactionService.getTransactionById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/transaction/{transactionId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockTransactionService).deleteTransaction(any(Transaction.class));
    }
}
