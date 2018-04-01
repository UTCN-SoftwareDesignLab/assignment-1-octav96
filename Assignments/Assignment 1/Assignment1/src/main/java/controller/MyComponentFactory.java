package controller;

import database.DBConnectionFactory;
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
import service.account.AccountManagementService;
import service.account.AccountManagementServiceMySQL;
import service.client.ClientManagementService;
import service.client.ClientManagementServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserManagementService;
import service.user.UserManagementServiceMySQL;

import java.sql.Connection;

/**
 * Created by Alex on 18/03/2017.
 */
public class MyComponentFactory {

    private final AuthenticationService authenticationService;
    private final ClientManagementService clientManagementService;
    private final AccountManagementService accountManagementService;
    private final UserManagementService userManagementService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final OperationRepository operationRepository;
    private final AccountRepository accountRepository;

    private static MyComponentFactory instance;

    public static MyComponentFactory instance() {
        if (instance == null) {
            instance = new MyComponentFactory();
        }
        return instance;
    }

    private MyComponentFactory() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(false).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.operationRepository = new OperationRepositoryMySQL(connection);

        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.userManagementService = new UserManagementServiceMySQL(this.userRepository,
                this.rightsRolesRepository, this.operationRepository);
        this.userManagementService.setUserId(3l);

       // this.operationRepository = new OperationRepositoryMySQL(connection);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.clientRepository = new ClientRepositoryMySQL(connection,accountRepository);

        this.clientManagementService = new ClientManagementServiceMySQL(clientRepository, accountRepository, operationRepository);
        this.accountManagementService = new AccountManagementServiceMySQL(accountRepository, clientRepository, operationRepository);
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public ClientManagementService getClientManagementService() {
        return clientManagementService;
    }

    public AccountManagementService getAccountManagementService() {
        return accountManagementService;
    }

    public UserManagementService getUserManagementService() {
        return userManagementService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

}
