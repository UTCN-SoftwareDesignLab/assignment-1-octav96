package repository.account;

import model.Account;
import model.Client;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    Account findById(Long id);

    List<Account> findAllForClient(Client client);

    boolean addAccount(Client client, Account account);

    void deleteAccount(Long id);

    void deleteAll();

    void updateAccount(Account account1, Account account2);

}
