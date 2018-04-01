package service.account;

import model.Account;
import model.Client;
import model.Operation;
import model.builder.OperationBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.operation.OperationRepository;

import java.sql.Date;
import java.util.List;

public class AccountManagementServiceMySQL implements AccountManagementService {
    private AccountRepository accountRepository;
    private ClientRepository clientRepository;
    private OperationRepository operationRepository;
    private Long userId;
    public AccountManagementServiceMySQL(AccountRepository accountRepository,
                                         ClientRepository clientRepository,
                                         OperationRepository operationRepository){
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
        this.operationRepository = operationRepository;;
    }

    @Override
    public Notification<Account> addMoney(Account account, Double amount) {
       Notification<Account> notification = new Notification<>();
       Account auxAccount = accountRepository.findById(account.getId());
       AccountValidator accountValidator = new AccountValidator(account);
       List<Account> accounts = accountRepository.findAll();

       boolean verify = accountValidator.validate(amount, accounts);
       if(!verify){
           accountValidator.getErrors().forEach(notification::addError);
           notification.setResult(null);
           return notification;
       }else {
           auxAccount.setAmount(account.getAmount() + amount);
           accountRepository.updateAccount(account, auxAccount);
           Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                   " added  " + amount + " to account with id = " + account.getId())
                   .setEmployeeId(userId).build();
           operationRepository.addOperation(operation);
           notification.setResult(auxAccount);
           return notification;
       }
    }

    @Override
    public Notification<Account> takeMoney(Account account, Double amount) {

        Account auxAccount = accountRepository.findById(account.getId());
        Notification<Account> notification = new Notification<>();
        AccountValidator accountValidator = new AccountValidator(auxAccount);
        List<Account> accounts = accountRepository.findAll();
        boolean verify = accountValidator.validate(amount, accounts);

        if(!verify) {
            accountValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        } else {

            auxAccount.setAmount(auxAccount.getAmount() - amount);
            notification.setResult(auxAccount);
            accountRepository.updateAccount(account, auxAccount);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " removed  " + amount + " from account with id = " + account.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);

            return notification;
        }
    }
    @Override
    public Notification<Account> deleteAccount(Account account){
        Notification<Account> notification = new Notification<>();
        AccountValidator accountValidator = new AccountValidator(account);
        List<Account> accounts = accountRepository.findAll();
        boolean verify = accountValidator.validateExistance(accounts);
        if(!verify) {
            accountValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        } else {
            notification.setResult(account);
            accountRepository.deleteAccount(account.getId());
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " deleted account with id = " + account.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);

            return notification;
        }
    }
    @Override
    public Notification<Account> addAccount(Client client, Account account){
        Notification<Account> notification = new Notification<>();
       // AccountValidator accountValidator = new AccountValidator(account);
        ClientValidator clientValidator = new ClientValidator(client);
        List<Account> accounts = accountRepository.findAll();
        List<Client> clients = clientRepository.findAll();
        //account.setCreationDate(new Date());
        //account.setCreationDate(Date);
        boolean verify = clientValidator.validateExistence(clients);
        if(!verify) {
           // accountValidator.getErrors().forEach(notification::addError);
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        } else {
            notification.setResult(account);
            accountRepository.addAccount(client, account);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " added account to client with id = " + client.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);

            return notification;
        }
    }
    public List<Account> findAllAccounts(){
        return accountRepository.findAll();
    }

    @Override
    public Notification<Account> findAccount(Account account){
        Notification<Account> notification = new Notification<>();

        AccountValidator accountValidator = new AccountValidator(account);
       // ClientValidator clientValidator = new ClientValidator(client);
        List<Account> accounts = accountRepository.findAll();
        boolean verify = accountValidator.validateExistance(accounts);
        if(!verify) {
            // accountValidator.getErrors().forEach(notification::addError);
            accountValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        } else {
           // Account auxAccount = accountRepository.findById(account.getId());
            notification.setResult(accountRepository.findById(account.getId()));
            return notification;
        }
    }


    @Override
    public Notification<Boolean> transferMoney(Account account1, Account account2, Double amount){

        Notification<Boolean> notification = new Notification<>();
        Account auxAccount1 = accountRepository.findById(account1.getId());
        Account auxAccount2 = accountRepository.findById(account2.getId());
        AccountValidator accountValidator1 = new AccountValidator(auxAccount1);
        AccountValidator accountValidator2 = new AccountValidator(auxAccount2);
        List<Account> accounts = accountRepository.findAll();
        boolean verify = accountValidator1.validate(amount, accounts) && accountValidator2.validateExistance(accounts);


        if(!verify){
            accountValidator1.getErrors().forEach(notification::addError);
            accountValidator2.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        }{
            notification.setResult(Boolean.TRUE);
            auxAccount1.setAmount(auxAccount1.getAmount() - amount);
            auxAccount2.setAmount(auxAccount2.getAmount() + amount);
            accountRepository.updateAccount(account1, auxAccount1);
            accountRepository.updateAccount(account2, auxAccount2);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " transfered  " + amount + " from account" + account1.getId() + " to account with id = " + account2.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);

            return notification;
        }

    }

    @Override
    public void setUserId(Long id) {
        this.userId = id;
    }
}
