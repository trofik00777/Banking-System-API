package org.trofik.banking_system.banks;

public abstract class BankInformation {
    protected AbstractBank bank;

    public BankInformation(AbstractBank bank) {
        this.bank = bank;
    }


}
