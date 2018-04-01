package repository.security;

import model.Right;
import model.Role;
import model.User;
import model.validation.Notification;

import java.util.List;

/**
 * Created by Alex on 11/03/2017.
 */
public interface RightsRolesRepository {

    void addRole(String role);

    void addRight(String right);

    Notification<Role> findRoleByTitle(String role);

    Role findRoleById(Long roleId);

    Right findRightByTitle(String right);

    void addRolesToUser(User user, List<Role> roles);

    List<Role> findRolesForUser(Long userId);

    void addRoleRight(Long roleId, Long rightId);

    void deleteRolesFromUser(Long id);

    List<Long> findRoleIdsForUser(Long userId);

    void deleteAllRoles();

    void deleteAllUserRole();

}
