package com.payMyBuddy.services;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.BankTransaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.BankAccountRepository;
import com.payMyBuddy.repositories.BankTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {

    @Mock
    private BankAccountRepository mockBankAccountRepository;
    @Mock
    private BankTransactionRepository mockBankTransactionRepository;
    @Mock
    private UserService mockUserService;

    @InjectMocks
    private BankAccountService bankAccountServiceUnderTest;

    @Test
    void testCreateBankAccountTransaction() {
        // Setup
        final User userId = new User("firstName", "lastName", "email", "password");
        final BankAccount bankAccountId = new BankAccount(0, "bankName");

        // Run the test
        final BankTransaction result = bankAccountServiceUnderTest.createBankAccountTransaction(userId, bankAccountId,
                0.0);

        // Verify the results
    }

    @Test
    void testSaveBankTransaction() {
        // Setup
        final BankTransaction bankAccountTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));

        // Configure BankTransactionRepository.save(...).
        final BankTransaction bankTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));
        when(mockBankTransactionRepository.save(any(BankTransaction.class))).thenReturn(bankTransaction);

        // Run the test
        final BankTransaction result = bankAccountServiceUnderTest.saveBankTransaction(bankAccountTransaction);

        // Verify the results
    }

    @Test
    void testBankToWallet() {
        // Setup
        final BankTransaction bankTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));

        // Run the test
        final boolean result = bankAccountServiceUnderTest.bankToWallet(bankTransaction);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockUserService).updateUser(any(User.class));
    }

    @Test
    void testBankDeposit() {
        // Setup
        final BankTransaction bankTransaction = new BankTransaction(0.0, LocalDate.of(2020, 1, 1));

        // Run the test
        final boolean result = bankAccountServiceUnderTest.bankDeposit(bankTransaction);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockUserService).updateUser(any(User.class));
    }

    @Test
    void testGetAllBankAccounts() {
        // Setup
        when(mockBankAccountRepository.findAll()).thenReturn(List.of(new BankAccount(0, "bankName")));

        // Run the test
        final List<BankAccount> result = bankAccountServiceUnderTest.getAllBankAccounts();

        // Verify the results
    }

    @Test
    void testGetAllBankAccounts_BankAccountRepositoryReturnsNoItems() {
        // Setup
        when(mockBankAccountRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BankAccount> result = bankAccountServiceUnderTest.getAllBankAccounts();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetBankAccountById() {
        // Setup
        // Configure BankAccountRepository.findById(...).
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountRepository.findById(0L)).thenReturn(bankAccount);

        // Run the test
        final Optional<BankAccount> result = bankAccountServiceUnderTest.getBankAccountById(0L);

        // Verify the results
    }

    @Test
    void testGetBankAccountById_BankAccountRepositoryReturnsAbsent() {
        // Setup
        when(mockBankAccountRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<BankAccount> result = bankAccountServiceUnderTest.getBankAccountById(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testGetBankAccountByIban() {
        // Setup
        // Configure BankAccountRepository.findByIban(...).
        final Optional<BankAccount> bankAccount = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountRepository.findByIban(0)).thenReturn(bankAccount);

        // Run the test
        final Optional<BankAccount> result = bankAccountServiceUnderTest.getBankAccountByIban(0);

        // Verify the results
    }

    @Test
    void testGetBankAccountByIban_BankAccountRepositoryReturnsAbsent() {
        // Setup
        when(mockBankAccountRepository.findByIban(0)).thenReturn(Optional.empty());

        // Run the test
        final Optional<BankAccount> result = bankAccountServiceUnderTest.getBankAccountByIban(0);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testAddBankAccount() {
        // Setup
        final BankAccount bankAccount = new BankAccount(0, "bankName");
        when(mockBankAccountRepository.save(any(BankAccount.class))).thenReturn(new BankAccount(0, "bankName"));

        // Run the test
        bankAccountServiceUnderTest.addBankAccount(bankAccount);

        // Verify the results
        verify(mockBankAccountRepository).save(any(BankAccount.class));
    }

    @Test
    void testUpdateBankAccount() {
        // Setup
        final BankAccount bankAccount = new BankAccount(0, "bankName");

        // Configure BankAccountRepository.findById(...).
        final Optional<BankAccount> bankAccount1 = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountRepository.findById(0L)).thenReturn(bankAccount1);

        when(mockBankAccountRepository.save(any(BankAccount.class))).thenReturn(new BankAccount(0, "bankName"));

        // Run the test
        bankAccountServiceUnderTest.updateBankAccount(bankAccount);

        // Verify the results
        verify(mockBankAccountRepository).save(any(BankAccount.class));
    }

    @Test
    void testUpdateBankAccount_BankAccountRepositoryFindByIdReturnsAbsent() {
        // Setup
        final BankAccount bankAccount = new BankAccount(0, "bankName");
        when(mockBankAccountRepository.findById(0L)).thenReturn(Optional.empty());
        when(mockBankAccountRepository.save(any(BankAccount.class))).thenReturn(new BankAccount(0, "bankName"));

        // Run the test
        bankAccountServiceUnderTest.updateBankAccount(bankAccount);

        // Verify the results
        verify(mockBankAccountRepository).save(any(BankAccount.class));
    }

    @Test
    void testDeleteBankAccount() {
        // Setup
        final BankAccount bankAccount = new BankAccount(0, "bankName");

        // Configure BankAccountRepository.findById(...).
        final Optional<BankAccount> bankAccount1 = Optional.of(new BankAccount(0, "bankName"));
        when(mockBankAccountRepository.findById(0L)).thenReturn(bankAccount1);

        // Run the test
        bankAccountServiceUnderTest.deleteBankAccount(bankAccount);

        // Verify the results
        verify(mockBankAccountRepository).deleteById(0L);
    }

    @Test
    void testDeleteBankAccount_BankAccountRepositoryFindByIdReturnsAbsent() {
        // Setup
        final BankAccount bankAccount = new BankAccount(0, "bankName");
        when(mockBankAccountRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        bankAccountServiceUnderTest.deleteBankAccount(bankAccount);

        // Verify the results
        verify(mockBankAccountRepository).deleteById(0L);
    }

    @Test
    void testGetAllBankTransactionsByUser() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");

        // Configure BankTransactionRepository.findAllBankTransactionsByUser(...).
        final List<BankTransaction> list = List.of(new BankTransaction(0.0, LocalDate.of(2020, 1, 1)));
        when(mockBankTransactionRepository.findAllBankTransactionsByUser(any(User.class))).thenReturn(list);

        // Run the test
        final List<BankTransaction> result = bankAccountServiceUnderTest.getAllBankTransactionsByUser(user);

        // Verify the results
    }

    @Test
    void testGetAllBankTransactionsByUser_BankTransactionRepositoryReturnsNoItems() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockBankTransactionRepository.findAllBankTransactionsByUser(any(User.class)))
                .thenReturn(Collections.emptyList());

        // Run the test
        final List<BankTransaction> result = bankAccountServiceUnderTest.getAllBankTransactionsByUser(user);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetAllBankTransactions() {
        // Setup
        // Configure BankTransactionRepository.findAll(...).
        final List<BankTransaction> list = List.of(new BankTransaction(0.0, LocalDate.of(2020, 1, 1)));
        when(mockBankTransactionRepository.findAll()).thenReturn(list);

        // Run the test
        final List<BankTransaction> result = bankAccountServiceUnderTest.getAllBankTransactions();

        // Verify the results
    }

    @Test
    void testGetAllBankTransactions_BankTransactionRepositoryReturnsNoItems() {
        // Setup
        when(mockBankTransactionRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BankTransaction> result = bankAccountServiceUnderTest.getAllBankTransactions();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
