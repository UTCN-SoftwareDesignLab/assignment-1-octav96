package service.user;

import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;

/**
 * Created by Alex on 11/03/2017.
 */
public class AuthenticationServiceMySQLTest {

    public static final String TEST_USERNAME = "test@username.com";
    public static final String TEST_PASSWORD = "TestPassword1@";
    private static AuthenticationService authenticationService;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;


    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        //RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

        authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);

    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }



    @Test
    public void login() throws Exception {
        userRepository.removeAll();
        rightsRolesRepository.addRole(ADMINISTRATOR);
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);


        Assert.assertNotNull(authenticationService
                .login(userRepository.findAll().get(0).getUsername(),userRepository.findAll()
                .get(0).getPassword()));
    }


}