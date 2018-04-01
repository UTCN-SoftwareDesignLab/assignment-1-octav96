package repository.security;

import database.DBConnectionFactory;
import model.Right;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.util.Collections;

import static database.Constants.Rights.CREATE_USER;
import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class RightsRolesRepositoryMySQLTest {

    private static Connection connection;
    private static RightsRolesRepository rightsRolesRepository;
    @BeforeClass
    public static void setUp() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
    }
    @Test
    public void addRole() {
        rightsRolesRepository.deleteAllRoles();
        rightsRolesRepository.addRole(ADMINISTRATOR);
        assertTrue(rightsRolesRepository.findRoleByTitle(ADMINISTRATOR) != null);
    }

    @Test
    public void addRight() {
//        rightsRolesRepository.deleteAllRights();
        rightsRolesRepository.addRight(CREATE_USER);
        assertTrue(rightsRolesRepository.findRightByTitle(CREATE_USER) != null);
    }

    @Test
    public void findRoleByTitle() {
        rightsRolesRepository.deleteAllRoles();
        rightsRolesRepository.addRole(ADMINISTRATOR);
        assertTrue(rightsRolesRepository.findRoleByTitle(ADMINISTRATOR).getResult().getRole().equals(ADMINISTRATOR));
    }

    @Test
    public void findRoleById() {
        rightsRolesRepository.deleteAllRoles();
        rightsRolesRepository.addRole(ADMINISTRATOR);

        assertTrue(rightsRolesRepository.findRoleById(rightsRolesRepository.findRoleByTitle(ADMINISTRATOR)
                .getResult().getId()).getRole().equals(ADMINISTRATOR));
    }

    @Test
    public void findRightByTitle() {
//        rightsRolesRepository.deleteAllRights();
        Right right = new Right(1l, CREATE_USER);
        rightsRolesRepository.addRight(right.getRight());
        assertTrue(rightsRolesRepository.findRightByTitle(CREATE_USER).getRight().equals(CREATE_USER));
    }

    @Test
    public void addRolesToUser() {
        rightsRolesRepository.deleteAllUserRole();
        Role role = new Role(1l,ADMINISTRATOR,null);
        User user = new UserBuilder().setId(1l)
                .setPassword("password")
                .setUsername("Costel")
                .setRoles(Collections.singletonList(role))
                .build();
        rightsRolesRepository.addRolesToUser(user, Collections.singletonList(role));
        assertTrue(rightsRolesRepository.findRolesForUser(user.getId()) != null);
    }

    @Test
    public void deleteRolesFromUser() {
        rightsRolesRepository.deleteAllUserRole();
        Role role = new Role(1l,ADMINISTRATOR,null);
        User user = new UserBuilder().setId(1l)
                .setPassword("password")
                .setUsername("Costel")
                .setRoles(Collections.singletonList(role))
                .build();
        rightsRolesRepository.addRolesToUser(user, Collections.singletonList(role));
        rightsRolesRepository.deleteRolesFromUser(user.getId());
        assertTrue(rightsRolesRepository.findRolesForUser(user.getId()).size() == 0);
    }

    @Test
    public void findRolesForUser() {
        rightsRolesRepository.deleteAllUserRole();
        Role role = new Role(1l,ADMINISTRATOR,null);
        User user = new UserBuilder().setId(1l)
                .setPassword("password")
                .setUsername("Costel")
                .setRoles(Collections.singletonList(role))
                .build();
        rightsRolesRepository.addRolesToUser(user, Collections.singletonList(role));
        assertTrue(rightsRolesRepository.findRolesForUser(user.getId()) != null);
    }

    @Test
    public void findRoleIdsForUser() {
        rightsRolesRepository.deleteAllUserRole();
        Role role = new Role(1l,ADMINISTRATOR,null);
        User user = new UserBuilder().setId(1l)
                .setPassword("password")
                .setUsername("Costel")
                .setRoles(Collections.singletonList(role))
                .build();
        rightsRolesRepository.addRolesToUser(user, Collections.singletonList(role));
        assertTrue(rightsRolesRepository.findRoleIdsForUser(user.getId()) != null);
    }

    @Test
    public void addRoleRight() {
    }
}