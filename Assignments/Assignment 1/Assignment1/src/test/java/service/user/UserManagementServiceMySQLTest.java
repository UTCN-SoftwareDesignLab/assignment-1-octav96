package service.user;

import database.DBConnectionFactory;
import model.Operation;
import model.Role;
import model.User;
import model.builder.OperationBuilder;
import model.builder.UserBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.operation.OperationRepository;
import repository.operation.OperationRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientManagementServiceMySQL;

import java.sql.Connection;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class UserManagementServiceMySQLTest {
    private static UserManagementService userManagementService;

    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;
    private static OperationRepository operationRepository;
    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();

        operationRepository = new OperationRepositoryMySQL(connection);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        userManagementService = new UserManagementServiceMySQL(userRepository, rightsRolesRepository, operationRepository);
    }
    @Test
    public void deleteUser() {
        operationRepository.deleteAll();
        userRepository.removeAll();
        userManagementService.setUserId(1l);
        userManagementService.addUser("octav@yahoo.com","ABCabc123123$", ADMINISTRATOR);
        userManagementService.deleteUser(userRepository.findAll().get(0));
        assertTrue(userRepository.findAll().size() == 0);
    }

    @Test
    public void addUser() {
        operationRepository.deleteAll();
        userRepository.removeAll();
        userManagementService.setUserId(1l);
        userManagementService.addUser("octav@yahoo.com","ABCabc123123$", ADMINISTRATOR);
        assertTrue(userRepository.findAll().size() == 1);
    }

    @Test
    public void findUser() {
        operationRepository.deleteAll();
        userRepository.removeAll();
        userManagementService.setUserId(1l);
        userManagementService.addUser("octav@yahoo.com","ABCabc123123$", ADMINISTRATOR);
        assertTrue(userManagementService.findUser(userRepository.findAll().get(0).getId()).getResult() != null);
    }

    @Test
    public void updateUser() {
        operationRepository.deleteAll();
        userRepository.removeAll();
        userManagementService.setUserId(1l);
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        userManagementService.addUser("octav@yahoo.com","ABCabc123123$", ADMINISTRATOR);
        User updateUser = new UserBuilder().setUsername("Costantin")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        assertTrue(userManagementService.updateUser(userRepository.findAll().get(0), updateUser).getResult() == Boolean.TRUE);
    }

    @Test
    public void getReportForUser() {
        operationRepository.deleteAll();
        userRepository.removeAll();
        userManagementService.setUserId(1l);
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        userManagementService.addUser("octav@yahoo.com","ABCabc123123$", ADMINISTRATOR);
        User user = new UserBuilder().setUsername("Costantin")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        Operation operation = new OperationBuilder().setOperation("BLA BLA")
                .setEmployeeId(userRepository.findAll().get(0).getId())
                .build();
        operationRepository.addOperation(operation);
        assertTrue(userManagementService.getReportForUser(userRepository.findAll().get(0).getId()).size() == 1);
    }


}