package service.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.operation.OperationRepository;
import repository.operation.OperationRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class AccountManagementServiceMySQLTest {

    private static AccountManagementService accountManagementService;
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
    private static OperationRepository operationRepository;
    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
        operationRepository = new OperationRepositoryMySQL(connection);
        accountManagementService = new AccountManagementServiceMySQL(accountRepository, clientRepository, operationRepository);
    }
    @Test
    public void addMoney() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        accountManagementService.setUserId(1l);
        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Account account =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        accountRepository.addAccount(client, account);
        assertTrue(accountManagementService.addMoney(accountRepository.findAll().get(0),500d).getResult() != null);

    }

    @Test
    public void takeMoney() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        accountManagementService.setUserId(1l);
        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Account account =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        accountRepository.addAccount(client, account);
        assertTrue(accountManagementService.takeMoney(accountRepository.findAll().get(0),500d).getResult() != null);
    }

    @Test
    public void deleteAccount() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        accountManagementService.setUserId(1l);
        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Account account =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        accountRepository.addAccount(client, account);
        assertTrue(accountManagementService.deleteAccount(accountRepository.findAll().get(0)).getResult() != null);
    }

    @Test
    public void addAccount() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        accountManagementService.setUserId(1l);
        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Account account =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        assertTrue(accountManagementService.addAccount(clientRepository.findAll().get(0), account).getResult() != null);
    }

    @Test
    public void findAllAccounts() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        accountManagementService.setUserId(1l);
        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Account account =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        assertTrue(accountManagementService.findAllAccounts() != null);
    }

    @Test
    public void findAccount() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        accountManagementService.setUserId(1l);
        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Account account =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        accountRepository.addAccount(clientRepository.findAll().get(0), account);
        accountManagementService.addAccount(clientRepository.findAll().get(0),accountRepository.findAll().get(0));
        assertTrue(accountManagementService.findAccount(accountRepository.findAll().get(0)).getResult() != null);
    }

    @Test
    public void transferMoney() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        accountManagementService.setUserId(1l);
        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Account account =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        Account accountTransfer =  new AccountBuilder()//.setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(clientRepository.findAll().get(0).getId())
                .build();
        accountRepository.addAccount(clientRepository.findAll().get(0), account);
        accountRepository.addAccount(clientRepository.findAll().get(0), accountTransfer);

        accountManagementService.addAccount(clientRepository.findAll().get(0),accountRepository.findAll().get(0));

        assertTrue(accountManagementService.transferMoney(accountRepository.findAll().get(0),
                accountRepository.findAll().get(1),500d).getResult() != null);
    }

}