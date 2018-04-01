package repository.client;

import model.Client;
import model.builder.ClientBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;
import static database.Constants.Tables.CLIENT;

public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;
    private final AccountRepository accountRepository;
    public ClientRepositoryMySQL(Connection connection, AccountRepository accountRepository){
        this.connection = connection;
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        Client client;
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + CLIENT ;
            ResultSet clientResultSet = statement.executeQuery(sql);
            while(clientResultSet.next()){
                   client = new ClientBuilder().setId(clientResultSet.getLong(1)).
                            setName(clientResultSet.getString(2)).
                            setIdentityCardNumber(clientResultSet.getInt(3)).
                            setPersonalNumericCode(clientResultSet.getString(4)).
                            setAddress(clientResultSet.getString(5)).build();

                   client.setAccounts(accountRepository.findAllForClient(client));
                   clients.add(client);
                 //  System.out.println(clients.get(0).getPersonalNumericCode());

            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Client findById(Long id) {
        // List<Account> accounts = new ArrayList<>();
        Client client;// = new Client();
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + CLIENT  + " WHERE `id` = " + id;
            ResultSet clientResultSet = statement.executeQuery(sql);
            clientResultSet.next();
            client = new ClientBuilder().setId(clientResultSet.getLong(1)).
                    setName(clientResultSet.getString(2)).
                    setIdentityCardNumber(clientResultSet.getInt(3)).
                    setPersonalNumericCode(clientResultSet.getString(4)).
                    setAddress(clientResultSet.getString(5)).build();

            return client;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addClient(Client client) {
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO " + CLIENT +" values (null, ?, ?, ?, ?)");
            preparedStatement.setString(1, client.getName());
            preparedStatement.setInt(2, client.getIdentityCardNumber());
            preparedStatement.setString(3, client.getPersonalNumericCode());
            preparedStatement.setString(4,client.getAddress());
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }


    }

    @Override
    public void deleteClient(Long id) {

        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + CLIENT + " WHERE id = " + id;
            statement.executeUpdate(sql);
            sql = "DELETE FROM " + ACCOUNT + " WHERE `clientId` = " + id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateClient(Client oldClient, Client newClient) {
        this.deleteClient(oldClient.getId());

        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO " + CLIENT + " values (" + oldClient.getId() +", ?, ?, ?, ?)");

            preparedStatement.setString(1, newClient.getName());
            preparedStatement.setInt(2, newClient.getIdentityCardNumber());
            preparedStatement.setString(3, newClient.getPersonalNumericCode());
            preparedStatement.setString(4, newClient.getAddress());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(){
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + CLIENT;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
