package repository.user;

import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class UserRepositoryMySQLTest {
    private static Connection connection;
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
    }
    @Test
    public void findAll() {
        userRepository.removeAll();
        Role role = new Role(1l,ADMINISTRATOR,null);
        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setId(3l)
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        assertTrue(userRepository.findAll().size() == 1);
    }

    @Test
    public void findById() throws AuthenticationException{
        userRepository.removeAll();
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);

        User userCheck = userRepository.findByUsernameAndPassword("Costel","banana").getResult();

        assertTrue(userRepository.findById(userCheck.getId()).getUsername().equals(user.getUsername()));

    }

    @Test
    public void findByUsernameAndPassword() throws AuthenticationException{
        userRepository.removeAll();
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);

        assertTrue(userRepository.findByUsernameAndPassword("Costel","banana").getResult() != null);
    }

    @Test
    public void save() {
        userRepository.removeAll();
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        assertTrue(userRepository.findAll().size() == 1);
    }

    @Test
    public void deleteUser() throws AuthenticationException{
        userRepository.removeAll();
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        userRepository.deleteUser(userRepository.findByUsernameAndPassword("Costel","banana").getResult().getId());
        assertTrue(userRepository.findAll().size() == 0);
    }

    @Test
    public void updateUser() {
        userRepository.removeAll();
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();

        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);


        User updateUser = new UserBuilder().setUsername("Costantin")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.updateUser(user,updateUser);
        assertTrue(userRepository.findAll().get(0).getUsername().equals(updateUser.getUsername()));
    }

    @Test
    public void removeAll() {
        userRepository.removeAll();
        Role role = rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult();
        User user = new UserBuilder().setUsername("Costel")
                .setPassword("banana")
                .setRoles(Collections.singletonList(role))
                .build();
        userRepository.save(user);
        userRepository.removeAll();
        assertTrue(userRepository.findAll().size() == 0);
    }
}