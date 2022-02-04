package org.trofik.banking_system.users;

import org.trofik.banking_system.banks.LoanBank;
import org.trofik.banking_system.banks.LoanSaveInformation;
import org.trofik.banking_system.banks.SavingBank;

import java.util.List;

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
        List<LoanSaveInformation> loansSaves = LoanBank.getInfoAboutClient(this);
        loansSaves.addAll(SavingBank.getInfoAboutClient(this));
        return new ClientInformation(this, loansSaves);
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
