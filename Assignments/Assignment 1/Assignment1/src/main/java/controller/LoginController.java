package controller;

import model.Client;
import model.User;
import model.validation.Notification;
import repository.operation.OperationRepositoryMySQL;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.AuthenticationException;
import repository.user.UserRepositoryMySQL;
import service.account.AccountManagementService;
import service.account.AccountManagementServiceMySQL;
import service.client.ClientManagementService;
import service.client.ClientManagementServiceMySQL;
import service.user.AuthenticationService;
import service.user.UserManagementService;
import service.user.UserManagementServiceMySQL;
import view.AccountView;
import view.ClientView;
import view.LoginView;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 18/03/2017.
 */

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    //private final MyComponentFactory myComponentFactory;
    private final ClientManagementService clientManagementService;
    private final AccountManagementService accountManagementService;
    private final UserManagementService userManagementService;
    public LoginController(LoginView loginView, AuthenticationService authenticationService,
                           ClientManagementService clientManagementService,
                           AccountManagementService accountManagementService,
                           UserManagementService userManagementService) {
        this.loginView = loginView;
        //this.myComponentFactory = myComponentFactory;
        this.authenticationService = authenticationService;
        this.clientManagementService = clientManagementService;
        this.accountManagementService = accountManagementService;
        this.userManagementService = userManagementService;
        loginView.setLoginButtonListener(new LoginButtonListener());
      //  loginView.setRegisterButtonListener(new RegisterButtonListener());
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
            } catch (AuthenticationException e1) {
                e1.printStackTrace();
            }
            //ControllerFactory controllerFactory = ControllerFactory.instance();
            //MyComponentFactory myComponentFactory = MyComponentFactory.instance();
            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Login successful!");
                    List<Long> roles = new ArrayList<>();

                    if(loginNotification.getResult().getRoles().get(0).getId().equals(2l)){
                       // System.out.print(loginNotification.getResult().getRoles().get(0).getId());
                        clientManagementService.setUserId(loginNotification.getResult().getId());
                        accountManagementService.setUserId(loginNotification.getResult().getId());
                        new EmployeeController(new ClientView(), new AccountView(),
                                clientManagementService, accountManagementService);
                    }else{
                        //System.out.print(loginNotification.getResult().getRoles().get(0).getId());

                        userManagementService.setUserId(loginNotification.getResult().getId());

                        new AdministratorController(new UserView(),userManagementService);
                    }
                }
            }
        }
    }




}
