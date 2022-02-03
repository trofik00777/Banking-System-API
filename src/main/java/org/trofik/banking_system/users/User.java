package org.trofik.banking_system.users;

public abstract class User {
    public final String name;
    public final String surname;


    protected User(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public abstract UserInformation getInfo();
}
