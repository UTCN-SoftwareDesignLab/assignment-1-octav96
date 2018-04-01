package service.client;

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
import service.account.AccountManagementServiceMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClientManagementServiceMySQLTest {
    private static ClientManagementService clientManagementService;
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;
    private static OperationRepository operationRepository;
    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
        operationRepository = new OperationRepositoryMySQL(connection);
        clientManagementService = new ClientManagementServiceMySQL( clientRepository, accountRepository, operationRepository);
    }
    @Test
    public void deleteClient() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        clientManagementService.setUserId(1l);

        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        clientManagementService.addClient(client);
        assertTrue(clientManagementService.deleteClient(clientRepository.findAll().get(0)).getResult() != null);
    }

    @Test
    public void addClient() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        clientManagementService.setUserId(1l);

        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        assertTrue(clientManagementService.addClient(client).getResult() != null);

    }

    @Test
    public void findClient() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        clientManagementService.setUserId(1l);

        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();

        clientManagementService.addClient(client);
        assertTrue(clientManagementService.findClient(clientRepository.findAll().get(0).getId()).getResult() != null);

    }

    @Test
    public void updateClient() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        clientManagementService.setUserId(1l);

        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        Client updateClient = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costantin").build();
        clientManagementService.addClient(client);

        assertTrue(clientManagementService
                .updateClient(clientRepository.findAll().get(0),updateClient).getResult() != Boolean.FALSE);

    }

    @Test
    public void addAccount() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        clientManagementService.setUserId(1l);

        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        Client updateClient = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costantin").build();
        clientManagementService.addClient(client);
        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();

        assertTrue(clientManagementService
                .addAccount(clientRepository.findAll().get(0), account).getResult() != Boolean.FALSE);

    }

    @Test
    public void findAllClients() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        clientManagementService.setUserId(1l);

        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        Client updateClient = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costantin").build();
        clientManagementService.addClient(client);
        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();

        assertTrue(clientManagementService
                .findAllClients().size() == 1);

    }

    @Test
    public void findAllAccountsForClient() {
        accountRepository.deleteAll();
        clientRepository.deleteAll();
        clientManagementService.setUserId(1l);

        Client client = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        Client updateClient = new ClientBuilder()
                //.setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costantin").build();
        clientManagementService.addClient(client);
        Account account =  new AccountBuilder().setId(5l)
                .setAmount(500.5)
                .setType("CREDIT")
                .setClientId(5l)
                .build();

        clientManagementService.addAccount(clientRepository.findAll().get(0),account);
        assertTrue(clientManagementService.findAllAccountsForClient(clientRepository.findAll().get(0)).getResult() != null);

    }


}