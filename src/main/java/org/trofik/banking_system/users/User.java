package org.trofik.banking_system.users;

public abstract class User {
    public final String name;
    public final String surname;
    public final String login;
    public final String password;
    public final int id;

    public User(String name, String surname, String login, String password, int id) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public abstract UserInformation getInfo();
}
