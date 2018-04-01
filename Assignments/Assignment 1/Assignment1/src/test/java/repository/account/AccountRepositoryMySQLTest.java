package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {

    private static AccountRepository accountRepository;
    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
    }
    @Test
    public void findAll() {
       // List<Account> accounts = accountRepository.findAll();
        accountRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();
        accountRepository.addAccount(client, account);
        assertNotNull(accountRepository.findAll());
    }

    @Test
    public void findAllForClient() {
        accountRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();

        accountRepository.addAccount(client, account);
        assertTrue(accountRepository.findAllForClient(client).size() == 1);

    }

    @Test
    public void findById() {
        accountRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();
        accountRepository.addAccount(client, account);
        Long id = accountRepository.findAllForClient(client).get(0).getId();
        assertTrue(accountRepository.findById(id) != null);
    }

    @Test
    public void addAccount() {
        accountRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();
        accountRepository.addAccount(client, account);
        assertTrue(accountRepository.findAll().size() == 1);
    }

    @Test
    public void deleteAccount() {
        accountRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();
        accountRepository.addAccount(client, account);
        Long id = accountRepository.findAllForClient(client).get(0).getId();
        accountRepository.deleteAccount(id);
        assertTrue(accountRepository.findAll().size() == 0);
    }

    @Test
    public void updateAccount() {
        accountRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();
        accountRepository.addAccount(client, account);
        Account updateAccount = new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("DEBIT")
                .setClientId(5l)
                .build();
        accountRepository.updateAccount(account,updateAccount);

        assertTrue(accountRepository.findAllForClient(client).get(0).getType().equals("DEBIT"));
    }
}