package model.builder;

import model.Account;
import model.Client;

import java.util.List;

public class ClientBuilder {
    private Client client;

    public ClientBuilder(){
        this.client = new Client();
    }

    public ClientBuilder setName(String name){
        this.client.setName(name);
        return this;
    }

    public ClientBuilder setId(Long id){
        this.client.setId(id);
        return this;
    }

    public ClientBuilder setIdentityCardNumber(int identityCardNumber){
        this.client.setIdentityCardNumber(identityCardNumber);
        return this;
    }

    public ClientBuilder setPersonalNumericCode(String personalNumericCode){
        this.client.setPersonalNumericCode(personalNumericCode);
        return this;
    }

    public ClientBuilder setAddress(String address){
        this.client.setAddress(address);
        return this;
    }

    public ClientBuilder setAccounts() {
        return setAccounts();
    }

    public Client build(){
        return this.client;
    }
}
