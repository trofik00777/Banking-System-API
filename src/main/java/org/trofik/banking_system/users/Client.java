package org.trofik.banking_system.users;

public class Client extends User {
    public Client(String name, String surname, String login, String password) {
        super(name, surname, login, password, false);
    }

    @Override
    public ClientInformation getInfo() {
        //...
        return null;
    }
}
