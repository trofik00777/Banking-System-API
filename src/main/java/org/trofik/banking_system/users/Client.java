package org.trofik.banking_system.users;

public class Client extends User {
    public Client(String name, String surname) {
        super(name, surname);
    }

    @Override
    public ClientInformation getInfo() {
        //...
        return null;
    }
}
