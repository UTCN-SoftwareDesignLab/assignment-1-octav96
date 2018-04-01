package model.builder;

import model.Account;

import java.sql.Date;

public class AccountBuilder {
    private Account account;

    public AccountBuilder(){
        this.account = new Account();
    }
    public AccountBuilder setId(Long id){
        this.account.setId(id);
        return this;
    }

    public AccountBuilder setClientId(Long clientId){
        this.account.setClientId(clientId);
        return this;
    }

    public AccountBuilder setType(String accountType){
        this.account.setType(accountType);
        return this;
    }

    public AccountBuilder setAmount(Double amount){
        this.account.setAmount(amount);
        return this;
    }

    public AccountBuilder setCreationDate(Date date){
        this.account.setCreationDate(date);
        return this;
    }

    public Account build(){
        return this.account;
    }
}
