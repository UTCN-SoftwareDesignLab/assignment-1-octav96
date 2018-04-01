package service.user;

import model.Operation;
import model.Role;
import model.User;
import model.builder.OperationBuilder;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.operation.OperationRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.CUSTOMER;

public class UserManagementServiceMySQL implements UserManagementService{


    private UserRepository userRepository;
    private RightsRolesRepository rightsRolesRepository;
    private OperationRepository operationRepository;
    private Long userId;

    public UserManagementServiceMySQL(UserRepository userRepository,
                                      RightsRolesRepository rightsRolesRepository,
                                      OperationRepository operationRepository ) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.operationRepository = operationRepository;
    }
    @Override
    public Notification<User> deleteUser(User user) {
        Notification<User> notification = new Notification<>();
        UserValidator userValidator = new UserValidator(user);
        List<User> users = userRepository.findAll();
        Boolean verify = userValidator.validateExistence(users);

        if(!verify){
            userValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        }else{
            userRepository.deleteUser(user.getId());
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " deleted " + "user with id = " + user.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);
            notification.setResult(user);
            return notification;
        }
    }

    @Override
    public Notification<User> addUser(String username, String password, String role) {
        Notification<Role> customerRole = rightsRolesRepository.findRoleByTitle(role);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(customerRole.getResult()))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<User> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(user);
            return userRegisterNotification;
        } else {
            user.setPassword(encodePassword(password));
            userRepository.save(user);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " added a new user.")
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);

            //   rightsRolesRepository.addRolesToUser(user,Collections.singletonList(customerRole.getResult()));
            userRegisterNotification.setResult(user);
            return userRegisterNotification;
        }
    }

    @Override
    public Notification<User> findUser(Long id) {
        Notification<User> notification = new Notification<>();
        UserValidator userValidator = new UserValidator(new UserBuilder().setId(id).build());
        List<User> clients = userRepository.findAll();
        Boolean verify = userValidator.validateExistence(clients);
        if(!verify){
            userValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        }else{
            notification.setResult(userRepository.findById(id));
            return notification;
        }
    }

    @Override
    public Notification<Boolean> updateUser(User user1, User user2) {
        Notification<Boolean> notification = new Notification<>();
        UserValidator clientValidator1 = new UserValidator(user1);
        //ClientValidator clientValidator2 = new ClientValidator(client2);

        List<User> users = userRepository.findAll();
        Boolean verify = clientValidator1.validateExistence(users);// && clientValidator2.validateUniqueness(clients);
        if(!verify){
            clientValidator1.getErrors().forEach(notification::addError);
            // clientValidator2.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        }else{
            user2.setPassword(encodePassword(user2.getPassword()));
            userRepository.updateUser(user1, user2);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " updated " + "user with id = " + user1.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);

            notification.setResult(Boolean.TRUE);
            return notification;
        }
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public List<Operation> getReportForUser(Long id){

        List<Operation> operations = operationRepository.findAllForUser(new UserBuilder().setId(id).build());
        return operations;
    }
    public void setUserId(Long id){
        this.userId = id;
    }
}
