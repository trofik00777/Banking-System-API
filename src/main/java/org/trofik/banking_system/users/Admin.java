package org.trofik.banking_system.users;

public class Admin extends User {
    public Admin(String name, String surname, String login, String password, int id) {
        super(name, surname, login, password, id);
    }

    @Override
    public AdminInformation getInfo() {
        //...
        return null;
    }


}
