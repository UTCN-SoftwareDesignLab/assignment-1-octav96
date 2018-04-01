package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;

public class EmployeeView extends  JFrame{
    private JButton btnAdd;
    private JButton btnFindClient;
    private JButton btnRemoveClient;
    private JButton btnUpdateClient;
    private JButton btnShowClients;
    private JTextField tfClientId;
    private JTextField tfClientName;
    private JTextField tfClientIdentityCardNumber;
    private JTextField tfClientPersonalNumericCode;
    private JTextField tfClientAddress;
    private JScrollPane scrollPane;
    private JTable clientsTable;

    private JButton btnAddAccount;
    private JButton btnFindAccount;
    private JButton btnRemoveAccount;
    private JButton btnUpdateAccount;
    private JButton btnShowAccounts;
    private JTextField tfAccountId;
    private JTextField tfAccountClientId;
    private JTextField tfAccountType;
    private JTextField tfAccountAmount;
    private JTextField tfAccountCreationDate;
    private JScrollPane scrollPane1;
    private JTable accountsTable;







    public EmployeeView() throws HeadlessException{
        setSize(1000, 1000);
        //setLocationRelativeTo(null);
        initializeFields();
      //  JScrollPane scrollPane = new JScrollPane();
       // JPanel ppp = new JPanel();
      //  getContentPane().add
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
       // add(new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        add(scrollPane);
        add(new JLabel("CLIENT ID (FOR UPDATE AND DELETE)"));
        add(tfClientId);
        add(new JLabel("CLIENT NAME"));
        add(tfClientName);
        add(new JLabel("CLIENT IDENTITY CARD NUMBER"));
        add(tfClientIdentityCardNumber);
        add(new JLabel("CLIENT PERSONAL NUMERIC CODE"));
        add(tfClientPersonalNumericCode);
        add(new JLabel("CLIENT ADDRESS"));

        add(tfClientAddress);
       // btnAddClient.setMaximumSize(new Dimension(btnAddClient.getWidth()-10, btnAddClient.getHeight()));

       // add(btnAddClient);
        add(btnFindClient);
        add(btnRemoveClient);
        add(btnUpdateClient);
        add(btnShowClients);
       // add(scrollPane);
        //add(clientsTable);


        add(new JLabel("ACCOUNT ID (FOR UPDATE AND DELETE)"));
        add(tfAccountId);
        add(new JLabel("ACCOUNT CLIENT ID"));
        add(tfAccountClientId);
        add(new JLabel("ACCOUNT TYPE"));
        add(tfAccountType);
        add(new JLabel("ACCOUNT AMOUNT"));
        add(tfAccountAmount);
        add(new JLabel("ACCOUNT CREATION DATE"));
        add(tfAccountCreationDate);
        add(btnAddAccount);
        add(btnFindAccount);
        add(btnRemoveAccount);
        add(btnUpdateAccount);
        add(btnShowAccounts);
        add(scrollPane1);
        setVisible(true);





    }

    private void initializeFields(){
      //  btnAddClient = new JButton("ADD CLIENT");
        btnRemoveClient = new JButton("REMOVE CLIENT");
        btnUpdateClient = new JButton("UPDATE CLIENT");
        btnShowClients = new JButton("SHOW CLIENTS");
        btnFindClient = new JButton("FIND CLIENT");
        tfClientId = new JTextField();
        tfClientName = new JTextField();
        tfClientIdentityCardNumber = new JTextField();
        tfClientPersonalNumericCode = new JTextField();
        tfClientAddress = new JTextField();




        clientsTable = new JTable();
        scrollPane = new JScrollPane(clientsTable);
        Object[] clientsColumns ={"ID", "NAME", "IDENTITY_CARD_NUMBER","PERSONAL_NUMERIC_CODE","ADDRESS"};
        DefaultTableModel clientsTableModel = new DefaultTableModel();
        clientsTableModel.setColumnIdentifiers(clientsColumns);
        clientsTable.setModel(clientsTableModel);
        clientsTable.setVisible(true);
        clientsTable.setBackground(Color.YELLOW);
        clientsTable.setForeground(Color.BLACK);
      //  clientsTable.setRowHeight(20);
        clientsTable.setAutoResizeMode(MAXIMIZED_BOTH);


        btnAddAccount = new JButton("ADD ACCOUNT");
        btnRemoveAccount = new JButton("REMOVE ACCOUNT");
        btnUpdateAccount = new JButton("UPDATE ACCOUNT");
        btnShowAccounts = new JButton("SHOW ACCOUNTS");
        btnFindAccount = new JButton("FIND ACCOUNT");

        tfAccountId = new JTextField();
        tfAccountClientId = new JTextField();
        tfAccountType = new JTextField();
        tfAccountAmount = new JTextField();
        tfAccountCreationDate = new JTextField();



        accountsTable = new JTable();
        scrollPane1 = new JScrollPane(accountsTable);
        Object[] accountsColumns ={"ID", "CLIENT_ID", "TYPE","AMOUNT","CREATION_DATE"};
        DefaultTableModel accountsTableModel = new DefaultTableModel();
        accountsTableModel.setColumnIdentifiers(accountsColumns);
        accountsTable.setModel(accountsTableModel);
        accountsTable.setVisible(true);
        accountsTable.setBackground(Color.YELLOW);
        accountsTable.setForeground(Color.BLACK);
        //  clientsTable.setRowHeight(20);
        accountsTable.setAutoResizeMode(MAXIMIZED_BOTH);
    }

    public Long getClientId(){
        return Long.parseLong(tfClientId.getText());
    }
    public String getClientName(){
        return tfClientName.getText();
    }
    public int getClientIdentityCardNumber(){
        return Integer.parseInt(tfClientIdentityCardNumber.getText());
    }
    public String getClientPersonalNumericCode(){
        return tfClientPersonalNumericCode.getText();
    }
    public String getClientAddress(){
        return tfClientAddress.getText();
    }

    public void setAddClientButtonListener(ActionListener addClientButtonListener){
       // btnAddClient.addActionListener(addClientButtonListener);
    }
    public void setRemoveClientButtonListener(ActionListener removeClientButtonListener){
        btnRemoveClient.addActionListener(removeClientButtonListener);
    }
    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener){
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }
    public void setShowClientsButtonListener(ActionListener showClientsButtonListener){
        btnShowClients.addActionListener(showClientsButtonListener);
    }

}
