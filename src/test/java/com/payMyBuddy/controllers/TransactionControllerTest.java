package com.payMyBuddy.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.TransactionLabel;
import com.payMyBuddy.services.TransactionLabelService;
import com.payMyBuddy.services.TransactionService;
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
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService mockTransactionService;
    @MockBean
    private TransactionLabelService mockTransactionLabelService;

    @Test
    void testCreateTransaction() throws Exception {
        // Setup
        final Optional<Transaction> transaction = Optional.of(new Transaction(new BigDecimal("0.00"), LocalDate.of(2020, 1, 1), new BigDecimal("0.00"), new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", 100);
        final MockHttpServletResponse response = mockMvc.perform(post("/transaction")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        System.out.println(jsonObject);

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Created");
        verify(mockTransactionService).createTransaction(any(Transaction.class));
    }
//
//    @Test
//    void testCreateTransaction() throws Exception {
//        // Setup
//        Transaction transaction = new Transaction(new BigDecimal("0.00"), LocalDate.of(2020, 1, 1), new BigDecimal("0.00"), new TransactionLabel("description", List.of()));
//        final Iterable<Transaction> transactionIterable = List.of(transaction);
//        when(mockTransactionService.getAllTransactions()).thenReturn(transactionIterable);
//
//        // Run the test
//        String jsonObject = new Gson().toJson(transaction);
//        final MockHttpServletResponse response = mockMvc.perform(post("/transaction")
//                        .content(jsonObject).contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andReturn().getResponse();
//        System.out.println(jsonObject);
//
//        // Verify the results
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
////        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
////        verify(mockTransactionService).createTransaction(any(Transaction.class));
//    }

    @Test
    void testGetTransactionById() throws Exception {
        // Setup
        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(new BigDecimal("0.00"), LocalDate.of(2020, 1, 1), new BigDecimal("0.00"), new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction")
                        .param("transactionId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction found");
    }

    @Test
    void testGetTransaction_ReturnBadRequest() throws Exception {
        // Setup
        when(mockTransactionService.getTransactionById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction")
                        .param("transactionId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testUpdateTransaction() throws Exception {
        // Setup
        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(new BigDecimal("0.00"), LocalDate.of(2020, 1, 1), new BigDecimal("0.00"), new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("amount", 1500);
        jsonObject.addProperty("date", 2020-3-29);
        jsonObject.addProperty("commission", 20);
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction/{transactionId}", 1)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction updated");
        verify(mockTransactionService).updateTransaction(any(Transaction.class));
    }

    @Test
    void testUpdateTransaction_ReturnBadRequest() throws Exception {
        // Setup
        final Optional<Transaction> transaction = Optional.of(new Transaction(new BigDecimal("0.00"), LocalDate.of(2020, 1, 1), new BigDecimal("0.00"), new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);

        //Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction/{transactionId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testDeleteTransaction() throws Exception {
        // Setup
        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(new BigDecimal("0.00"), LocalDate.of(2020, 1, 1), new BigDecimal("0.00"), new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/transaction/{transactionId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Successful Operation");
        verify(mockTransactionService).deleteTransaction(any(Transaction.class));
    }

    @Test
    void testDeleteTransaction_ReturnBadRequest() throws Exception {
        // Setup
        final Optional<Transaction> transaction = Optional.of(new Transaction(new BigDecimal("0.00"), LocalDate.of(2020, 1, 1), new BigDecimal("0.00"), new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/transaction/{transactionId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
