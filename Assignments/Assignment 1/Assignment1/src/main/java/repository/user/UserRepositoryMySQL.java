package repository.user;

import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.USER;
import static database.Constants.Tables.USER_ROLE;

/**
 * Created by Alex on 11/03/2017.
 */
public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        User user = new User();
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + USER ;
            ResultSet userResultSet = statement.executeQuery(sql);
            while(userResultSet.next()){
                user = new UserBuilder().setId(userResultSet.getLong(1))
                        .setUsername(userResultSet.getString(2))
                        .setPassword(userResultSet.getString(3))
                        .build();

                user.setRoles(rightsRolesRepository.findRolesForUser(user.getId()));
                users.add(user);
                //  System.out.println(clients.get(0).getPersonalNumericCode());

            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findById(Long id) {
       try {
           Statement statement = connection.createStatement();
           String fetchUserSql = "Select * from `" + USER + "` where `id` = " + id;
           //System.out.print(id);
           ResultSet userResultSet = statement.executeQuery(fetchUserSql);

           if (userResultSet.next()){
               User user = new UserBuilder()
                       .setId(userResultSet.getLong("id"))
                       .setUsername(userResultSet.getString("username"))
                       .setPassword(userResultSet.getString("password"))
                       .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                       .build();
               return user;

           }
           return null;

        } catch (SQLException e) {
            e.printStackTrace();
           return null;
        }

    }


    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new AuthenticationException();
        }
    }

    @Override
    public boolean save(User user) {

        try {

            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)");
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);
            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void deleteUser(Long id){
        try {
            rightsRolesRepository.deleteRolesFromUser(id);

            Statement statement = connection.createStatement();
            String  sql = "DELETE from user where `id` = " + id;
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateUser(User oldUser, User newUser){
        this.deleteUser(oldUser.getId());
        try {
           newUser.setId(oldUser.getId());
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user values ("+ oldUser.getId() +", ?, ?)");

            statement.setString(1, newUser.getUsername());
            statement.setString(2, newUser.getPassword());
            statement.executeUpdate();

//            rightsRolesRepository.addRolesToUser(newUser, oldUser.getRoles());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from `user` where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
