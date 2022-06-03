package com.payMyBuddy.controllers;

import com.google.gson.JsonObject;
import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.BankTransaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.security.jwt.AuthEntryPointJwt;
import com.payMyBuddy.security.jwt.JwtUtils;
import com.payMyBuddy.security.services.UserDetailsServiceImpl;
import com.payMyBuddy.services.BankAccountService;
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
@WebMvcTest(BankAccountController.class)
class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankAccountService mockBankAccountService;
    @MockBean
    private UserService mockUserService;
    @MockBean
    private UserDetailsServiceImpl mockUserDetailsServiceImpl;
    @MockBean
    private AuthEntryPointJwt mockAuthEntryPointJwt;
    @MockBean
    private JwtUtils mockJwtUtils;

    @Test
    void testCreateBankAccount() throws Exception {
        // Setup
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(bankAccount);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("iban", 5132);
        jsonObject.addProperty("bankName", "Revolut");
        final MockHttpServletResponse response = mockMvc.perform(post("/bankAccount")
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("Bank Account Created", response.getContentAsString());
        verify(mockBankAccountService).addBankAccount(any(BankAccount.class));
    }

    @Test
    void testGetBankAccount() throws Exception {
        // Setup
        // Configure BankAccountService.getBankAccountById(...).
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(bankAccount);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/bankAccount")
                        .param("bankAccountId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Bank Account found", response.getContentAsString());
    }

    @Test
    void testGetBankAccount_BankAccountServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/bankAccount")
                        .param("bankAccountId", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testUpdateBankAccount() throws Exception {
        // Setup
        // Configure BankAccountService.getBankAccountById(...).
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(bankAccount);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("iban", 5132);
        jsonObject.addProperty("bankName", "Revolut");
        final MockHttpServletResponse response = mockMvc.perform(put("/bankAccount/{bankAccountId}", 0)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Bank Account updated", response.getContentAsString());
        verify(mockBankAccountService).updateBankAccount(any(BankAccount.class));
    }

    @Test
    void testUpdateBankAccount_BankAccountServiceGetBankAccountByIdReturnsBadRequest() throws Exception {
        // Setup
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/bankAccount/{bankAccountId}", 0)
//                        .content("bankAccountId").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testDeleteBankAccount() throws Exception {
        // Setup
        // Configure BankAccountService.getBankAccountById(...).
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(bankAccount);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("iban", 5132);
        jsonObject.addProperty("bankName", "Revolut");
        final MockHttpServletResponse response = mockMvc.perform(delete("/bankAccount/{bankAccountId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("Successful Operation", response.getContentAsString());
        verify(mockBankAccountService).deleteBankAccount(any(BankAccount.class));
    }

    @Test
    void testDeleteBankAccount_BankAccountServiceGetBankAccountByIdReturnsBadRequest() throws Exception {
        // Setup
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/bankAccount/{bankAccountId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetAllBankAccountByUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/profile/bankAccounts/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testGetAllBankAccountByUser_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/profile/bankAccounts/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testAddBankAccount() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("iban", 5132);
        jsonObject.addProperty("bankName", "Revolut");
        final MockHttpServletResponse response = mockMvc.perform(put("/user/profile/{userId}/addBankAccount", 0)
                        .content(jsonObject.toString()).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
        verify(mockUserService).addBankAccount(any(User.class), any(BankAccount.class));
    }

    @Test
    void testAddBankAccount_UserServiceGetUserByIdReturnsBadRequest() throws Exception {
        // Setup
//        when(mockUserService.getUserById(0L).isEmpty()).thenReturn(Optional.empty());
        mockUserService.getUserById(0L).isEmpty();

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/user/profile/{userId}/addBankAccount", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testRemoveBankAccount() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure BankAccountService.getBankAccountById(...).
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(bankAccount);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        delete("/user/profile/{userId}/removeBankAccount/{bankAccountId}", 0, 0)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockUserService).removeBankAccount(any(User.class), any(BankAccount.class));
        verify(mockBankAccountService).deleteBankAccount(any(BankAccount.class));
    }

    @Test
    void testRemoveBankAccount_UserServiceGetUserByIdReturnsNotFound() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Configure BankAccountService.getBankAccountById(...).
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(bankAccount);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        delete("/user/profile/{userId}/removeBankAccount/{bankAccountId}", 0, 0)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("User doesn't exist in DB", response.getContentAsString());
    }

    @Test
    void testRemoveBankAccount_BankAccountServiceGetBankAccountByIdReturnsBadRequest() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        final BankAccount bankAccount = new BankAccount(0, "bankName");
        when(mockBankAccountService.getBankAccountById(0L)).thenReturn(Optional.of(bankAccount));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(
                        delete("/user/profile/{userId}/removeBankAccount/{bankAccountId}", 0, 0)
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetAllBankTransactionsByUser() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/profile/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    void testGetAllBankTransactionsByUser_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());
        when(mockBankAccountService.getAllBankTransactionsByUser(any(User.class)))
                .thenReturn(List.of(new BankTransaction(0.0, LocalDate.of(2020, 1, 1))));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/profile/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testGetAllBankTransactionsByUser_BankAccountServiceReturnsNotFound() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        when(mockBankAccountService.getAllBankTransactionsByUser(any(User.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/profile/{userId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testBankToWallet() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure BankAccountService.createBankAccountTransaction(...).
        final BankTransaction bankTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.createBankAccountTransaction(any(User.class), any(BankAccount.class),
                eq(0.0))).thenReturn(bankTransaction);

        when(mockBankAccountService.bankToWallet(any(BankTransaction.class))).thenReturn(false);

        // Configure BankAccountService.saveBankTransaction(...).
        final BankTransaction bankTransaction1 = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.saveBankTransaction(any(BankTransaction.class))).thenReturn(bankTransaction1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/profile/{userId}/credit", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
        verify(mockBankAccountService).saveBankTransaction(any(BankTransaction.class));
    }

    @Test
    void testBankToWallet_UserServiceReturnsBadRequest() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Configure BankAccountService.createBankAccountTransaction(...).
        final BankTransaction bankTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.createBankAccountTransaction(any(User.class), any(BankAccount.class),
                eq(0.0))).thenReturn(bankTransaction);

        when(mockBankAccountService.bankToWallet(any(BankTransaction.class))).thenReturn(false);

        // Configure BankAccountService.saveBankTransaction(...).
        final BankTransaction bankTransaction1 = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.saveBankTransaction(any(BankTransaction.class))).thenReturn(bankTransaction1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/profile/{userId}/credit", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("", response.getContentAsString());
    }

    @Test
    void testBankDeposit() throws Exception {
        // Setup
        // Configure UserService.getUserById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserService.getUserById(0L)).thenReturn(user);

        // Configure BankAccountService.createBankAccountTransaction(...).
        final BankTransaction bankTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.createBankAccountTransaction(any(User.class), any(BankAccount.class),
                eq(0.0))).thenReturn(bankTransaction);

        when(mockBankAccountService.bankDeposit(any(BankTransaction.class))).thenReturn(false);

        // Configure BankAccountService.saveBankTransaction(...).
        final BankTransaction bankTransaction1 = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.saveBankTransaction(any(BankTransaction.class))).thenReturn(bankTransaction1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/profile/{userId}/withdraw", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockBankAccountService).saveBankTransaction(any(BankTransaction.class));
    }

    @Test
    void testBankDeposit_UserServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockUserService.getUserById(0L)).thenReturn(Optional.empty());

        // Configure BankAccountService.createBankAccountTransaction(...).
        final BankTransaction bankTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.createBankAccountTransaction(any(User.class), any(BankAccount.class),
                eq(0.0))).thenReturn(bankTransaction);

        when(mockBankAccountService.bankDeposit(any(BankTransaction.class))).thenReturn(false);

        // Configure BankAccountService.saveBankTransaction(...).
        final BankTransaction bankTransaction1 = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankAccountService.saveBankTransaction(any(BankTransaction.class))).thenReturn(bankTransaction1);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/profile/{userId}/withdraw", 0)
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockBankAccountService).saveBankTransaction(any(BankTransaction.class));
    }

    @Test
    void testGetAllBankAccounts() throws Exception {
        // Setup
        when(mockBankAccountService.getAllBankAccounts()).thenReturn(List.of(new BankAccount(0, "bankName")));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/profile/bankAccounts")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"bankAccountId\":null,\"iban\":0,\"bankName\":\"bankName\"}]", response.getContentAsString());
    }

    @Test
    void testGetAllBankTransactions() throws Exception {
        // Setup
        // Configure BankAccountService.getAllBankTransactions(...).
        final List<BankTransaction> list = List.of(new BankTransaction(0.0, LocalDate.of(2020, 1, 1)));
        when(mockBankAccountService.getAllBankTransactions()).thenReturn(list);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/profile/bankTransactions")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[{\"bankTransactionId\":null,\"amount\":0.0,\"date\":\"01/01/2020\",\"commission\":0.0,\"user\":null,\"bankAccount\":null}]", response.getContentAsString());
    }
}
