package com.payMyBuddy.controllers;

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
@WebMvcTest(TransactionLabelController.class)
class TransactionLabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionLabelService mockTransactionLabelService;
    @MockBean
    private TransactionService mockTransactionService;

    @Test
    void testCreateTransactionLabel() throws Exception {
        // Setup
        // Run the test
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(transactionLabel);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("description", "add description");
        final MockHttpServletResponse response = mockMvc.perform(post("/transaction_label", 1)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Label Created");
        verify(mockTransactionLabelService).createTransactionLabel(any(TransactionLabel.class));
    }

    @Test
    void testGetTransactionLabel() throws Exception {
        // Setup
        // Configure TransactionLabelService.getTransactionLabelById(...).
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(transactionLabel);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction_label")
                        .param("transactionLabelId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Label found");
    }

    @Test
    void testGetTransactionLabel_ReturnBadRequest() throws Exception {
        // Setup
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/transaction_label")
                        .param("transactionLabelId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testUpdateTransactionLabel() throws Exception {
        // Setup
        // Configure TransactionLabelService.getTransactionLabelById(...).
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(transactionLabel);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("description", "add description");
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction_label{transactionLabelId}", 1)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Label updated");
        verify(mockTransactionLabelService).updateTransactionLabel(any(TransactionLabel.class));
    }

    @Test
    void testUpdateTransactionLabel_ReturnBadRequest() throws Exception {
        // Setup
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/transaction_label{transactionLabelId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testDeleteTransactionLabel() throws Exception {
        // Setup
        // Configure TransactionLabelService.getTransactionLabelById(...).
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(transactionLabel);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/transaction_label/{transactionLabelId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Successful Operation");
        verify(mockTransactionLabelService).deleteTransactionLabel(any(TransactionLabel.class));
    }

    @Test
    void testDeleteTransactionLabel_ReturnBadRequest() throws Exception {
        // Setup
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/transaction_label/{transactionLabelId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testAddTransactionLabel() throws Exception {
        // Setup
        // Configure TransactionLabelService.getTransactionLabelById(...).
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(transactionLabel);

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/addTransactionLabel")
                        .param("transactionLabelId", "1")
                        .param("transactionId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Added");
        verify(mockTransactionLabelService).addTransactionLabel(any(TransactionLabel.class), any(Transaction.class));
    }

    @Test
    void testAddTransactionLabel_ReturnNotFound() throws Exception {
        // Setup
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(Optional.empty());

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/addTransactionLabel")
                        .param("transactionLabelId", "0")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Label doesn't exist in DB");
    }

    @Test
    void testAddTransactionLabel_ReturnBadRequest() throws Exception {
        // Setup
        // Configure TransactionLabelService.getTransactionLabelById(...).
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(transactionLabel);

        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(1L)).thenReturn(transaction);


        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/addTransactionLabel",1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testRemoveTransactionLabel() throws Exception {
        // Setup
        // Configure TransactionLabelService.getTransactionLabelById(...).
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(0L)).thenReturn(transactionLabel);

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/removeTransactionLabel")
                        .param("transactionLabelId", "0")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Removed");
        verify(mockTransactionLabelService).removeTransactionLabel(any(TransactionLabel.class), any(Transaction.class));
    }

    @Test
    void testRemoveTransactionLabel_ReturnNotFound() throws Exception {
        // Setup
        when(mockTransactionLabelService.getTransactionLabelById(0L)).thenReturn(Optional.empty());

        // Configure TransactionService.getTransactionById(...).
        final Optional<Transaction> transaction = Optional.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, new TransactionLabel("description", List.of())));
        when(mockTransactionService.getTransactionById(0L)).thenReturn(transaction);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/removeTransactionLabel")
                        .param("transactionLabelId", "0")
                        .param("transactionId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("Transaction Label doesn't exist in DB");
    }

    @Test
    void testRemoveTransactionLabel_ReturnBadRequest() throws Exception {
        // Setup
        // Configure TransactionLabelService.getTransactionLabelById(...).
        final Optional<TransactionLabel> transactionLabel = Optional.of(new TransactionLabel("description", List.of(new Transaction(0, LocalDate.of(2020, 1, 1), 0, null))));
        when(mockTransactionLabelService.getTransactionLabelById(1L)).thenReturn(transactionLabel);

        when(mockTransactionService.getTransactionById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/removeTransactionLabel")
                        .param("transactionLabelId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
