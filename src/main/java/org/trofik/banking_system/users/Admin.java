package org.trofik.banking_system.users;

public class Admin extends User {
    public Admin(String name, String surname, String login, String password) {
        super(name, surname, login, password, true);
    }

    public Admin(String login, String password) {
        super(login, password, true);
    }

    @Override
    public AdminInformation getInfo() {
        //...
        return null;
    }


}
