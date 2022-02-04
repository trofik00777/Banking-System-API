package org.trofik.banking_system.banks;

import java.util.List;

public class BankInformation {
    protected AbstractBank bank;

    public BankInformation(AbstractBank bank) {
        this.bank = bank;
    }

    public List<MoneyInformation> getInfoAboutClients() {
        return List.of(new LoanInformation(bank, 1, 1, null, null));
    }

    @Override
    public String toString() {
        return "BankInformation{" +
                "bank=" + bank +
                '}';
    }
}
