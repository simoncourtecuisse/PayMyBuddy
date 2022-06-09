package com.payMyBuddy.services;

import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
import com.payMyBuddy.repositories.TransactionRepository;
import com.payMyBuddy.repositories.UserRepository;
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
class TransactionServiceTest {

    @Mock
    private TransactionRepository mockTransactionRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private UserService mockUserService;

    @InjectMocks
    private TransactionService transactionServiceUnderTest;

    @Test
    void testGetAllTransactions() {
        // Setup
        // Configure TransactionRepository.findAll(...).
        final List<Transaction> transactions = List.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionRepository.findAll()).thenReturn(transactions);

        // Run the test
        final Iterable<Transaction> result = transactionServiceUnderTest.getAllTransactions();

        // Verify the results
    }

    @Test
    void testGetAllTransactions_TransactionRepositoryReturnsNoItems() {
        // Setup
        when(mockTransactionRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final Iterable<Transaction> result = transactionServiceUnderTest.getAllTransactions();

        // Verify the results
    }

    @Test
    void testGetTransactionById() {
        // Setup
        // Configure TransactionRepository.findById(...).
        final Optional<Transaction> transaction = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionRepository.findById(0L)).thenReturn(transaction);

        // Run the test
        final Optional<Transaction> result = transactionServiceUnderTest.getTransactionById(0L);

        // Verify the results
    }

    @Test
    void testGetTransactionById_TransactionRepositoryReturnsAbsent() {
        // Setup
        when(mockTransactionRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Transaction> result = transactionServiceUnderTest.getTransactionById(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testCreateTransaction1() {
        // Setup
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure TransactionRepository.save(...).
        final Transaction transaction1 = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionRepository.save(any(Transaction.class))).thenReturn(transaction1);

        // Run the test
        transactionServiceUnderTest.createTransaction(transaction);

        // Verify the results
        verify(mockTransactionRepository).save(any(Transaction.class));
    }

    @Test
    void testGetAllTransactionsByUser() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");

        // Configure TransactionRepository.findAllTransactionsByDebtorOrCreditorOrderByDateDesc(...).
        final List<Transaction> transactions = List.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionRepository.findAllTransactionsByDebtorOrCreditorOrderByDateDesc(any(User.class),
                any(User.class))).thenReturn(transactions);

        // Run the test
        final List<Transaction> result = transactionServiceUnderTest.getAllTransactionsByUser(user);

        // Verify the results
    }

    @Test
    void testGetAllTransactionsByUser_TransactionRepositoryReturnsNoItems() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockTransactionRepository.findAllTransactionsByDebtorOrCreditorOrderByDateDesc(any(User.class),
                any(User.class))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Transaction> result = transactionServiceUnderTest.getAllTransactionsByUser(user);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testUpdateTransaction() {
        // Setup
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure TransactionRepository.findById(...).
        final Optional<Transaction> transaction1 = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionRepository.findById(0L)).thenReturn(transaction1);

        // Configure TransactionRepository.save(...).
        final Transaction transaction2 = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionRepository.save(any(Transaction.class))).thenReturn(transaction2);

        // Run the test
        transactionServiceUnderTest.updateTransaction(transaction);

        // Verify the results
        verify(mockTransactionRepository).save(any(Transaction.class));
    }

    @Test
    void testUpdateTransaction_TransactionRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionRepository.findById(0L)).thenReturn(Optional.empty());

        // Configure TransactionRepository.save(...).
        final Transaction transaction1 = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionRepository.save(any(Transaction.class))).thenReturn(transaction1);

        // Run the test
        transactionServiceUnderTest.updateTransaction(transaction);

        // Verify the results
        verify(mockTransactionRepository).save(any(Transaction.class));
    }

    @Test
    void testDeleteTransaction() {
        // Setup
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure TransactionRepository.findById(...).
        final Optional<Transaction> transaction1 = Optional.of(
                new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description"));
        when(mockTransactionRepository.findById(0L)).thenReturn(transaction1);

        // Run the test
        transactionServiceUnderTest.deleteTransaction(transaction);

        // Verify the results
        verify(mockTransactionRepository).deleteById(0L);
    }

    @Test
    void testDeleteTransaction_TransactionRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        transactionServiceUnderTest.deleteTransaction(transaction);

        // Verify the results
        verify(mockTransactionRepository).deleteById(0L);
    }

    @Test
    void testCreateTransaction2() {
        // Setup
        final User debtor = new User("firstName", "lastName", "email", "password");
        final User creditor = new User("firstName", "lastName", "email", "password");

        // Run the test
        final Transaction result = transactionServiceUnderTest.createTransaction(debtor, creditor, 0.0, "description");

        // Verify the results
    }

    @Test
    void testSaveTransaction() {
        // Setup
        final Transaction paymentTransaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure TransactionRepository.save(...).
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockTransactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // Run the test
        transactionServiceUnderTest.saveTransaction(paymentTransaction);

        // Verify the results
        verify(mockTransactionRepository).save(any(Transaction.class));
    }

    @Test
    void testProcessUserTransaction() {
        // Setup
        final Transaction transaction = new Transaction(10, LocalDate.of(2020, 1, 1), 2, "description");

        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final boolean result = transactionServiceUnderTest.processUserTransaction(transaction);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockUserService).updateUser(any(User.class));
    }

    @Test
    void testProcessUserTransaction_UserRepositoryReturnsAbsent() {
        // Setup
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final boolean result = transactionServiceUnderTest.processUserTransaction(transaction);

        // Verify the results
        assertThat(result).isFalse();
        verify(mockUserService).updateUser(any(User.class));
    }
}
