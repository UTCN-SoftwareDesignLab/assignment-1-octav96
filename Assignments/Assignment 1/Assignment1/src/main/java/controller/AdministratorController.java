package controller;

import model.Operation;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepositoryMySQL;
import service.user.UserManagementService;
import view.UserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdministratorController {
    private UserView userView;
    private UserManagementService userManagementService;

    public AdministratorController(UserView userView, UserManagementService userManagementService){
        this.userView = userView;
        this.userManagementService = userManagementService;

        userView.setAddUserButtonListener(new AddUserActionListener());
        userView.setRemoveUserButtonListener(new RemoveUserActionListener());
        userView.setUpdateUserButtonListener(new UpdateUserActionListener());
        userView.setShowReportForUserButtonListener(new ShowReportForUserActionListener());
    }
    private class AddUserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String userName = userView.getUserName();
                String userPassword = userView.getUserPassword();
                String userRole;// = userView.getUserRole();
                User user = new UserBuilder().setUsername(userName)
                        .setPassword(userPassword)
                        .build();

                Notification<Role> roleNotification = new Notification<>();
                if (userView.getAdministratorCheckBox().isSelected()) {
                    userRole = "administrator";
                } else {
                    userRole = "employee";
                }

                Notification<User> notification = userManagementService.addUser(user.getUsername(), user.getPassword(), userRole);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(userView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(userView.getContentPane(), "User addition not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(userView.getContentPane(), "User addition successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(userView.getContentPane(), "Fields must be initialized!");
            }
        }
    }

    private class RemoveUserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                User user = new UserBuilder().setId(userView.getUserId())
                        .build();

                Notification<User> notification = userManagementService.deleteUser(user);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(userView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(userView.getContentPane(), "User deletion not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(userView.getContentPane(), "User deletion successful!");
                    }
                }
            }catch(NumberFormatException nr){
                JOptionPane.showMessageDialog(userView.getContentPane(), "Fields must be initialized!");
            }
        }
    }

    private class UpdateUserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                User user = new UserBuilder().setId(userView.getUserId())
                        .build();
                User userUpdate = new UserBuilder().setUsername(userView.getUserName())
                        .setPassword(userView.getUserPassword())
                        .build();

                Notification<Boolean> notification = userManagementService.updateUser(user, userUpdate);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(userView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == Boolean.FALSE) {
                        JOptionPane.showMessageDialog(userView.getContentPane(), "User update not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(userView.getContentPane(), "User update successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(userView.getContentPane(), "Fields must be initialized!");
            }
        }
    }

    private class ShowReportForUserActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                User user = new UserBuilder().setId(userView.getUserId())
                        .build();
                User userUpdate = new UserBuilder().setUsername(userView.getUserName())
                        .setPassword(userView.getUserPassword())
                        .build();
                List<Operation> operations = userManagementService.getReportForUser(user.getId());

                userView.setTextArea(getFormattedOperations(operations));
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(userView.getContentPane(), "Fields must be initialized!");
            }
        }
    }
    private static String getFormattedOperations(List<Operation> operations){
       // List<Operation> operations = userManagementService.getReportForUser(user.getId());
        String str = "";
        for(int i = 0; i < operations.size();i++){
            str+=operations.get(i).getOperation() +"\n";
        }
        return str;
    }
}
