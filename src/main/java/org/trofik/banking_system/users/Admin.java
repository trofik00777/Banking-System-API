package org.trofik.banking_system.users;

public class Admin extends User {
    public Admin(String name, String surname) {
        super(name, surname);
    }

    @Override
    public AdminInformation getInfo() {
        //...
        return null;
    }


}
