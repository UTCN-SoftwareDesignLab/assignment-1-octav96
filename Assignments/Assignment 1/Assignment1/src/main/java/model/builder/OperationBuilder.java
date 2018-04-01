package model.builder;

import model.Operation;

import java.sql.Date;

public class OperationBuilder {

    private Operation operation;

    public OperationBuilder(){
        this.operation = new Operation();
    }

    public OperationBuilder setOperationId(Long id){
        this.operation.setId(id);
        return this;
    }

    public OperationBuilder setOperation(String operation){
        this.operation.setOperation(operation);
        return this;
    }

    public OperationBuilder setEmployeeId(Long employeeId){
        this.operation.setEmployeeId(employeeId);
        return this;
    }

    public OperationBuilder setDate(Date date){
        this.operation.setDate(date);
        return this;
    }

    public Operation build(){
        return this.operation;
    }

}
