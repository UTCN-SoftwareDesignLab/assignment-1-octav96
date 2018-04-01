package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class UserView extends JFrame{
    private JButton btnAddUser;
    //private JButton btnFindUser;
    private JButton btnRemoveUser;
    private JButton btnUpdateUser;
    private JButton btnShowReportForUser;


    private JTextField tfUserId;
    private JTextField tfUserName;
    private JTextField tfUserPassword;
    private JTextField tfUserRole;
    private JTextArea textArea;
    private JCheckBox administrator;
  //  private JCheckBox employee;


    public UserView() throws HeadlessException {
        setSize(1000, 500);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(new JLabel("USER ID"));
        add(tfUserId);
        add(new JLabel("USER NAME"));
        add(tfUserName);
        add(new JLabel("USER PASSWORD"));
        add(tfUserPassword);
        //add(new JLabel("USER ROLE"));
        //add(tfUserRole);
        add(administrator);
        //add(employee);
        add(btnAddUser);
        add(btnRemoveUser);
        add(btnUpdateUser);
        add(btnShowReportForUser);
        add(new JLabel("REPORT"));
        add(textArea);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        setVisible(true);
    }
    private void initializeFields(){
        btnAddUser = new JButton("ADD USER");
       // JButton btnFindUser = new JButton();
        btnRemoveUser = new JButton("REMOVE USER");
        btnUpdateUser = new JButton("UPDATE USER");
        btnShowReportForUser = new JButton("SHOW REPORT FOR USER");
        administrator = new JCheckBox("ADMINISTRATOR");
      //  employee = new JCheckBox("EMPLOYEE");
        tfUserId = new JTextField();
        tfUserName = new JTextField();
        tfUserPassword = new JTextField();
        tfUserRole = new JTextField();
        textArea = new JTextArea();
        textArea.setEditable(false);

    }

    public Long getUserId(){
        return Long.parseLong(tfUserId.getText());
    }

    public String getUserName(){
        return tfUserName.getText();
    }

    public String getUserPassword(){
        return tfUserPassword.getText();
    }

    public String getUserRole(){
        return tfUserRole.getText();
    }

    public JCheckBox getAdministratorCheckBox(){
        return this.administrator;
    }

    public void setTextArea(String text){
        this.textArea.setText(text);
    }


    public void setAddUserButtonListener(ActionListener addUserButtonListener){
        btnAddUser.addActionListener(addUserButtonListener);
    }
    public void setRemoveUserButtonListener(ActionListener removeUserButtonListener){
        btnRemoveUser.addActionListener(removeUserButtonListener);
    }

    public void setUpdateUserButtonListener(ActionListener updateUserButtonListener){
        btnUpdateUser.addActionListener(updateUserButtonListener);
    }

    public void setShowReportForUserButtonListener(ActionListener showReportForUserButtonListener){
        btnShowReportForUser.addActionListener(showReportForUserButtonListener);
    }
}
