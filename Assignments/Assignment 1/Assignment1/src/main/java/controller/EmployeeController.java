package controller;

import model.Account;
import model.Client;
import model.User;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import model.validation.Notification;
import service.account.AccountManagementService;
import service.client.ClientManagementService;
import view.AccountView;
import view.ClientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class EmployeeController {
    private ClientView clientView;
    private AccountView accountView;
    private ClientManagementService clientManagementService;
    private AccountManagementService accountManagementService;

    public EmployeeController(ClientView clientView, AccountView accountView,
                              ClientManagementService clientManagementService,
                                AccountManagementService accountManagementService){
        this.clientView = clientView;
        this.accountView = accountView;
        this.clientManagementService = clientManagementService;
        this.accountManagementService = accountManagementService;
        clientView.setAddClientButtonListener(new AddClientActionListener());
        clientView.setRemoveClientButtonListener(new DeleteClientActionListener());
       // clientView.setShowClientsButtonListener(new ShowClientsActionListener());
        clientView.setFindClientButtonListener(new FindClientActionListener());
        clientView.setUpdateClientButtonListener(new UpdateClientActionListener());


        accountView.setAddAccountButtonListener(new AddAccountActionListener());

        accountView.setRemoveAccountButtonListener(new DeleteAccountActionListener());
        accountView.setAddMoneyToAccountButtonListener(new AddMoneyActionListener());
        accountView.setTakeMoneyToAccountButtonListener(new TakeMoneyActionListener());
        accountView.setTransferMoneyButtonListener(new TransferMoneyActionListener());
        accountView.setFindAccountButtonListener(new FindAccountActionListener());
    }

    private class AddClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String clientName = clientView.getClientName();
                int clientIdentityCardNumber = clientView.getClientIdentityCardNumber();
                String clientPersonalNumericCode = clientView.getClientPersonalNumericCode();
                String clientAddress = clientView.getClientAddress();

                Client client = new ClientBuilder().setName(clientName)
                        .setIdentityCardNumber(clientIdentityCardNumber)
                        .setPersonalNumericCode(clientPersonalNumericCode)
                        .setAddress(clientAddress)
                        .build();

                Notification<Client> notification = clientManagementService.addClient(client);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client addition not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client addition successful!");
                    }
                }
            }catch(NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }
    private class DeleteClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long clientId = clientView.getClientId();
                Client client = new ClientBuilder().setId(clientId)
                        .build();

                Notification<Client> notification = clientManagementService.deleteClient(client);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client deletion not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client deletion successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }
    private class FindClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long clientId = clientView.getClientId();
                Client client = new ClientBuilder().setId(clientId)
                        .build();

                Notification<Client> notification = clientManagementService.findClient(client.getId());
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client search not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client search successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }

        }
    }
    private class UpdateClientActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long clientId = clientView.getClientId();

                Notification<Client> notification = clientManagementService.findClient(clientId);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client update not successful, please try again later.");
                    } else {

                        Client updateClient;

                        updateClient = new ClientBuilder().setName(clientView.getClientName())
                                .setIdentityCardNumber(clientView.getClientIdentityCardNumber())
                                .setPersonalNumericCode(clientView.getClientPersonalNumericCode())
                                .setAddress(clientView.getClientAddress())
                                .build();
                        Client client = new ClientBuilder().setId(clientId).build();
                        clientManagementService.updateClient(client, updateClient);
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Client update successful!");


                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }

        }
    }

    private class AddAccountActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long clientId = accountView.getAccountClientId();
                String type = accountView.getAccountType();
                Double amount = accountView.getAccountAmount();
                //String clientAddress = clientView.getClientAddress();

                Account account = new AccountBuilder().setClientId(clientId)
                        .setType(type)
                        .setAmount(amount)
                        .build();
                Client client = new ClientBuilder().setId(clientId).build();
                Notification<Account> notification = accountManagementService.addAccount(client, account);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Account addition not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Account addition successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }
    private class DeleteAccountActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long accountId = accountView.getAccountId();

                //String clientAddress = clientView.getClientAddress();

                Account account = new AccountBuilder().setId(accountId).build();
                // Client client = new ClientBuilder().setId(clientId).build();
                Notification<Account> notification = accountManagementService.deleteAccount(account);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Account deletion not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Account deletion successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }

    private class TakeMoneyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long accountId = accountView.getAccountId();
                Double money = accountView.getTransactionAmount();


                Account account = new AccountBuilder().setId(accountId).build();
                // Client client = new ClientBuilder().setId(clientId).build();
                Notification<Account> notification = accountManagementService.takeMoney(account, money);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Transaction not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Transaction successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }

    private class AddMoneyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long accountId = accountView.getAccountId();
                Double money = accountView.getTransactionAmount();


                Account account = new AccountBuilder().setId(accountId).build();
                //Account account = accountManagementService.findAllAccounts()
                // Client client = new ClientBuilder().setId(clientId).build();
                Notification<Account> notification = accountManagementService.findAccount(account);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Transaction not successful, please try again later.");
                    } else {
                        Notification<Account> notification1 = accountManagementService.addMoney(notification.getResult(), money);
                        if (notification1.getResult() == null) {
                            JOptionPane.showMessageDialog(clientView.getContentPane(), "Transaction not successful, please try again later.");
                        } else {
                            JOptionPane.showMessageDialog(clientView.getContentPane(), "Transaction successful!");
                        }
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }

    private class TransferMoneyActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long accountId = accountView.getAccountId();
                Double money = accountView.getTransactionAmount();
                Long accountId1 = accountView.getAccountId1();


                Account account = new AccountBuilder().setId(accountId).build();
                Account account1 = new AccountBuilder().setId(accountId1).build();
                // Client client = new ClientBuilder().setId(clientId).build();
                Notification<Boolean> notification = accountManagementService.transferMoney(account, account1, money);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == Boolean.FALSE) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Transaction not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Transaction successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }
    private class FindAccountActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Long accountId = accountView.getAccountId();

                //String clientAddress = clientView.getClientAddress();

                Account account = new AccountBuilder().setId(accountId).build();
                Notification<Account> notification = accountManagementService.findAccount(account);
                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(clientView.getContentPane(), notification.getFormattedErrors());
                } else {
                    if (notification.getResult() == null) {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Account search not successful, please try again later.");
                    } else {
                        JOptionPane.showMessageDialog(clientView.getContentPane(), "Account search successful!");
                    }
                }
            }catch (NumberFormatException nr){
                JOptionPane.showMessageDialog(clientView.getContentPane(), "Fields must be initialized!");
            }
        }
    }
}
