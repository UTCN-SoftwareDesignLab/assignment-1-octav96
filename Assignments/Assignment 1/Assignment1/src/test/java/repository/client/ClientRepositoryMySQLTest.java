package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {

    private static ClientRepository clientRepository;
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();

        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
    }
    @Test
    public void findAll() {
        clientRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        assertTrue(clientRepository.findAll().size() == 1);

    }

    @Test
    public void findById() {
        clientRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        Long id = clientRepository.findAll().get(0).getId();
        assertTrue(clientRepository.findById(id).getName().equals(client.getName()));
    }

    @Test
    public void addClient() {
        clientRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        assertTrue(clientRepository.findAll().size() == 1);
    }

    @Test
    public void deleteClient() {
        clientRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        clientRepository.addClient(client);
        clientRepository.deleteClient(clientRepository.findAll().get(0).getId());
        assertTrue(clientRepository.findAll().size() == 0);
    }

    @Test
    public void updateClient() {
        clientRepository.deleteAll();
        Client client = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costel").build();
        Client updateClient = new ClientBuilder()
                .setId(5l)
                .setAddress("ABC")
                .setPersonalNumericCode("123123123")
                .setIdentityCardNumber(123123)
                .setName("Costantin").build();

        clientRepository.addClient(client);
        clientRepository.updateClient(clientRepository.findAll().get(0),updateClient);
        assertTrue(clientRepository.findAll().get(0).getName().equals(updateClient.getName()));

    }
}