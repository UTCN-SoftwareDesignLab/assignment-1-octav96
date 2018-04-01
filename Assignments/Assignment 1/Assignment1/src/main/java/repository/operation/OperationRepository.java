package repository.operation;

import model.Operation;
import model.User;

import java.util.List;

public interface OperationRepository {
    public List<Operation> findAllForUser(User user);

    public boolean addOperation(Operation operation);

    public void deleteAll();
}
