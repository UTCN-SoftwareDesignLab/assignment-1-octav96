package repository.account;

import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import repository.operation.OperationRepository;

import  java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository{

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        Account account;
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + ACCOUNT ;
            ResultSet accountResultSet = statement.executeQuery(sql);
            while(accountResultSet.next()){
                account = new AccountBuilder().setId(accountResultSet.getLong(1)).
                        setClientId(accountResultSet.getLong(2)).
                        setType(accountResultSet.getString(3)).
                        setAmount(accountResultSet.getDouble(4)).
                        setCreationDate(accountResultSet.getDate(5)).build();

                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Account> findAllForClient(Client client) {
        List<Account> accounts = new ArrayList<>();
        Account account;
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + ACCOUNT  + " WHERE `clientId` = " + client.getId();
            ResultSet accountResultSet = statement.executeQuery(sql);
            while(accountResultSet.next()){
                account = new AccountBuilder().setId(accountResultSet.getLong(1)).
                        setClientId(accountResultSet.getLong(2)).
                        setType(accountResultSet.getString(3)).
                        setAmount(accountResultSet.getDouble(4)).
                        setCreationDate(accountResultSet.getDate(5)).build();
                accounts.add(account);

            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Account findById(Long id) {
        Account account = new Account();
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + ACCOUNT  + " WHERE `id` = " + id;
            ResultSet accountResultSet = statement.executeQuery(sql);
            accountResultSet.next();
                account = new AccountBuilder().setId(accountResultSet.getLong(1)).
                        setClientId(accountResultSet.getLong(2)).
                        setType(accountResultSet.getString(3)).
                        setAmount(accountResultSet.getDouble(4)).
                        setCreationDate(accountResultSet.getDate(5)).build();

            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addAccount(Client client, Account account) {
        try {
            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO " + ACCOUNT +" values (null, ?, ?, ?, ?)");
            preparedStatement.setLong(1, account.getClientId());
            preparedStatement.setString(2, account.getType());
            preparedStatement.setDouble(3, account.getAmount());
            preparedStatement.setDate(4, new java.sql.Date(currentDate.getTime()));
            preparedStatement.executeUpdate();


            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    @Override
    public void deleteAccount(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + ACCOUNT + " WHERE id = " + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account1, Account account2) {
        this.deleteAccount(account1.getId());
        try {
            account2.setId(account1.getId());
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO " + ACCOUNT + " values ("+ account1.getId() +", ?, ?, ?, ?)");

            statement.setLong(1, account2.getClientId());
            statement.setString(2, account2.getType());
            statement.setDouble(3, account2.getAmount());
            statement.setDate(4, account2.getCreationDate());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + ACCOUNT;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
