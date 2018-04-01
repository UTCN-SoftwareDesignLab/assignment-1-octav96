package repository.operation;

import database.DBConnectionFactory;
import model.Operation;
import model.User;
import model.builder.OperationBuilder;
import model.builder.UserBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class OperationRepositoryMySQLTest {

    private static OperationRepository operationRepository;
    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        operationRepository = new OperationRepositoryMySQL(connection);

    }
    @Test
    public void findAllForUser() {
        operationRepository.deleteAll();
        Operation operation = new OperationBuilder().setEmployeeId(1l).setOperation("BLABLA").build();
        operationRepository.addOperation(operation);
        User user = new UserBuilder().setId(1l).build();
        assertTrue(operationRepository.findAllForUser(user).size() == 1);
    }

    @Test
    public void addOperation() {
        operationRepository.deleteAll();
        Operation operation = new OperationBuilder().setEmployeeId(1l).setOperation("BLABLA").build();
        operationRepository.addOperation(operation);
        User user = new UserBuilder().setId(1l).build();
        assertTrue(operationRepository.findAllForUser(user).size() == 1);
    }
}