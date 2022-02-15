package com.payMyBuddy.controllers;

import com.google.gson.JsonObject;
import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.services.BankAccountService;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService mockBankAccountService;

    @Test
    void testCreateBankAccount() throws Exception {
        // Setup
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(1));
        when(mockBankAccountService.getBankAccountById(1L)).thenReturn(bankAccount);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("iban", "1");
        final MockHttpServletResponse response = mockMvc.perform(post("/bankAccount")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("Bank Account Created");
        verify(mockBankAccountService).addBankAccount(any(BankAccount.class));
    }

    @Test
    void testGetBankAccount() throws Exception {
        // Setup
        when(mockBankAccountService.getBankAccountById(1L)).thenReturn(Optional.of(new BankAccount(1)));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/bankAccount")
                        .param("bankAccountId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Bank Account found");
    }

    @Test
    void testGetBankAccount_ReturnBadRequest() throws Exception {
        // Setup
        when(mockBankAccountService.getBankAccountById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/bankAccount")
                        .param("bankAccountId", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testUpdateBankAccount() throws Exception {
        // Setup
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(1423));
        when(mockBankAccountService.getBankAccountById(1L)).thenReturn(bankAccount);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("iban", 1423);
        final MockHttpServletResponse response = mockMvc.perform(put("/bankAccount/{bankAccountId}", 1)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Bank Account updated");
        verify(mockBankAccountService).updateBankAccount(any(BankAccount.class));
    }

    @Test
    void testUpdateBankAccount_ReturnBadRequest() throws Exception {
        // Setup
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(1423));
        when(mockBankAccountService.getBankAccountById(1L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/bankAccount/{bankAccountId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testDeleteBankAccount() throws Exception {
        // Setup
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(1423));
        when(mockBankAccountService.getBankAccountById(1L)).thenReturn(bankAccount);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/bankAccount/{bankAccountId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("Successful Operation");
        verify(mockBankAccountService).deleteBankAccount(any(BankAccount.class));
    }

    @Test
    void testDeleteBankAccount_ReturnBadRequest() throws Exception {
        // Setup
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/bankAccount/{bankAccountId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }
}
