package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Date;

import static javax.swing.BoxLayout.Y_AXIS;

public class AccountView extends JFrame {

    private JButton btnAddAccount;
    private JButton btnFindAccount;
    private JButton btnRemoveAccount;
   // private JButton btnUpdateAccount;
    private JButton btnTakeMoneyFromAccount;
    private JButton btnAddMoneyToAccount;
    private JButton btnTransferMoney;
    // private JButton btnShowAccounts;
    private JTextField tfAccountId;
    private JTextField tfAccountId1;
    private JTextField tfTransactionAmount;
    private JTextField tfAccountClientId;
    private JTextField tfAccountType;
    private JTextField tfAccountAmount;
  //  private JTextField tfAccountCreationDate;

    public AccountView(){
        setSize(1000, 500);

        initializeFields();

        setLayout(new BoxLayout(getContentPane(), Y_AXIS));

        add(new JLabel("ACCOUNT ID 1(FOR UPDATE AND DELETE AND TRANSFER)"));
        add(tfAccountId);
        add(new JLabel("ACCOUNT ID 2(FOR TRANSFER)"));
        add(tfAccountId1);
        add(new JLabel("TRANSACTION AMOUNT"));
        add(tfTransactionAmount);

        add(new JLabel("ACCOUNT CLIENT ID"));
        add(tfAccountClientId);
        add(new JLabel("ACCOUNT TYPE"));
        add(tfAccountType);
        add(new JLabel("ACCOUNT AMOUNT"));
        add(tfAccountAmount);
       // add(new JLabel("ACCOUNT CREATION DATE"));
        //add(tfAccountCreationDate);
        add(btnAddAccount);
        add(btnFindAccount);
        add(btnRemoveAccount);
        add(btnTakeMoneyFromAccount);
        add(btnAddMoneyToAccount);
        add(btnTransferMoney);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void initializeFields(){
        btnAddAccount = new JButton("ADD ACCOUNT");
        btnRemoveAccount = new JButton("REMOVE ACCOUNT");
        btnAddMoneyToAccount = new JButton("ADD MONEY TO ACCOUNT");
        btnTakeMoneyFromAccount = new JButton("TAKE MONEY FROM ACCOUNT");
        btnTransferMoney = new JButton("TRANSFER MONEY");

        // btnShowAccounts = new JButton("SHOW ACCOUNTS");
        btnFindAccount = new JButton("FIND ACCOUNT");

        tfAccountId = new JTextField();
        tfAccountId1 = new JTextField();

        tfAccountClientId = new JTextField();
        tfAccountType = new JTextField();
        tfAccountAmount = new JTextField();
        //tfAccountCreationDate = new JTextField();
        tfTransactionAmount = new JTextField();

    }
    public Long getAccountId(){
        return Long.parseLong(tfAccountId.getText());
    }
    public Long getAccountId1(){
        return Long.parseLong(tfAccountId1.getText());
    }
    public Long getAccountClientId(){
        return Long.parseLong(tfAccountClientId.getText());
    }
    public String getAccountType(){
        return tfAccountType.getText();
    }
    public Double getAccountAmount(){
        return Double.parseDouble(tfAccountAmount.getText());
    }

    public Double getTransactionAmount(){
        return Double.parseDouble(tfTransactionAmount.getText());
    }

    public void setAddAccountButtonListener(ActionListener updateAccountButtonListener){
        btnAddAccount.addActionListener(updateAccountButtonListener);
    }
    public void setFindClientButtonListener(ActionListener updateAccountButtonListener){
        btnFindAccount.addActionListener(updateAccountButtonListener);
    }
    public void setRemoveAccountButtonListener(ActionListener updateAccountButtonListener){
        btnRemoveAccount.addActionListener(updateAccountButtonListener);
    }
    public void setAddMoneyToAccountButtonListener(ActionListener updateAccountButtonListener){
        btnAddMoneyToAccount.addActionListener(updateAccountButtonListener);
    }
    public void setTakeMoneyToAccountButtonListener(ActionListener updateAccountButtonListener){
        btnTakeMoneyFromAccount.addActionListener(updateAccountButtonListener);
    }
    public void setTransferMoneyButtonListener(ActionListener updateAccountButtonListener){
        btnTransferMoney.addActionListener(updateAccountButtonListener);
    }

    public void setFindAccountButtonListener(ActionListener updateAccountButtonListener){
        btnFindAccount.addActionListener(updateAccountButtonListener);
    }

}
