package service.client;

import model.Account;
import model.Client;
import model.Operation;
import model.builder.ClientBuilder;
import model.builder.OperationBuilder;
import model.validation.AccountValidator;
import model.validation.ClientValidator;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.client.ClientRepository;
import repository.operation.OperationRepository;
import repository.operation.OperationRepositoryMySQL;

import java.util.List;

public class ClientManagementServiceMySQL implements ClientManagementService {

    private ClientRepository clientRepository;
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;
    private Long userId;

    public ClientManagementServiceMySQL(ClientRepository clientRepository,
                                        AccountRepository accountRepository,
                                        OperationRepository operationRepository){
        this.clientRepository = clientRepository;
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    public Notification<Client> deleteClient(Client client) {
        Notification<Client> notification = new Notification<>();
        ClientValidator clientValidator = new ClientValidator(client);
        List<Client> clients = clientRepository.findAll();
        Boolean verify = clientValidator.validateExistence(clients);

        if(!verify){
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        }else{
            clientRepository.deleteClient(client.getId());
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " deleted " + "client with id = " + client.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);

            notification.setResult(client);
            return notification;
        }

    }

    @Override
    public Notification<Client> addClient(Client client) {
        Notification<Client> notification = new Notification<>();
        ClientValidator clientValidator = new ClientValidator(client);
        List<Client> clients = clientRepository.findAll();
        Boolean verify = clientValidator.validateUniqueness(clients);
        if(!verify){
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        }else{
            clientRepository.addClient(client);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " added " + "client.")
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);
            notification.setResult(client);
            return notification;
        }
    }

    @Override
    public Notification<Client> findClient(Long id) {
        Notification<Client> notification = new Notification<>();
        ClientValidator clientValidator = new ClientValidator(new ClientBuilder().setId(id).build());
        List<Client> clients = clientRepository.findAll();
        Boolean verify = clientValidator.validateExistence(clients);
        if(!verify){
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        }else{
            notification.setResult(clientRepository.findById(id));
            return notification;
        }
    }

    @Override
    public Notification<Boolean> updateClient(Client client1, Client client2) {
        Notification<Boolean> notification = new Notification<>();
        ClientValidator clientValidator1 = new ClientValidator(client1);
        //ClientValidator clientValidator2 = new ClientValidator(client2);

        List<Client> clients = clientRepository.findAll();
        Boolean verify = clientValidator1.validateExistence(clients);// && clientValidator2.validateUniqueness(clients);
        if(!verify){
            clientValidator1.getErrors().forEach(notification::addError);
           // clientValidator2.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        }else{
            clientRepository.updateClient(client1, client2);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " updated " + "client with id = " + client1.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);
            notification.setResult(Boolean.TRUE);
            return notification;
        }
    }

    @Override
    public Notification<Boolean> addAccount(Client client, Account account) {
        Notification<Boolean> notification = new Notification<>();
        ClientValidator clientValidator = new ClientValidator(client);

        List<Client> clients = clientRepository.findAll();

        boolean verify =  clientValidator.validateExistence(clients);

        if(!verify){
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(Boolean.FALSE);
            return notification;
        }else{
            accountRepository.addAccount(client, account);
            Operation operation = new OperationBuilder().setOperation("USER with id = " + userId +
                    " added " + "account to client with id = " + client.getId())
                    .setEmployeeId(userId).build();
            operationRepository.addOperation(operation);
            notification.setResult(Boolean.TRUE);
            return notification;
        }
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Notification<List<Account>> findAllAccountsForClient(Client client) {
        Notification<List<Account>> notification = new Notification<>();
        ClientValidator clientValidator = new ClientValidator(client);
        List<Client> clients = clientRepository.findAll();

        boolean verify =  clientValidator.validateExistence(clients);
        if(!verify){
            clientValidator.getErrors().forEach(notification::addError);
            notification.setResult(null);
            return notification;
        }else{

            notification.setResult(accountRepository.findAllForClient(client));
            return notification;
        }
    }

    @Override
    public void setUserId(Long id) {
        this.userId = id;
    }
}




