package org.trofik.banking_system.users;

import org.trofik.banking_system.banks.BankInformation;
import org.trofik.banking_system.banks.LoanBank;
import org.trofik.banking_system.banks.SavingBank;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User {
    public Admin(String name, String surname, String login, String password) {
        super(name, surname, login, password, true);
    }

    public Admin(String login, String password) {
        super(login, password, true);
    }

    public Admin(String login, String password, boolean isHashed) {
        super(login, password, true, isHashed);
    }

    @Override
    public AdminInformation getInfo() {
        List<BankInformation> bankInformationList = new ArrayList<>();
        try {
            bankInformationList.add(new BankInformation(new LoanBank(this)));
        } catch (Exception e) {}
        try {
            bankInformationList.add(new BankInformation(new SavingBank(this)));
        } catch (Exception e) {}

        return new AdminInformation(this, bankInformationList);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                '}';
    }
}
