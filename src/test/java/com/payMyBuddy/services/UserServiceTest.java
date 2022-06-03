package com.payMyBuddy.services;

import com.payMyBuddy.models.BankAccount;
import com.payMyBuddy.models.Transaction;
import com.payMyBuddy.models.User;
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
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    void testGetAllUsers() {
        // Setup
        // Configure UserRepository.findAll(...).
        final List<User> users = List.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        final List<User> result = userServiceUnderTest.getAllUsers();

        // Verify the results
    }

    @Test
    void testGetAllUsers_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<User> result = userServiceUnderTest.getAllUsers();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetUserById() {
        // Setup
        // Configure UserRepository.findById(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserRepository.findById(0L)).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.getUserById(0L);

        // Verify the results
    }

    @Test
    void testGetUserById_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.getUserById(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testGetUserByEmail() {
        // Setup
        // Configure UserRepository.findByEmail(...).
        final Optional<User> user = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserRepository.findByEmail("email")).thenReturn(user);

        // Run the test
        final Optional<User> result = userServiceUnderTest.getUserByEmail("email");

        // Verify the results
    }

    @Test
    void testGetUserByEmail_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        final Optional<User> result = userServiceUnderTest.getUserByEmail("email");

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testCreateUser() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");

        // Configure UserRepository.save(...).
        final User user1 = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        userServiceUnderTest.createUser(user);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");

        // Configure UserRepository.findById(...).
        final Optional<User> user1 = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserRepository.findById(0L)).thenReturn(user1);

        // Configure UserRepository.save(...).
        final User user2 = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user2);

        // Run the test
        userServiceUnderTest.updateUser(user);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testUpdateUser_UserRepositoryFindByIdReturnsAbsent() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.findById(0L)).thenReturn(Optional.empty());

        // Configure UserRepository.save(...).
        final User user1 = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        userServiceUnderTest.updateUser(user);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");

        // Configure UserRepository.findByEmail(...).
        final Optional<User> user1 = Optional.of(new User("firstName", "lastName", "email", "password"));
        when(mockUserRepository.findByEmail("email")).thenReturn(user1);

        // Run the test
        userServiceUnderTest.deleteUser(user);

        // Verify the results
        verify(mockUserRepository).deleteById(0L);
    }

    @Test
    void testDeleteUser_UserRepositoryFindByEmailReturnsAbsent() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.findByEmail("email")).thenReturn(Optional.empty());

        // Run the test
        userServiceUnderTest.deleteUser(user);

        // Verify the results
        verify(mockUserRepository).deleteById(0L);
    }

    @Test
    void testAddFriend() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        final User friendUser = new User("firstName", "lastName", "email", "password");

        // Configure UserRepository.save(...).
        final User user1 = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        userServiceUnderTest.addFriend(user, friendUser);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testRemoveFriend() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        final User friendUser = new User("firstName", "lastName", "email", "password");

        // Configure UserRepository.save(...).
        final User user1 = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        userServiceUnderTest.removeFriend(user, friendUser);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testAddBankAccount() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        final BankAccount bankAccount = new BankAccount(0, "bankName");

        // Configure UserRepository.save(...).
        final User user1 = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        userServiceUnderTest.addBankAccount(user, bankAccount);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testRemoveBankAccount() {
        // Setup
        final User user = new User("firstName", "lastName", "email", "password");
        final BankAccount bankAccount = new BankAccount(0, "bankName");

        // Configure UserRepository.save(...).
        final User user1 = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user1);

        // Run the test
        userServiceUnderTest.removeBankAccount(user, bankAccount);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testAddCreditorToTransaction() {
        // Setup
        final User creditor = new User("firstName", "lastName", "email", "password");
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure UserRepository.save(...).
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        // Run the test
        userServiceUnderTest.addCreditorToTransaction(creditor, transaction);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testRemoveCreditorToTransaction() {
        // Setup
        final User creditor = new User("firstName", "lastName", "email", "password");
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure UserRepository.save(...).
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        // Run the test
        userServiceUnderTest.removeCreditorToTransaction(creditor, transaction);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testAddDebtorToTransaction() {
        // Setup
        final User debtor = new User("firstName", "lastName", "email", "password");
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure UserRepository.save(...).
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        // Run the test
        userServiceUnderTest.addDebtorToTransaction(debtor, transaction);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }

    @Test
    void testRemoveDebtorToTransaction() {
        // Setup
        final User debtor = new User("firstName", "lastName", "email", "password");
        final Transaction transaction = new Transaction(0.0, LocalDate.of(2020, 1, 1), 0.0, "description");

        // Configure UserRepository.save(...).
        final User user = new User("firstName", "lastName", "email", "password");
        when(mockUserRepository.save(any(User.class))).thenReturn(user);

        // Run the test
        userServiceUnderTest.removeDebtorToTransaction(debtor, transaction);

        // Verify the results
        verify(mockUserRepository).save(any(User.class));
    }
}
