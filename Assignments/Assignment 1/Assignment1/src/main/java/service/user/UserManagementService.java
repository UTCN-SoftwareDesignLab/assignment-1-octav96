package service.user;

import model.Operation;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface UserManagementService {
    public Notification<User> deleteUser(User user);

    public Notification<User> addUser(String username, String password, String role);

    public Notification<User> findUser(Long id);

    public Notification<Boolean> updateUser(User user1, User user2);

    public void setUserId(Long id);

    public List<Operation> getReportForUser(Long id);
}
