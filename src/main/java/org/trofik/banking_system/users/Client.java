package org.trofik.banking_system.users;

import org.trofik.banking_system.banks.LoanBank;

public class Client extends User {
    public Client(String name, String surname, String login, String password) {
        super(name, surname, login, password, false);
    }

    public Client(String login, String password) {
        super(login, password, false);
    }

    public Client(String login, String password, boolean isHashed) {
        super(login, password, false, isHashed);
    }

    @Override
    public ClientInformation getInfo() {
        return new ClientInformation(this, LoanBank.getInfoAboutClient(this));
    }
}
