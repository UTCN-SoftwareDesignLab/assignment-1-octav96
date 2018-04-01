package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private Client client;
    private List<String> errors;
    public ClientValidator(Client client){
        this.client = client;
        this.errors = new ArrayList<>();
    }

    public List<String> getErrors(){
        return this.errors;
    }

    public boolean validateExistence(List<Client> clients){
        for(int i = 0;i < clients.size();i++){
            if(clients.get(i).getId() == this.client.getId()) {
                return true;
            }
        }
        this.errors.add("Client with id = " + this.client.getId() + " does not exist!");
        return false;
    }

    public boolean validateUniqueness(List<Client> clients){

        for(int i = 0;i < clients.size();i++){
            if(clients.get(i).getPersonalNumericCode() == this.client.getPersonalNumericCode()) {
                this.errors.add("Client already exists!");
                return false;
            }
        }
        return true;
    }


}
