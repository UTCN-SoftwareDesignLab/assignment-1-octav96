package service.account;

import model.Account;
import model.Client;
import model.validation.Notification;

import java.util.List;

public interface AccountManagementService {
    public Notification<Account> addMoney(Account account, Double amount);
    public Notification<Account> takeMoney(Account account, Double amount);
    public Notification<Boolean> transferMoney(Account account1, Account account2, Double amount);
    public Notification<Account> deleteAccount(Account account);
    public Notification<Account> addAccount(Client client, Account account);
    public List<Account> findAllAccounts();
    public Notification<Account> findAccount(Account account);
    public void setUserId(Long id);

}
