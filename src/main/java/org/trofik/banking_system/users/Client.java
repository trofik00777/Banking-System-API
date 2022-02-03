package org.trofik.banking_system.users;

public class Client extends User {
    public Client(String name, String surname, String login, String password, int id) {
        super(name, surname, login, password, id);
    }

    @Override
    public ClientInformation getInfo() {
        //...
        return null;
    }
}
