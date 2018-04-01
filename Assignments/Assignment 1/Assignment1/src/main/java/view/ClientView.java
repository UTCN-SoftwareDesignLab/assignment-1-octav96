package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class ClientView extends JFrame {
    private JButton btnAddClient;
    private JButton btnFindClient;
    private JButton btnRemoveClient;
    private JButton btnUpdateClient;
    private JButton btnShowClients;
    private JTextField tfClientId;
    private JTextField tfClientName;
    private JTextField tfClientIdentityCardNumber;
    private JTextField tfClientPersonalNumericCode;
    private JTextField tfClientAddress;


    public ClientView() throws HeadlessException {
        setSize(1000, 500);

        initializeFields();

        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
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
        add(btnAddClient);
        add(btnFindClient);
        add(btnRemoveClient);
        add(btnUpdateClient);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void initializeFields() {
        btnAddClient = new JButton("ADD CLIENT");
        btnRemoveClient = new JButton("REMOVE CLIENT");
        btnUpdateClient = new JButton("UPDATE CLIENT");
        btnShowClients = new JButton("SHOW CLIENTS");
        btnFindClient = new JButton("FIND CLIENT");
        tfClientId = new JTextField();
        tfClientName = new JTextField();
        tfClientIdentityCardNumber = new JTextField();
        tfClientPersonalNumericCode = new JTextField();
        tfClientAddress = new JTextField();
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
        btnAddClient.addActionListener(addClientButtonListener);
    }
    public void setFindClientButtonListener(ActionListener findClientButtonListener){
        btnFindClient.addActionListener(findClientButtonListener);
    }
    public void setRemoveClientButtonListener(ActionListener removeClientButtonListener){
        btnRemoveClient.addActionListener(removeClientButtonListener);
    }
    public void setUpdateClientButtonListener(ActionListener updateClientButtonListener){
        btnUpdateClient.addActionListener(updateClientButtonListener);
    }


}
