package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator {
    private final Account account;
    private final List<String> errors;

    public AccountValidator(Account account){
        this.account = account;
        this.errors = new ArrayList<>();
    }

    public List<String> getErrors(){
        return errors;
    }

    public boolean validateExistance(List<Account> accounts){
        for(int i = 0;i < accounts.size();i++){
            if(accounts.get(i).getId() == this.account.getId()) {
                return true;
            }
        }
        errors.add("Account with id = " + this.account.getId() + " does not exist!");
        return false;
    }

    public boolean validateTransaction(Double amount){
        if(this.account.getType().equals("DEBIT") && (this.account.getAmount() - amount < 0)) {
            errors.add("Invalid transaction!");
            return false;
        }
        return true;

    }

    public boolean validate(Double amount, List<Account> accounts){
        validateExistance(accounts);
        validateTransaction(amount);
        return errors.isEmpty();
    }
}
