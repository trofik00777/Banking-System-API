package org.trofik.banking_system.banks;

import java.sql.Date;

public abstract class MoneyInformation {
    protected AbstractBank bank;
    protected Date dateStart;
    protected Date dateFinish;

    public MoneyInformation(AbstractBank bank, Date dateStart, Date dateFinish) {
        this.bank = bank;
        this.dateStart = dateStart;
        this.dateFinish = dateFinish;
    }
}
