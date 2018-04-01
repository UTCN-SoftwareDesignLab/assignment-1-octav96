package repository.operation;

import model.Operation;
import model.User;
import model.builder.OperationBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static database.Constants.Tables.CLIENT;
import static database.Constants.Tables.OPERATION;

public class OperationRepositoryMySQL implements  OperationRepository{
    private final Connection connection;

    public OperationRepositoryMySQL(Connection connection){
        this.connection = connection;
    }
    @Override
    public List<Operation> findAllForUser(User user) {
        List<Operation> operations = new ArrayList<>();
        Operation operation;
        try {

            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + OPERATION  + " WHERE `employeeId` = " + user.getId();
            ResultSet operationResultSet = statement.executeQuery(sql);
            while(operationResultSet.next()){
                operation = new OperationBuilder().setOperationId(operationResultSet.getLong(1))
                        .setOperation(operationResultSet.getString(2))
                        .setEmployeeId(operationResultSet.getLong(3))
                        .setDate(operationResultSet.getDate(4)).build();

                operations.add(operation);
            }
            return operations;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean addOperation(Operation operation) {
        try {
            Calendar calendar = Calendar.getInstance();
            java.util.Date currentDate = calendar.getTime();
            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO " + OPERATION +" values (null, ?, ?, ?)");
            preparedStatement.setString(1, operation.getOperation());
            preparedStatement.setLong(2, operation.getEmployeeId());
            preparedStatement.setDate(3, new java.sql.Date(currentDate.getTime()));
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }
    public void deleteAll(){
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM " + OPERATION;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
